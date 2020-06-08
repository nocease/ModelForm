package com.nocease.util;

import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * @version 2.0
 * @Author nocease
 * @Date 2019/11/12 16:43
 * @Description 发送邮件
 */
public class SendMail extends Thread {

//    // 配置信息
//    @Value("${mail.mailUser}")
//    private String sendMail;
//    @Value("${mail.mailPassword}")
//    private String password;

    // 传入参数
    private String Addressee;
    private String title;
    private String mess;

    public String getAddressee() {
        return Addressee;
    }

    public void setAddressee(String addressee) {
        Addressee = addressee;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public SendMail(String addressee, String title, String mess) {
        super();
        Addressee = addressee;
        this.title = title;
        this.mess = mess;
    }

    public SendMail() {
        super();
    }

    public void run() {
        //System.out.println(this.sendMail + "-----" + this.password);
        this.Send(this.Addressee, this.title, this.mess);
    }

    private void Send(String Addressee, String title, String mess) {
        try {
            Properties prop = new Properties();
            // 开启debug调试，以便在控制台查看
            prop.setProperty("mail.debug", "true");
            // 设置邮件服务器主机名
            prop.setProperty("mail.host", "smtp.qq.com");
            // 发送服务器需要身份验证
            prop.setProperty("mail.smtp.auth", "true");
            // 发送邮件协议名称
            prop.setProperty("mail.transport.protocol", "smtp");

            // 开启SSL加密
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.socketFactory", sf);

            // 创建session
            Session session = Session.getInstance(prop);
            // 通过session得到transport对象
            Transport ts = session.getTransport();
            // 连接邮件服务器：邮箱类型，帐号，授权码代替密码（更安全）
            // =====================自行修改账号和授权码
            Map mailPROP = (Map) YamlUtil.getValue().get("mail");
            ts.connect("smtp.qq.com", mailPROP.get("mailUser").toString(), mailPROP.get("mailPassword").toString());// 后面的字符是授权码
            // 创建邮件
            Message message = createSimpleMail(session, Addressee, title, mess);
            // 发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    /**
     * @Method: createSimpleMail
     * @Description: 创建一封只包含文本的邮件
     */
    private MimeMessage createSimpleMail(Session session, String Addressee, String title, String mess)
            throws Exception {
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人
        Map mailPROP = (Map) YamlUtil.getValue().get("mail");
        message.setFrom(new InternetAddress(mailPROP.get("mailUser").toString()));
        // 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(Addressee));
        // 邮件的标题
        message.setSubject(title);
        // 邮件的文本内容
        message.setContent(mess, "text/html;charset=UTF-8");
        // 返回创建好的邮件对象
        return message;
    }
}
