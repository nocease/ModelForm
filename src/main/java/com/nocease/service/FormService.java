package com.nocease.service;


import com.nocease.pojo.LayuiTable;
import com.nocease.pojo.MfForm;
import java.util.List;

/**
 * @version 1.0
 * @Author nocease
 * @Date 2019/11/12 21:18
 * @Description 表单的service接口
 */
public interface FormService {

    /**
     * @param page
     * @param limit
     * @param name
     * @return 获取全部表单，分页、搜索name
     */
    LayuiTable getAllForm(int page, int limit, String name);

    //获取全部表单
    List<MfForm> getAllForm();

    //添加表单
    void addForm(MfForm mf);

    //修改表单
    int updateForm(MfForm mf);

    boolean ifHas(String tableName);

    //根据id获取一个表单
    MfForm getOnrFormById(String id);

    //获取未初始化的全部表单
    List<MfForm> getAllFormNoPage(String pagetype);
}
