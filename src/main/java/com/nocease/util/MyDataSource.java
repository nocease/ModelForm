package com.nocease.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @Author nocease
 * @date 2019/12/11 9:37
 * @Description 从配置文件分析出当前的数据库类型
 */

@Configuration
public class MyDataSource {
    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    //获取数据库类型
    public String getSqlType() {
        return jdbcUrl.split(":")[1];
    }
}
