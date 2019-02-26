package com.qdingnet.bigdata.component;

import com.alibaba.fastjson.JSON;
import com.qdingnet.bigdata.beans.WechartMsg;
import com.qdingnet.bigdata.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

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
        HttpClientUtils.sendHttpPost(ALARM_WECHAT.replace("ACCESS_TOKEN", accessToken), msg);
    }

    public String getAccessToken() {
        String resp = HttpClientUtils.doGet(ACCESS_TOKEN_URL + agentId);
        val jsonObject = JSON.parseObject(resp);
        if (jsonObject.getInteger("code") == 200) {
            String accessToken = jsonObject.getString("data");
            return accessToken;
        } else {
            return null;
        }
    }
}
