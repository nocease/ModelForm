package com.nocease.util;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;

import java.io.IOException;
import java.util.Map;

/**
 * @version 1.0
 * @Author nocease
 * @date 2020/3/21 16:50
 * @Description
 */
public class SendSms {
    public void Send(String phone, String code) {
        Map prop = (Map) YamlUtil.getValue().get("sms");
        String[] phoneNumbers = {phone};
        try {
            String[] params = {code};
            SmsSingleSender ssender = new SmsSingleSender(Integer.parseInt(prop.get("appid").toString()), prop.get("appkey").toString());
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0], Integer.parseInt(prop.get("templateId").toString()), params, prop.get("smsSign").toString(), "",
                    "");
        } catch (HTTPException e) {
            // HTTP 响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // JSON 解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络 IO 错误
            e.printStackTrace();
        }
    }
}
