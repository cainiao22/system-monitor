package com.qdingnet.bigdata.component;

import com.alibaba.fastjson.JSON;
import com.qdingnet.bigdata.beans.WechartMsg;
import com.qdingnet.bigdata.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yanpf
 * @date 2018/12/4 16:49
 * @description
 */
@Slf4j
@Component
public class WeChatAlarmSender {

    /**
     * 参数： mobiles=15801029684|13641064288|15110234502|15011233180|18600295448|15313159809&title=监控报表&content=
     */
    public static final String ALARM_WECHAT="http://wukong.iqdnet.cn/wukongbg/admin/api/wx_msg_send_by_mobiles";

    @Async
    public void send(WechartMsg msg){
        Map<String, String> params = new HashMap<>(3);
        params.put("mobiles", msg.getMobiles().stream().collect(Collectors.joining("|")));
        params.put("title", msg.getTitle());
       /* params.put("content", msg.getContent()
                .replaceAll("\"", "'")
                .replaceAll("\\\\", ""));*/
       params.put("content", msg.getContent());
        log.info("发送报警信息{} ---->>>> {}", String.join("|", msg.getMobiles()), JSON.toJSONString(params));
        HttpClientUtils.doPost(ALARM_WECHAT, params);
    }
}
