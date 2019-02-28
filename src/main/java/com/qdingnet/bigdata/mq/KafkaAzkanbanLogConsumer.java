package com.qdingnet.bigdata.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qdingnet.bigdata.beans.*;
import com.qdingnet.bigdata.component.WeChatAlarmSender;
import com.qdingnet.bigdata.enums.BinLogTypeEnum;
import com.qdingnet.bigdata.mapper.AzkabanMonitorBlacklistMapper;
import com.qdingnet.bigdata.mapper.AzkabanMonitorOwnerMapper;
import com.qdingnet.bigdata.mapper.AzkabanMonitorWhitelistMapper;
import com.qdingnet.bigdata.service.AzkabanErrorInfoService;
import com.qdingnet.bigdata.utils.Constants;
import com.qdingnet.bigdata.utils.GZIPUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yanpf
 * @date 2018/12/3 15:09
 * @description
 */
@Slf4j
@Component
public class KafkaAzkanbanLogConsumer {

    @Resource
    AzkabanMonitorOwnerMapper azkabanMonitorOwnerMapper;

    @Resource
    AzkabanMonitorBlacklistMapper azkabanMonitorBlacklistMapper;

    @Resource
    AzkabanMonitorWhitelistMapper azkabanMonitorWhitelistMapper;

    @Value("${wechart.agentid}")
    String agentId;

    @Resource
    WeChatAlarmSender sender;

    @Resource
    AzkabanErrorInfoService azkabanErrorInfoService;

    @Resource
    RedisTemplate<String, String> redisTemplate;

    @KafkaListener(topics = {"azkaban_event_log", "sz_azkaban_event_log", "wyy_azkaban_event_log"})
    public void onReceive(ConsumerRecord<String, String> record) throws Exception {
        JSONObject jsonObject = JSON.parseObject(record.value());
        if(!BinLogTypeEnum.INSERT.name().equals(jsonObject.getString("type"))){
            return;
        }
        JSONArray data = jsonObject.getJSONArray("data");
        for (int i = 0; i < data.size(); i++) {
            JSONObject item = data.getJSONObject(i);
            String logInfo = item.getString("log");
            String name = item.getString("name");
            String execId = item.getString("exec_id");
            String uploadTime = item.getString("upload_time");
            String attempt = item.getString("attempt");
            if (StringUtils.isEmpty(name)) {
                name = item.getString("exec_id");
            }
            if (name.indexOf(":") != -1) {
                name = name.substring(name.lastIndexOf(":") + 1);
            }
            String s = GZIPUtils.uncompressToString(Base64.decodeBase64(logInfo));
            log.info("获取日志信息:{},topic:{}", s.length(), record.topic());
            if (s != null) {
                String[] split = s.split("\n");
                label:
                for (String s1 : split) {
                    boolean isBlack = false;
                    for (AzkabanMonitorBlacklist black : azkabanMonitorBlacklistMapper.getListByBusinessType(record.topic())) {
                        if(s1.contains(black.getContent())){
                            String redisKey = getRedisKey(record.topic(), black.getContent(), execId, name);
                            if(redisTemplate.opsForValue().get(redisKey) != null){
                                continue label;
                            }
                            redisTemplate.opsForValue().set(redisKey, "1", 1, TimeUnit.MINUTES);
                            isBlack = true;
                            break;
                        }
                    }
                    if (isBlack) {
                        for (AzkabanMonitorWhitelist white : azkabanMonitorWhitelistMapper.getListByBusinessType(record.topic())) {
                            if (s1.contains(white.getContent())) {
                                continue label;
                            }
                        }
                        log.info("接收到错误信息:{}", record.value());
                        TextWechartMsg wechartMsg = new TextWechartMsg();
                        String msg = "project:%s,exec_id:%s,uploadTime:%s, error:%s";
                        msg = String.format(msg, name, execId, uploadTime, s1);

                        List<String> owners = new ArrayList<>();
                        for (AzkabanMonitorOwner owner : azkabanMonitorOwnerMapper.getListByBusinessType(record.topic())) {
                            String userName = owner.getUserName();
                            owners.add(userName);
                        }
                        wechartMsg.setContent(msg);
                        wechartMsg.setTouser(String.join("|", owners));
                        wechartMsg.setAgentid(agentId);
                        log.info("发现错误信息:{}，发送报警!!!", s1);
                        sender.send(wechartMsg);
                        AzkabanErrorInfo azkabanErrorInfo = new AzkabanErrorInfo();
                        azkabanErrorInfo.setExecId(Integer.valueOf(execId));
                        azkabanErrorInfo.setErrorLog(s1);
                        azkabanErrorInfo.setName(name);
                        azkabanErrorInfo.setAttempt(Integer.valueOf(attempt));
                        azkabanErrorInfo.setUploadTime(Long.valueOf(uploadTime));
                        log.info("保存错误的任务信息");
                        azkabanErrorInfoService.add(azkabanErrorInfo);
                    }
                }
            }
        }

    }

    public String getRedisKey(String ... keys) throws Exception {
        StringBuffer sb = new StringBuffer(Constants.Redis.prefix);
        sb.append("KafkaAzkanbanLogConsumer::");
        if(keys == null || keys.length == 0){
            throw new Exception("redis的keys不能为空");
        }

        for (String key : keys) {
            if(key == null){
                key = "";
            }
            sb.append("::").append(Base64.encodeBase64String(key.getBytes()));
        }

        return sb.toString();
    }



}
