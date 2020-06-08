package com.nocease.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nocease.mapper.FormMapper;
import com.nocease.pojo.LayuiTable;
import com.nocease.pojo.MfForm;
import com.nocease.service.FormService;
import com.nocease.util.MyDataSource;
import com.nocease.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @version 1.0
 * @Author nocease
 * @Date 2019/11/12 21:18
 * @Description
 */
@Service
public class FormServiceImpl implements FormService {
    @Autowired
    private FormMapper fm;

    @Autowired
    private MyDataSource mds;

    @Override
    public LayuiTable getAllForm(int page, int limit, String name) {
        Example example = new Example(MfForm.class);
        example.createCriteria()
                .orLike("formName", MyUtil.toLike(name))
                .orLike("formTablename", MyUtil.toLike(name))
                .orLike("formDescribe", MyUtil.toLike(name));
        PageHelper.startPage(page, limit);
        PageInfo pageinfo = new PageInfo(fm.selectByExample(example));
        return new LayuiTable(pageinfo);
    }

    @Override
    public void addForm(MfForm mf) {
        mf.setFormId(MyUtil.getUUID());
        mf.setFormTime(MyUtil.getNowDate(0));
        //建表
        CreatTable(mf.getFormTablename());
        //记录
        fm.insertSelective(mf);
    }

    @Override
    public int updateForm(MfForm mf) {
        return fm.updateByPrimaryKeySelective(mf);
    }

    //判断数据库表名是否存在
    @Override
    public boolean ifHas(String tableName) {
        Example example = new Example(MfForm.class);
        example.createCriteria()
                .andLike("formTablename", tableName);
        int n = fm.selectCountByExample(example);
        return n == 0 ? false : true;
    }

    //根据id获取一个表单
    @Override
    public MfForm getOnrFormById(String id) {
        MfForm mfForm = new MfForm();
        mfForm.setFormId(id);
        return fm.selectOne(mfForm);
    }

    //获取未初始化的全部表单
    @Override
    public List<MfForm> getAllFormNoPage(String pagetype) {
        List<MfForm> list = fm.getAllNoPageForm(pagetype);
        return list;
    }

    //获取全部表单
    @Override
    public List<MfForm> getAllForm() {
        return fm.selectAll();
    }

    //建表,支持多种数据库
    //SQLServer 2005及以上版本、Oracle 9i及以上版本、Mysql5.7及以上版本、达梦7、神通、南大通用
    private void CreatTable(String tableName) {
        //获取当前连接的数据库类型
        String sqlType = mds.getSqlType();

        //建表sql语句
        String sql = "";

        if ("oracle".equals(sqlType)) {
            sql = "create table " + tableName + "(id varchar(100) primary key not null, time varchar(100), pageid varchar(100))";
        } else if ("mysql".equals(sqlType)) {
            sql = "create table `" + tableName + "` (id varchar(100) NOT NULL,time varchar(100), pageid varchar(100),PRIMARY KEY (`id`) )";
        } else {
            sql = "create table " + tableName + "(id varchar(100) primary key CLUSTERED, time varchar(100),pageid varchar(100))";
        }

        //执行sql
        fm.doSql(sql);
    }
}
