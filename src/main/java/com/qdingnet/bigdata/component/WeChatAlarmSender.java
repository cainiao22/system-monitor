package com.qdingnet.bigdata.component;

import com.alibaba.fastjson.JSON;
import com.qdingnet.bigdata.beans.WechartMsg;
import com.qdingnet.bigdata.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanpf
 * @date 2018/12/4 16:49
 * @description
 */
@Slf4j
@Component
public class WeChatAlarmSender {

    @Value("${wechart.access_token_url}")
    String ACCESS_TOKEN_URL;

    @Value("${wechart.message_send_url}")
    String ALARM_WECHAT;

    @Value("${wechart.agentid}")
    String agentId;

    private static final String proxyUrl = "http://m7-vm-bd-66:12051/httpProxy";

    /**
     * {
     * "touser" : "UserID1|UserID2|UserID3",
     * "toparty" : "PartyID1|PartyID2",
     * "totag" : "TagID1 | TagID2",
     * "msgtype" : "text",
     * "agentid" : 1,
     * "text" : {
     * "content" : "你的快递已到，请携带工卡前往邮件中心领取。\n出发前可查看<a href=\"http://work.weixin.qq.com\">邮件中心视频实况</a>，聪明避开排队。"
     * },
     * "safe":0
     * }
     *
     * @param msg
     */
    @Async
    public void send(WechartMsg msg) {
        final String accessToken = this.getAccessToken();
        Map<String, String> param = new HashMap<>();
        param.put("url", ALARM_WECHAT.replace("ACCESS_TOKEN", accessToken));
        param.put("params", JSON.toJSONString(msg));
        log.info("发送post信息, params:{}", JSON.toJSONString(param));
        HttpClientUtils.doPost(proxyUrl, param);
    }

    public String getAccessToken() {
        Map<String, String> param = new HashMap<>();
        param.put("url", ACCESS_TOKEN_URL + agentId);
        String resp = HttpClientUtils.doGet(proxyUrl, param);
        val jsonObject = JSON.parseObject(resp);
        if (jsonObject.getInteger("code") == 200) {
            String accessToken = jsonObject.getString("data");
            return accessToken;
        } else {
            return null;
        }
    }
}
