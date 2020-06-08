package com.nocease.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @version 1.0
 * @Author nocease
 * @date 2020/4/13 15:30
 * @Description 获取一个Connection对象
 */
@Configuration
public class GetConnection {
    @Value("${spring.datasource.driver-class-name}")
    private String driveClassName;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String userPassword;

    public Connection getMySqlConnection() {
        try {
            Class.forName(driveClassName);
            return DriverManager.getConnection(url, userName, userPassword);
        } catch (Exception e) {
            System.out.println("获取conn对象失败！");
            return null;
        }

    }

}
