package com.nocease.service;

import com.nocease.pojo.LayuiTable;
import com.nocease.pojo.MfPageSetAdd;

import java.util.List;

/**
 * @version 1.0
 * @Author nocease
 * @date 2019/12/24 16:47
 * @Description
 */
public interface PageSetAddService {

    //获取一个页面中所有字段设置
    LayuiTable getFieldSetById(int page, int limit, String pageid);

    //获取一个字段add信息
    MfPageSetAdd getOneFieldAddSet(String fieldid);

    //修改某个字段是否提交
    boolean UpdFieldIsPost(String fieldid, String ispost);

    //修改某个字段属性
    boolean UpdFieldState(String fieldid, String state);

    //修改某个字段显示顺序
    boolean UpdFieldOrderBy(String fieldid, String orderby);

    //提交默认值
    boolean updDefaultValue(MfPageSetAdd mfPageSetAddsa);

    //初始化add页面！！！联查setadd表、字段表、selectValue表
    List<MfPageSetAdd> addHtmlInitialization(String pageid);
}
