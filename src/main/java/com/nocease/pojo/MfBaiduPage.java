package com.nocease.pojo;

import lombok.Data;

import javax.persistence.Id;

/**
 * @version 1.0
 * @Author nocease
 * @date 2020/4/2 14:54
 * @Description 百度统计页面
 */
@Data
public class MfBaiduPage {
    @Id
    private String pageid;//页面id
    private String bdId;//百度统计id
}
