package com.nocease.pojo;


import javax.persistence.Id;
import lombok.Data;

/**
 * @version 1.0
 * @Author nocease
 * @Date 2019/11/10 13:08
 * @Description 表单实体类
 */
@Data
public class MfForm {
    @Id
    private String formId;//表单主键
    private String formName;//表单名称
    private String formTablename;//数据库表名
    private String formDescribe;//描述表单
    private String formTime;//创建时间
}
