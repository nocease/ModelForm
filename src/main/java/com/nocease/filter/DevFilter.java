package com.nocease.filter;

import com.nocease.util.YamlUtil;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 * 判断当前配置文件 是否允许进行开发
 */
@WebFilter("/mfhtml/*")
public class DevFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String dev = YamlUtil.getValue().get("dev").toString();
        if (dev.equals("true"))
            chain.doFilter(request, response);
        else {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect("/userhtml/notDev.html");
        }
    }
}
