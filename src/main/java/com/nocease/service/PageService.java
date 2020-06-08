package com.nocease.service;

import com.nocease.pojo.LayuiTable;
import com.nocease.pojo.MfPage;

/**
 * @version 1.0
 * @Author nocease
 * @date 2019/12/20 15:39
 * @Description page的接口
 */
public interface PageService {
    //查询全部
    LayuiTable getAllPages(int page, int limit, String title);

    //根据字段修改页面
    int updatePage(MfPage mfPage);

    //新建页面
    int addPage(MfPage mfPage);

    //查询某个表单创建页面的数量
    int getPageCountByForm(String formid);

    //根据页面id找到表单id，再找到表名返回
    String gerTableNameByPageid(String pageid);
}
