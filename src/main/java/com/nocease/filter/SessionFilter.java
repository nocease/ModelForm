package com.nocease.filter;

import com.nocease.util.CookieUtil;
import com.nocease.util.MyUtil;
import com.nocease.util.RedisUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 为用户页面附带cookie，并存到redis
 */
@WebFilter("/userhtml/*")
public class SessionFilter implements Filter {

    private RedisUtil redisUtil;

    public SessionFilter(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        String uuid = MyUtil.getUUID();
        CookieUtil cookieUtil = new CookieUtil(req, res);

        //如果没有会话的cookie
        if (cookieUtil.getCookie("JSESSIONID") == null) {
            cookieUtil.setCookie("JSESSIONID", uuid, 1);
            Map map = new HashMap();
            map.put("JSESSIONID", uuid);
            redisUtil.set("JSESSIONID::" + uuid, map);
        } else {
            //如果有jsessionid
            //但是redis里面没有，说明已失效，重新给
            if (redisUtil.get("JSESSIONID::" + cookieUtil.getCookie("JSESSIONID")) == null) {
                cookieUtil.setCookie("JSESSIONID", uuid, 1);
                Map map = new HashMap();
                map.put("JSESSIONID", uuid);
                redisUtil.set("JSESSIONID::" + uuid, map);
            }
        }

        chain.doFilter(request, response);
    }

}
