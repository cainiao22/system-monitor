package com.qdingnet.bigdata.controller;

import com.alibaba.fastjson.JSON;
import com.qdingnet.bigdata.beans.TextWechartMsg;
import com.qdingnet.bigdata.component.WeChatAlarmSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanpf
 * @date 2018/12/4 17:03
 * @description
 */

@RestController
public class IndexController {

    @Autowired
    WeChatAlarmSender sender;

    @PostMapping("index")
    public String index(TextWechartMsg wechartMsg){
        System.out.println(JSON.toJSONString(wechartMsg));
        return "hello";
    }
}
