package com.nocease.controller;

import com.nocease.pojo.LayuiTable;
import com.nocease.pojo.MfField;
import com.nocease.service.FieldService;
import com.nocease.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version 1.0
 * @Author nocease
 * @date 2019/11/26 14:53
 * @Description 表单字段的controller
 */
@RestController
@RequestMapping("/field")
public class FieldController {
    @Autowired
    private FieldService fs;

    //添加字段
    @RequestMapping("/add")
    @Transactional
    public boolean addField(MfField mf, String selectValueJson) {
        try {
            if (fs.ifHas(MyUtil.Null2Str(mf.getFormId()), MyUtil.Null2Str(mf.getFieldName())))
                return false;
            else {
                fs.addField(mf, MyUtil.Null2Str(selectValueJson));
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    //判断是否存在
    @RequestMapping("/isHas")
    public boolean isHas(String tableId, String fieldName) {
        if (fieldName.equalsIgnoreCase("id") || fieldName.equalsIgnoreCase("time"))
            return true;
        else
            return fs.ifHas(MyUtil.Null2Str(tableId), MyUtil.Null2Str(fieldName));
    }

    //根据id获取一个表单的全部字段layui
    @RequestMapping("/get")
    public LayuiTable getAllFieldByTable(Integer page, Integer limit, String tableId, String str) {
        return fs.getAllFieldByForm(MyUtil.null2o(page), MyUtil.null2o(limit), MyUtil.Null2Str(tableId), MyUtil.Null2Str(str));
    }


    //根据id获取一个表单的全部字段
    @RequestMapping("/getFormFieldById")
    public List<MfField> getFormFieldById(String id) {
        return fs.getAllField(MyUtil.Null2Str(id), "");
    }


    //修改字段
    @RequestMapping("/upd")
    public int updForm(MfField MfField) {
        return fs.updateField(MfField);
    }

    //根据id获取一个字段的详细信息
    @RequestMapping("/getOneFieldById")
    public MfField getOneFieldById(String fieldid) {
        return fs.getOneFieldById(MyUtil.Null2Str(fieldid));
    }

}
