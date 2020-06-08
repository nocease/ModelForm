package com.nocease.pojo;

import com.nocease.util.MyUtil;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * @version 1.0
 * @Author nocease
 * @date 2019/12/24 15:30
 * @Description 新建页面设置 实体类
 */
@Data
public class MfPageSetAdd {
    @Id
    private String fieldid;//字段id
    private String pageid;//页面id
    private String ispost;//是否提交 0/1
    private String fieldstate;//字段属性  0只读 1可编辑 2必填
    private String defaultvalue;//字段默认值
    private String orderby;//排序

    @Transient
    private MfField mfField;//字段对象
    @Transient
    private MfPage mfPage;//页面对象

}
