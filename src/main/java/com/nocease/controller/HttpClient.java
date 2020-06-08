package com.nocease.controller;

import com.alibaba.fastjson.JSONObject;
import com.nocease.pojo.HttpClientResult;
import com.nocease.util.HttpClientUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @Author nocease
 * @date 2020/3/21 13:43
 * @Description 辅助进行Ajax跨域请求
 */
@RestController
@CrossOrigin
public class HttpClient {
    @RequestMapping(value = "/dohttp")
    public HttpClientResult doAjax(@RequestBody JSONObject jsonObject) {
        HttpClientResult httpClientResult = new HttpClientResult();
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> params = new HashMap<String, String>();
        String type = "";
        String url = "";
        String isjson = "";
        try {
            map = (Map<String, Object>) jsonObject;
            headers = (Map<String, String>) map.get("headers");
            params = (Map<String, String>) map.get("params");
            type = (String) map.get("type");
            url = (String) map.get("url");
            isjson = (String) map.get("sendjson");
        } catch (Exception e) {
            e.printStackTrace();
            httpClientResult.setContent("error:传入的参数有误。" + e.getMessage());
            return httpClientResult;
        }

        if (isjson.equals("true")) {
            try {
                httpClientResult = HttpClientUtil.doPost(url, headers, params + "");
            } catch (Exception e) {
                e.printStackTrace();
                httpClientResult.setContent("error:post请求发送json数据失败！" + e.getMessage());
            }
        } else {
            if (type.equals("post")) {
                try {
                    httpClientResult = HttpClientUtil.doPost(url, headers, params);
                } catch (Exception e) {
                    e.printStackTrace();
                    httpClientResult.setContent("error:post请求失败！" + e.getMessage());
                }
            } else if (type.equals("get")) {
                try {
                    httpClientResult = HttpClientUtil.doGet(url, headers, params);
                } catch (Exception e) {
                    e.printStackTrace();
                    httpClientResult.setContent("error:get请求失败！" + e.getMessage());
                }
            } else {
                httpClientResult.setContent("error:请求类型只能为post或get！");
            }
        }

        return httpClientResult;
    }
}
