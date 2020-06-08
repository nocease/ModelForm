package com.nocease.controller;

import com.nocease.mapper.PageMapper;
import com.nocease.pojo.LayuiTable;
import com.nocease.pojo.MfPage;
import com.nocease.service.PageService;
import com.nocease.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

/**
 * @version 1.0
 * @Author nocease
 * @date 2019/12/20 16:13
 * @Description
 */
@RestController
@RequestMapping("/page")
public class PageController {
    @Autowired
    private PageService ps;
    @Autowired
    private PageMapper pm;

    //获取全部page
    @RequestMapping("/list")
    public LayuiTable getAllPages(Integer page, Integer limit, String title) {
        return ps.getAllPages(MyUtil.null2o(page), MyUtil.null2o(limit), MyUtil.Null2Str(title));
    }

    //根据字段名修改
    @RequestMapping("/upd")
    @CacheEvict(value = {"addHtml", "getOnePage"}, key = "#mfPage.id")
    public int updateByField(MfPage mfPage) {
        return ps.updatePage(mfPage);
    }

    //修改页面状态
    @RequestMapping("/updateState")
    @CacheEvict(value = {"addHtml", "getOnePage"}, key = "#mfPage.id")
    public int updFormState(MfPage mfPage) {
        if (mfPage.getCanuse().equals("0"))
            mfPage.setCanuse("1");
        else if (mfPage.getCanuse().equals("1"))
            mfPage.setCanuse("0");
        return ps.updatePage(mfPage);
    }

    //新建页面
    @RequestMapping("/addPage")
    public int addPage(MfPage mfPage) {
        return ps.addPage(mfPage);
    }

    //获取某个表单的页面数量
    @RequestMapping("/isUseForm")
    public boolean isUseForm(String formid) {
        int count = ps.getPageCountByForm(formid);
        if (count == 0) {
            return false;
        } else {
            return true;
        }
    }

    //根据id获取一个page
    @RequestMapping("/getOnePage")
    public MfPage getOnePage(String id) {
        Example example = new Example(MfPage.class);
        example.createCriteria().andEqualTo("id", MyUtil.Null2Str(id));
        return pm.selectOneByExample(example);
    }

}
