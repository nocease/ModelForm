package com.nocease.pojo;

import org.apache.http.Header;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 响应内容：实体类
 */
public class HttpClientResult implements Serializable {

    private static final long serialVersionUID = 2168152194164783950L;

    //响应码
    private int code;

    //body
    private String content;

    //响应状态行
    private String StatusLine;

    //响应头
    private Header[] allHeader;

    //响应头的cookie
    private Header[] allCookie;

    public HttpClientResult() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatusLine() {
        return StatusLine;
    }

    public void setStatusLine(String statusLine) {
        StatusLine = statusLine;
    }

    public Header[] getAllHeader() {
        return allHeader;
    }

    public void setAllHeader(Header[] allHeader) {
        this.allHeader = allHeader;
    }

    public String getAllCookie() {
        String str="";
        for (Header c:allCookie){
            str+=  c.toString().split(";")[0].substring(12,c.toString().split(";")[0].length())+";";
        }
        return str;
    }

    public void setAllCookie(Header[] allCookie) {
        this.allCookie = allCookie;
    }

    @Override
    public String toString() {
        return "HttpClientResult{" +
                "code=" + code +
                ", content='" + content + '\'' +
                ", StatusLine='" + StatusLine + '\'' +
                ", allHeader=" + Arrays.toString(allHeader) +
                ", allCookie=" + Arrays.toString(allCookie) +
                '}';
    }
}