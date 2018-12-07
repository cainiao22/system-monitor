package com.qdingnet.bigdata.controller;

import com.qdingnet.bigdata.component.WeChatAlarmSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("index")
    public String index(){
        sender.send(null);
        return "hello";
    }
}
