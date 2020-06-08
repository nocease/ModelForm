package com.nocease.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

/**
 * @version 1.0
 * @Author nocease
 * @date 2019/11/26 14:05
 * @Description 表单字段实体类
 */
@Data
public class MfField {
    @Id
    private String fieldId;//字段主键
    private String formId;//所属表单
    private String fieldTime;//创建时间
    private String fieldName;//字段名称
    private String fieldNameDesc;//字段名称描述
    private String fieldType;//字段类型123456
    private String fieldZzTest;//单行文本时正则表达式
    private String fieldZzDesc;//对正则表达式的说明
    private String fieldFiletype;//上传文件时类型123
    private String fieldTimetype;//时间日期类型123
    private String fieldSelectValue;//选择框的选项（uuid）

    @Transient
    private List<MfValue> mfValues;//选择框的全部选项
    @Transient
    private String defaultValue;//默认值

    //无参构造
    public MfField() {
    }

    //有参构造
    public MfField(String fieldId, String formId, String fieldName, String fieldNameDesc) {
        this.fieldId = fieldId;
        this.formId = formId;
        this.fieldName = fieldName;
        this.fieldNameDesc = fieldNameDesc;
        this.fieldTime = "9~~~9";
    }
}
