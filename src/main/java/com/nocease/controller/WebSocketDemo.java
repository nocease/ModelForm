package com.nocease.controller;

import com.nocease.util.WebsocketServer;
import com.nocease.util.YamlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @Author nocease
 * @date 2019/12/31 16:49
 * @Description
 */
@RestController
@RequestMapping("/demo")
public class WebSocketDemo {
    @Autowired
    private WebsocketServer ws;

    @RequestMapping("/demo1")
    public String demo() {
        ws.sendToOne("发给12", "12");
        ws.sendToOne("发给13", "13");
        return "ok";
    }

    @RequestMapping("/demo2")
    public void demo2() {
        System.out.println(YamlUtil.getValue());
    }

}
