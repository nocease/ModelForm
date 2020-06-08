package com.nocease.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nocease.mapper.FieldMapper;
import com.nocease.mapper.PageSetListMapper;
import com.nocease.pojo.LayuiTable;
import com.nocease.pojo.MfPageSetList;
import com.nocease.service.FieldService;
import com.nocease.service.PageSetListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Author nocease
 * @date 2020/3/19 14:21
 * @Description
 */
@Service
public class PageSetListServiceImpl implements PageSetListService {

    @Autowired
    private PageSetListMapper pageSetListMapper;
    @Autowired
    private FieldMapper fieldMapper;
    @Autowired
    private FieldService fieldService;

    //获取全部查询页面对象
    @Override
    public LayuiTable getFieldSetById(int page, int limit, String pageid) {
        Example example = new Example(MfPageSetList.class);
        example.createCriteria().andLike("pageid", pageid);
        List<MfPageSetList> list = pageSetListMapper.selectByExample(example);
        for (MfPageSetList msa : list) {
            msa.setMfField(fieldMapper.selectByPrimaryKey(msa.getFieldid()));
        }
        PageHelper.startPage(page, limit);
        PageInfo pageinfo = new PageInfo(list);
        return new LayuiTable(pageinfo);
    }

    //修改一个字段
    @Override
    public boolean autoUpd(MfPageSetList mfPageSetList) {
        Example example = new Example(MfPageSetList.class);
        example.createCriteria().andLike("fieldid", mfPageSetList.getFieldid());
        pageSetListMapper.updateByExampleSelective(mfPageSetList, example);
        return true;
    }

    //获取全部setlist，联查字段表、多选表
    @Override
    public List<MfPageSetList> getPageSetList(String pageid) {
        Example example = new Example(MfPageSetList.class);
        example.createCriteria().andEqualTo("pageid", pageid);
        example.setOrderByClause("orderby");
        List<MfPageSetList> list = pageSetListMapper.selectByExample(example);
        for (MfPageSetList mps : list) {
            mps.setMfField(fieldService.getOneFieldById(mps.getFieldid()));
        }
        return list;
    }

    //cansearch的全部数据库表字段名
    @Override
    public List<String> getTableNameWhereCanSearch(String pageid) {
        Example example = new Example(MfPageSetList.class);
        example.createCriteria().andEqualTo("pageid", pageid).andEqualTo("canSearch", "1");
        List<MfPageSetList> mfPageSetList = pageSetListMapper.selectByExample(example);
        List<String> list = new ArrayList<String>();
        if (mfPageSetList != null & mfPageSetList.size() > 0) {
            for (MfPageSetList mpsl : mfPageSetList) {
                String fieldName = fieldMapper.selectByPrimaryKey(mpsl.getFieldid()).getFieldName();
                list.add(fieldName);
            }
        }
        return list;
    }
}
