package com.nocease.controller;

import com.alibaba.fastjson.JSON;
import com.nocease.mapper.FormMapper;
import com.nocease.util.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author nocease
 * @date 2020/3/27 11:41
 * @Description
 */
@RestController
@RequestMapping("/doSQL")
@CrossOrigin
public class DoSQLController {
    @Autowired
    private FormMapper fm;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MyDataSource myDataSource;
    @Autowired
    private GetConnection getConnection;

    //获取一个对象
    @RequestMapping("/getObj")
    public Map<String, String> getObj(String sql) {
        return fm.getOneResult(sql);
    }

    //获取一个对象集合
    @RequestMapping("/getObjList")
    public List<Map<String, String>> getObjList(String sql) {
        return fm.getResault(sql);
    }

    //执行sql(不推荐)
    @RequestMapping("/insertORupdate")
    public int insertORupdate(String sql) {
        return fm.doSql(sql);
    }

    //获取redis里的当前session
    @RequestMapping("/getSession")
    public String getSession(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil cookieUtil = new CookieUtil(request, response);
        //System.out.println(redisUtil.get("JSESSIONID::" + cookieUtil.getCookie("JSESSIONID")));
        return redisUtil.get("JSESSIONID::" + cookieUtil.getCookie("JSESSIONID"));
    }

    //为redis里的当前session赋值或追加值
    @RequestMapping("/setSession")
    public void setSession(String key1, String value, HttpServletRequest request, HttpServletResponse response) {
        CookieUtil cookieUtil = new CookieUtil(request, response);
        String json = redisUtil.get("JSESSIONID::" + cookieUtil.getCookie("JSESSIONID"));
        //System.out.println("11111:" + cookieUtil.getCookie("JSESSIONID"));
        Map map = (Map) JSON.parse(json);
        map.put(MyUtil.Null2Str(key1), MyUtil.Null2Str(value));
        redisUtil.set("JSESSIONID::" + cookieUtil.getCookie("JSESSIONID"), map);
    }

    //删除redis里的当前session（恢复初始值）
    @RequestMapping("/delSession")
    public void delSession(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil cookieUtil = new CookieUtil(request, response);
        Map<String, String> map = new HashMap<>();
        map.put("JSESSIONID", cookieUtil.getCookie("JSESSIONID"));
        redisUtil.set("JSESSIONID::" + cookieUtil.getCookie("JSESSIONID"), map);
    }

    //初始化数据库
    @RequestMapping("/InitializeDatabase")
    public void InitializeDatabase() {
        //获取当前连接的数据库类型
        String sqlType = myDataSource.getSqlType();
        //获取conn对象
        Connection conn = getConnection.getMySqlConnection();
        //sql文件路径
        String sqlPath = "";
        if (sqlType.equals("mysql")) {
            sqlPath = "static/sql/mysql.sql";
        } else if (sqlType.equals("oracle")) {
            sqlPath = "static/sql/oracle.sql";
        } else {
            sqlPath = "static/sql/sql.sql";
        }
        try {
            ScriptRunner runner = new ScriptRunner(conn);
            Resources.setCharset(Charset.forName("UTF-8")); //设置字符集
            runner.setLogWriter(null);//设置是否输出日志
            Reader read = Resources.getResourceAsReader(sqlPath);
            runner.runScript(read);
            runner.closeConnection();
            conn.close();
            System.out.println("初始化数据库成功！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("初始化数据库失败！");
        }
    }

}
