package com.nocease.pojo;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Author nocease
 * @Date 2019/11/12 21:51
 * @Description 一个layui数据表的返回内容
 */
@Data
public class LayuiTable {
    private int code;
    private String msg;
    private List data = new ArrayList();
    private long count;

    public LayuiTable(PageInfo pi) {
        this.data = pi.getList();
        this.count = pi.getTotal();
        this.msg = "SUCCESS";
        this.code = 0;
    }

}
