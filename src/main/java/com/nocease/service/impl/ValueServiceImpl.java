package com.nocease.service.impl;

import com.nocease.mapper.ValueMapper;
import com.nocease.pojo.MfValue;
import com.nocease.service.ValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @version 1.0
 * @Author nocease
 * @date 2020/3/14 15:50
 * @Description
 */
@Service
public class ValueServiceImpl implements ValueService {
    @Autowired
    private ValueMapper vm;

    //添加一条
    @Override
    public void addValue(MfValue mv) {
        vm.insert(mv);
    }

    //根据id查询
    @Override
    public List<MfValue> getById(String id) {
        Example example = new Example(MfValue.class);
        example.createCriteria().andLike("fieldid", id);
        return vm.selectByExample(example);
    }
}
