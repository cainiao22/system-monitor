package com.qdingnet.bigdata.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qdingnet.bigdata.beans.WechartMsg;
import com.qdingnet.bigdata.component.WeChatAlarmSender;
import com.qdingnet.bigdata.config.AzkabanProperties;
import com.qdingnet.bigdata.utils.GZIPUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author yanpf
 * @date 2018/12/3 15:09
 * @description
 */
@Slf4j
@Component
public class KafkaAzkanbanLogConsumer {

    @Resource
    AzkabanProperties azkabanProperties;

    @Resource
    WeChatAlarmSender sender;

    @KafkaListener(topics = {"azkaban_event_log"})
    public void onReceive(ConsumerRecord<String, String> record) throws Exception {
        JSONObject jsonObject = JSON.parseObject(record.value());
        JSONArray data = jsonObject.getJSONArray("data");
        for (int i = 0; i < data.size(); i++) {
            JSONObject item = data.getJSONObject(i);
            String logInfo = item.getString("log");
            String name = item.getString("name");
            String execId = item.getString("exec_id");
            String uploadTime = item.getString("upload_time");
            if (StringUtils.isEmpty(name)) {
                name = item.getString("exec_id");
            }
            if (name.indexOf(":") != -1) {
                name = name.substring(name.lastIndexOf(":") + 1);
            }
            String s = GZIPUtils.uncompressToString(Base64.decodeBase64(logInfo));
            log.info("获取日志信息:{}", s.length());
            if (s != null) {
                String[] split = s.split("\n");
                label:
                for (String s1 : split) {
                    boolean isBlack = false;
                    for (String black : azkabanProperties.getBlacklist()) {
                        if(s1.contains(black)){
                            isBlack = true;
                            break;
                        }
                    }
                    if (isBlack) {
                        for (String white : azkabanProperties.getWhitelist()) {
                            if (s1.contains(white)) {
                                continue label;
                            }
                        }
                        log.info("接收到错误信息:{}", record.value());
                        WechartMsg wechartMsg = new WechartMsg("azkaban任务监控");
                        String msg = "project:%s,exec_id:%s,uploadTime:%s, error:%s";
                        msg = String.format(msg, name, execId, uploadTime, s1);
                        wechartMsg.setContent(msg);
                        wechartMsg.setMobiles(azkabanProperties.getAlarmPhones());
                        wechartMsg.setTitle("azkaban任务监控");
                        log.info("发现错误信息:{}，发送报警!!!", s1);
                        sender.send(wechartMsg);
                    }
                }
            }
        }

    }
}
