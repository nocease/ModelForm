package com.nocease.mapper;

import com.nocease.pojo.MfField;
import com.nocease.util.TkMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @version 1.0
 * @Author nocease
 * @date 2019/11/26 14:13
 * @Description 表单字段的mapper
 */
public interface FieldMapper extends TkMapper<MfField> {
    //根据表单id获取全部字段id
    @Select("select field_id from mf_field where form_id=#{formId}")
    List<String> getFieldsIdByForm(String formId);
}
