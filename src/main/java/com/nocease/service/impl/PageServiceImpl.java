package com.nocease.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nocease.mapper.*;
import com.nocease.pojo.LayuiTable;
import com.nocease.pojo.MfPage;
import com.nocease.pojo.MfPageSetAdd;
import com.nocease.pojo.MfPageSetList;
import com.nocease.service.PageService;
import com.nocease.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @version 1.0
 * @Author nocease
 * @date 2019/12/20 15:39
 * @Description page方法
 */
@Service
public class PageServiceImpl implements PageService {
    @Autowired
    private PageMapper pm;
    @Autowired
    private PageSetAddMapper psam;
    @Autowired
    private FieldMapper fm;
    @Autowired
    private FormMapper formMapper;
    @Autowired
    private PageSetListMapper pageSetListMapper;

    //获取全部page
    @Override
    public LayuiTable getAllPages(int page, int limit, String name) {
        Example example = new Example(MfPage.class);
        example.createCriteria()
                .orLike("title", MyUtil.toLike(name))
                .orLike("message", MyUtil.toLike(name));
        PageHelper.startPage(page, limit);
        PageInfo pageinfo = new PageInfo(pm.selectByExample(example));
        return new LayuiTable(pageinfo);
    }

    //修改字段
    @Override
    public int updatePage(MfPage mfPage) {
        return pm.updateByPrimaryKeySelective(mfPage);
    }

    //新建页面
    @Override
    public int addPage(MfPage mfPage) {
        String pageid = MyUtil.getUUID();
        //根据表单id获取全部字段id
        List<String> fileds = fm.getFieldsIdByForm(mfPage.getFormid());

        if (mfPage.getPagetype().equals("1")) {
            //在添加页面表里加数据
            MfPageSetAdd mps = new MfPageSetAdd();
            mps.setPageid(pageid);//pageid
            mps.setIspost("1");//可提交
            mps.setFieldstate("1");//可编辑
            mps.setOrderby("0");//显示顺序
            for (String fieldId : fileds) {
                mps.setFieldid(fieldId);
                psam.insertSelective(mps);
            }
        } else if (mfPage.getPagetype().equals("0")) {
            //在查询页面里增加数据
            mfPage.setPagelimit("10");
            MfPageSetList mfPageSetList = new MfPageSetList();
            mfPageSetList.setPageid(pageid);
            mfPageSetList.setCanSearch("0");
            mfPageSetList.setCanSort("0");
            mfPageSetList.setOrderby("0");
            for (String fieldId : fileds) {
                mfPageSetList.setFieldid(fieldId);
                pageSetListMapper.insertSelective(mfPageSetList);
            }
        }


        //在页面表加数据
        mfPage.setId(pageid);
        mfPage.setCtime(MyUtil.getNowDate(0));
        mfPage.setCanuse("1");
        mfPage.setHtmlWidth("80");
        return pm.insertSelective(mfPage);
    }

    //查询某个表单 初始化页面的数量
    @Override
    public int getPageCountByForm(String formid) {
        MfPage mfPage = new MfPage();
        mfPage.setFormid(formid);
        return pm.selectCount(mfPage);
    }

    //根据页面id找到表单id，再找到表名返回
    @Override
    public String gerTableNameByPageid(String pageid) {
        String formid = pm.selectByPrimaryKey(pageid).getFormid();
        return formMapper.selectByPrimaryKey(formid).getFormTablename();
    }

}
