package com.nocease.service;

import com.nocease.pojo.LayuiTable;
import com.nocease.pojo.MfField;

import java.util.List;

/**
 * @version 1.0
 * @Author nocease
 * @Date 2019/11/26 14:16
 * @Description 字段的service接口
 */
public interface FieldService {

    /**
     * 根据表单id获取该表单所有字段
     *
     * @param page
     * @param limit
     * @param tableId
     * @param str
     * @return
     */
    LayuiTable getAllFieldByForm(int page, int limit, String tableId, String str);

    List<MfField> getAllField(String tableId, String str);

    //添加字段
    void addField(MfField mf, String selectJson);

    //判断某个表单是否存在某个字段
    boolean ifHas(String tableId, String fieldName);

    //修改字段
    int updateField(MfField mfField);

    //根据id获取一个字段的详细信息
    MfField getOneFieldById(String fieldid);
}
