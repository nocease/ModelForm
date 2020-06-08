package com.nocease.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * @version 1.0
 * @Author nocease
 * @date 2020/3/19 13:59
 * @Description 查询页面设置 实体类
 */
@Data
public class MfPageSetList {
    @Id
    private String fieldid;//字段id
    private String pageid;//页面id
    private String canSort;//是否排序
    private String canSearch;//是否搜索
    private String orderby;//顺序

    @Transient
    private MfField mfField;//字段对象
}
