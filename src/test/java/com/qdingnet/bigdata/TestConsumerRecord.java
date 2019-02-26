package com.qdingnet.bigdata;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qdingnet.bigdata.beans.AzkabanMonitorBlacklist;
import com.qdingnet.bigdata.beans.AzkabanMonitorWhitelist;
import com.qdingnet.bigdata.config.AzkabanProperties;
import com.qdingnet.bigdata.mapper.AzkabanMonitorBlacklistMapper;
import com.qdingnet.bigdata.mapper.AzkabanMonitorWhitelistMapper;
import com.qdingnet.bigdata.mq.KafkaAzkanbanLogConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/2/21 16:53
 * @description
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestConsumerRecord {

    @Autowired
    KafkaAzkanbanLogConsumer consumer;

    @Test
    public void testConsumerRecord() throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "INSERT");
        JSONObject item = new JSONObject();
        item.put("log", "error hhhhh");
        item.put("name", "name");
        item.put("exec_id", "execid");
        item.put("upload_time", "12344555666");
        item.put("attempt", "0");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(item);
        jsonObject.put("data", jsonArray);
        ConsumerRecord<String, String> record = new ConsumerRecord<>("azkaban_event_log", 1, 1, "", jsonObject.toJSONString());
        consumer.onReceive(record);
    }

    @Autowired
    AzkabanProperties azkabanProperties;

    @Autowired
    AzkabanMonitorBlacklistMapper blacklistMapper;

    @Autowired
    AzkabanMonitorWhitelistMapper whitelistMapper;

    //@Test
    public void testAddBlackList(){
        final List<String> blacklist = azkabanProperties.getBlacklist();
        for (String black : blacklist) {
            AzkabanMonitorBlacklist azkabanMonitorBlacklist = new AzkabanMonitorBlacklist();
            azkabanMonitorBlacklist.setType("match");
            azkabanMonitorBlacklist.setBusinessType("azkaban_event_log");
            azkabanMonitorBlacklist.setContent(black);
            blacklistMapper.insertSelective(azkabanMonitorBlacklist);
        }
    }

    //@Test
    public void testAddWhiteList(){
        final List<String> whitelist = azkabanProperties.getWhitelist();
        for (String white : whitelist) {
            AzkabanMonitorWhitelist azkabanMonitorWhitelist = new AzkabanMonitorWhitelist();
            azkabanMonitorWhitelist.setType("match");
            azkabanMonitorWhitelist.setBusinessType("azkaban_event_log");
            azkabanMonitorWhitelist.setContent(white);
            whitelistMapper.insertSelective(azkabanMonitorWhitelist);
        }
    }
}
