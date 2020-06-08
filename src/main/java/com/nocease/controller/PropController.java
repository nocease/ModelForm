package com.nocease.controller;

import com.alibaba.fastjson.JSONObject;
import com.nocease.util.RedisUtil;
import com.nocease.util.YamlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @version 1.0
 * @Author nocease
 * @date 2020/3/22 17:32
 * @Description
 */
@RestController
@RequestMapping("/prop")
public class PropController {
    @Autowired
    private RedisUtil redisUtil;

    //获取全部配置
    @RequestMapping("/getProp")
    @Cacheable(value = "prop", key = "'Allprop'")
    public Map getProp() {
        return YamlUtil.getValue();
    }

    //保存配置信息
    @RequestMapping("/keepProp")
    public boolean keepProp(@RequestBody JSONObject jsonParam) {
        redisUtil.delByStr("prop");
        redisUtil.delByStr("addHtml");
        YamlUtil.setValue(jsonParam);
        return true;
    }

    //获取主题配置
    @RequestMapping("/getTheme")
    @Cacheable(value = "prop", key = "'theme'")
    public String getTheme() {
        return YamlUtil.getValue().get("theme").toString();
    }
}
