package com.nocease.service;

import com.nocease.pojo.LayuiTable;
import com.nocease.pojo.MfPageSetList;

import java.util.List;

/**
 * @version 1.0
 * @Author nocease
 * @date 2020/3/19 14:20
 * @Description
 */
public interface PageSetListService {
    //获取全部查询页面对象
    LayuiTable getFieldSetById(int null2o, int null2o1, String null2Str);

    //修改一个字段
    boolean autoUpd(MfPageSetList mfPageSetList);

    //获取全部setlist，联查字段表、多选表
    List<MfPageSetList> getPageSetList(String pageid);

    //cansearch的全部数据库表字段名
    List<String> getTableNameWhereCanSearch(String pageid);
}
