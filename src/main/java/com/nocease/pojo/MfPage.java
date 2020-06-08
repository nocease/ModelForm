package com.nocease.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * @version 1.0
 * @Author nocease
 * @date 2019/12/20 15:26
 * @Description 页面的实体类
 */
@Data
public class MfPage {
    @Id
    private String id;//主键uuid
    private String ctime;//创建时间
    private String title;//标题
    private String message;//备注说明
    private String formid;//表单id
    private String importscript;//js
    private String pagetype;//页面类型 0查询 1新建
    private String canuse;//状态0/1
    private String htmlWidth;//页面宽度占比（10-100）
    private String pagelimit;//查询页面分页，一页几条

    @Transient
    private String upFileUrl;//上传文件接口
}
