package com.nocease.filter;

import com.nocease.util.MyUtil;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 安全机制，校验key，保证接口安全
 * 加密方法：（时间的md5小写加SECRET）的md5的小写//！！！切勿泄漏
 */
@WebFilter("/*")
public class SafeX5Filter implements Filter {

    private static final String SECRET = "848817043";

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        request.setCharacterEncoding("utf-8");
        String URI = req.getRequestURI();
        if (URI.equals("/doSQL/setSession") ||
                URI.equals("/doSQL/getSession") ||
                URI.equals("/doSQL/insertORupdate") ||
                URI.equals("/doSQL/getObjList") ||
                URI.equals("/doSQL/getObj") ||
                URI.equals("/tencent/nlpTextTranslate") ||
                URI.equals("/tencent/idcardOcr") ||
                URI.equals("/tencent/visionImgtotext") ||
                URI.equals("/tencent/generalOcr") ||
                URI.equals("/tencent/sendSms") ||
                URI.equals("/tencent/sendMail")) {
            String key = request.getParameter("key");
            String time = request.getParameter("timestamp");
            String md5 = MyUtil.getMd5(MyUtil.getMd5(time).toLowerCase() + SECRET).toLowerCase();
            if (!md5.equals(key)) {
                System.out.println("非法请求，校验key失败！");
                return;
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

}
