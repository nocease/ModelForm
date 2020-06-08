package com.nocease.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nocease.mapper.FieldMapper;
import com.nocease.mapper.PageMapper;
import com.nocease.mapper.PageSetAddMapper;
import com.nocease.pojo.LayuiTable;
import com.nocease.pojo.MfPage;
import com.nocease.pojo.MfPageSetAdd;
import com.nocease.service.FieldService;
import com.nocease.service.PageSetAddService;
import com.nocease.util.YamlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author nocease
 * @date 2019/12/24 16:49
 * @Description
 */
@Service
public class PageSetAddServiceImpl implements PageSetAddService {
    @Autowired
    private PageSetAddMapper psam;
    @Autowired
    private FieldMapper fm;
    @Autowired
    private FieldService fieldService;
    @Autowired
    private PageMapper pageMapper;

    //获取一个页面中所有字段设置
    @Override
    public LayuiTable getFieldSetById(int page, int limit, String pageid) {
        Example example = new Example(MfPageSetAdd.class);
        example.createCriteria().andLike("pageid", pageid);
        List<MfPageSetAdd> list = psam.selectByExample(example);
        for (MfPageSetAdd msa : list) {
            msa.setMfField(fm.selectByPrimaryKey(msa.getFieldid()));
        }
        PageHelper.startPage(page, limit);
        PageInfo pageinfo = new PageInfo(list);
        return new LayuiTable(pageinfo);
    }

    //获取一个a字段的add信息
    @Override
    public MfPageSetAdd getOneFieldAddSet(String fieldid) {
        Example example = new Example(MfPageSetAdd.class);
        example.createCriteria().andLike("fieldid", fieldid);
        return psam.selectOneByExample(example);
    }

    //修改某个字段是否提交
    @Override
    public boolean UpdFieldIsPost(String fieldid, String ispost) {
        Example example = new Example(MfPageSetAdd.class);
        example.createCriteria().andEqualTo("fieldid", fieldid);
        MfPageSetAdd mfPageSetAdd = new MfPageSetAdd();
        mfPageSetAdd.setIspost(ispost);
        if (psam.updateByExampleSelective(mfPageSetAdd, example) == 1)
            return true;
        else return false;
    }

    //修改某个字段属性
    @Override
    public boolean UpdFieldState(String fieldid, String state) {
        Example example = new Example(MfPageSetAdd.class);
        example.createCriteria().andEqualTo("fieldid", fieldid);
        MfPageSetAdd mfPageSetAdd = new MfPageSetAdd();
        mfPageSetAdd.setFieldstate(state);
        if (psam.updateByExampleSelective(mfPageSetAdd, example) == 1) {
            if (state.equals("2")) this.UpdFieldIsPost(fieldid, "1");
            return true;
        } else return false;
    }

    //修改某个字段显示顺序
    @Override
    public boolean UpdFieldOrderBy(String fieldid, String orderby) {
        Example example = new Example(MfPageSetAdd.class);
        example.createCriteria().andEqualTo("fieldid", fieldid);
        MfPageSetAdd mfPageSetAdd = new MfPageSetAdd();
        mfPageSetAdd.setOrderby(orderby);
        if (psam.updateByExampleSelective(mfPageSetAdd, example) == 1) {
            return true;
        } else return false;
    }

    //修改默认值
    @Override
    public boolean updDefaultValue(MfPageSetAdd mfPageSetAddsa) {
        Example example = new Example(MfPageSetAdd.class);
        example.createCriteria().andEqualTo("pageid", mfPageSetAddsa.getPageid()).andEqualTo("fieldid", mfPageSetAddsa.getFieldid());
        MfPageSetAdd mfPageSetAdd = new MfPageSetAdd();
        mfPageSetAdd.setDefaultvalue(mfPageSetAddsa.getDefaultvalue());
        psam.updateByExampleSelective(mfPageSetAdd, example);
        return true;
    }

    //初始化add页面！！！联查setadd表、字段表、selectValue表、页面表
    @Override
    public List<MfPageSetAdd> addHtmlInitialization(String pageid) {
        Example example = new Example(MfPageSetAdd.class);
        example.createCriteria().andEqualTo("pageid", pageid);
        example.setOrderByClause("orderby");
        List<MfPageSetAdd> list = psam.selectByExample(example);
        for (MfPageSetAdd mps : list) {
            mps.setMfField(fieldService.getOneFieldById(mps.getFieldid()));
        }
        if (list != null & list.size() > 0) {
            MfPage mfPage = new MfPage();
            mfPage = pageMapper.selectByPrimaryKey(pageid);
            mfPage.setUpFileUrl((String) ((Map) YamlUtil.getValue().get("file_system")).get("upURL"));
            list.get(0).setMfPage(mfPage);
        }

        return list;
    }


}
