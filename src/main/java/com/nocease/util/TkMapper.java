package com.nocease.util;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ExampleMapper;

/**
 * @version 1.0
 * @Author nocease
 * @Date 2019/11/10 16:05
 * @Description  baseMapper的泛型，普通mapper继承此接口可自动获取基本的增删改查
 */
public interface TkMapper<T> extends BaseMapper<T>,ExampleMapper<T> {}
