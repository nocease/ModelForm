package com.nocease.pojo;

import lombok.Data;

/**
 * @version 1.0
 * @Author nocease
 * @date 2020/3/19 11:14
 * @Description 表单提交的接收数据key value
 */
@Data
public class PostValue {
    private String key;
    private String value;
}
