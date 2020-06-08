package com.nocease.controller;

import cn.xsshome.taip.imageclassify.TAipImageClassify;
import cn.xsshome.taip.nlp.TAipNlp;
import cn.xsshome.taip.ocr.TAipOcr;
import cn.xsshome.taip.util.RandomNonceStrUtil;
import com.nocease.util.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @version 1.0
 * @Author nocease
 * @date 2020/3/21 14:55
 * @Description 腾讯al接口
 */
@RestController
@RequestMapping("/tencent")
@CrossOrigin
public class TencentAlConroller {
    //文本翻译
    //http://127.0.0.1:8080/tencent/nlpTextTranslate?text=%E4%BD%A0%E5%A5%BD&source=zh&target=en
    @RequestMapping("/nlpTextTranslate")
    public String nlpTextTranslate(String text, String source, String target) throws Exception {
        Map prop = (Map) YamlUtil.getValue().get("tencent");
        String APPID = prop.get("APPID").toString();
        String APPKEY = (String) prop.get("APPKEY");
        TAipNlp client = new TAipNlp(APPID, APPKEY);
        String result = client.nlpTextTranslate(MyUtil.Null2Str(text), MyUtil.Null2Str(source), MyUtil.Null2Str(target));//文本翻译(翻译君)
        return result;
    }

    //身份证ocr
    //http://127.0.0.1:8080/tencent/idcardOcr?url=http://upload.jinxv.cn/upload/96b14d5f6f854a4199591dae357281c1.jpg&id=0
    //0正面 1反面
    @RequestMapping("/idcardOcr")
    public String idcardOcr(String url, Integer id) throws Exception {
        Map prop = (Map) YamlUtil.getValue().get("tencent");
        String APPID = prop.get("APPID").toString();
        String APPKEY = (String) prop.get("APPKEY");
        TAipOcr client = new TAipOcr(APPID, APPKEY);
        String result = client.idcardOcr(HttpUrlToFile.getNetBytes(MyUtil.Null2Str(url)), MyUtil.null2o(id));
        return result;
    }

    //描述图片
    //http://127.0.0.1:8080/tencent/idcardOcr?url=http://upload.jinxv.cn/upload/96b14d5f6f854a4199591dae357281c1.jpg&id=0
    @RequestMapping("/visionImgtotext")
    public String visionImgtotext(String url) throws Exception {
        Map prop = (Map) YamlUtil.getValue().get("tencent");
        String APPID = prop.get("APPID").toString();
        String APPKEY = (String) prop.get("APPKEY");
        TAipImageClassify client = new TAipImageClassify(APPID, APPKEY);
        String result = client.visionImgtotext(HttpUrlToFile.getNetBytes(MyUtil.Null2Str(url)), RandomNonceStrUtil.getRandomString());
        return result;
    }

    //按行识别图片文本
    //http://127.0.0.1:8080/tencent/generalOcr?url=http://upload.jinxv.cn/upload/96b14d5f6f854a4199591dae357281c1.jpg    @RequestMapping("/generalOcr")
    @RequestMapping("/generalOcr")
    public String generalOcr(String url) throws Exception {
        Map prop = (Map) YamlUtil.getValue().get("tencent");
        String APPID = prop.get("APPID").toString();
        String APPKEY = (String) prop.get("APPKEY");
        TAipOcr client = new TAipOcr(APPID, APPKEY);
        String result = client.generalOcr(HttpUrlToFile.getNetBytes(MyUtil.Null2Str(url)));
        return result;
    }

    //邮件推送
    @RequestMapping("/sendMail")
    public boolean sendmMail(String email, String title, String msg) {
        SendMail sendMail = new SendMail();
        sendMail.setAddressee(MyUtil.Null2Str(email));
        sendMail.setTitle(MyUtil.Null2Str(title));
        sendMail.setMess(MyUtil.Null2Str(msg));
        sendMail.start();
        return true;
    }

    //短信推送
    @RequestMapping("/sendSms")
    public boolean sendSms(String phone, String code) {
        SendSms sendSms = new SendSms();
        sendSms.Send(MyUtil.Null2Str(phone), MyUtil.Null2Str(code));
        return true;
    }

}
