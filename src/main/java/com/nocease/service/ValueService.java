package com.nocease.service;

import com.nocease.pojo.MfValue;

import java.util.List;

/**
 * 选项值的service接口
 */
public interface ValueService {
    //添加一条
    void addValue(MfValue mv);

    //根据id查全部
    List<MfValue> getById(String id);
}
