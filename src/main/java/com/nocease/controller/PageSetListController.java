package com.nocease.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nocease.mapper.FormMapper;
import com.nocease.mapper.PageMapper;
import com.nocease.pojo.*;
import com.nocease.service.FormService;
import com.nocease.service.PageService;
import com.nocease.service.PageSetListService;
import com.nocease.util.MyUtil;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author nocease
 * @date 2020/3/19 14:32
 * @Description
 */
@RestController
@RequestMapping("/pageAddList")
public class PageSetListController {

    @Autowired
    private PageSetListService pageSetListService;
    @Autowired
    private PageMapper pageMapper;
    @Autowired
    private PageService pageService;
    @Autowired
    private FormMapper formMapper;
    @Autowired
    private FormService formService;

    //获取一个页面中所有字段设置
    @RequestMapping("/getFieldSetById")
    public LayuiTable getFieldSetById(String pageid, Integer limit, Integer page) {
        return pageSetListService.getFieldSetById(MyUtil.null2o(page), MyUtil.null2o(limit), MyUtil.Null2Str(pageid));
    }

    //根据id、key、value修改一个字段值
    @CacheEvict(value = "listHtml", key = "#mypageid")
    @RequestMapping("/autoUpd")
    public boolean autoUpd(MfPageSetList mfPageSetList, String mypageid) {
        return pageSetListService.autoUpd(mfPageSetList);
    }

    //根据id获取一个page
    @RequestMapping("/getOnePageById")
    @Cacheable(value = "getOnePage", key = "#pageid")
    public MfPage getOnePageById(String pageid) {
        return pageMapper.selectByPrimaryKey(MyUtil.Null2Str(pageid));
    }

    //！！！获取全部setlist，联查字段表、多选表（生成页面）
    @RequestMapping("/getPageSetList")
    @Cacheable(value = "listHtml", key = "#pageid")
    public List<MfPageSetList> getPageSetList(String pageid) {
        return pageSetListService.getPageSetList(MyUtil.Null2Str(pageid));
    }

    //!!根据页面id获取layuitable（获取页面数据）
    @RequestMapping("/getTableByPage")
    public LayuiTable getTableByPage(String pageid, String str, Integer page, Integer limit) {
        String tableName = pageService.gerTableNameByPageid(MyUtil.Null2Str(pageid));//表名
        List<String> FieldNames = pageSetListService.getTableNameWhereCanSearch(MyUtil.Null2Str(pageid));
        String sql = "select * from " + tableName;
        String wheresql = "";
        str = MyUtil.toLike(str);
        if (FieldNames != null && FieldNames.size() > 0) {
            for (String field : FieldNames) {
                wheresql += " " + field + " like '" + str + "' or";
            }
            wheresql = wheresql.substring(0, wheresql.length() - 2);
            sql += " where" + wheresql;
        }
        PageHelper.startPage(MyUtil.null2o(page), MyUtil.null2o(limit));
        List<Map<String, String>> resault = formMapper.getResault(sql);
        //某些数据库取出来是大写，转化一下
        if (resault != null && resault.size() > 0) {
            if (resault.get(0).get("id") == null) {
                for (Map<String, String> map : resault) {
                    map.put("id", map.get("ID"));
                    map.put("time", map.get("TIME"));
                    map.put("pageid", map.get("PAGEID"));
                }
            }
        }
        PageInfo pageinfo = new PageInfo(resault);
        return new LayuiTable(pageinfo);
    }

    //根据表id和删除一行
    @RequestMapping("/delByForm")
    public boolean delByForm(String formid, String id) {
        String formTablename = formService.getOnrFormById(MyUtil.Null2Str(formid)).getFormTablename();//数据库表名
        String sql = "delete from " + MyUtil.Null2Str(formTablename) + " where id='" + MyUtil.Null2Str(id) + "'";
        formMapper.doSql(sql);
        return true;
    }

    //根据查询页面id获取对应的添加页面id
    @RequestMapping("/getAddPageByListPage")
    public String getAddPageByListPage(String pageid) {
        String formid = pageMapper.selectByPrimaryKey(MyUtil.Null2Str(pageid)).getFormid();
        Example example = new Example(MfPage.class);
        example.createCriteria().andEqualTo("formid", formid).andEqualTo("pagetype", "1");
        return pageMapper.selectOneByExample(example).getId();
    }

    //根据formid和id获取一个form
    @RequestMapping("/getTableOneRow")
    public Map<String, String> getTableOneRow(String formid, String id) {
        String formTablename = formService.getOnrFormById(MyUtil.Null2Str(formid)).getFormTablename();//数据库表名
        String sql = "select * from " + formTablename + " where  id='" + MyUtil.Null2Str(id) + "'";
        return formMapper.getOneResult(sql);
    }

    //修改一行
    @RequestMapping("/updateOneRow")
    public boolean updateOneRow(@RequestBody JSONObject jsonParam) {
        try {
            PostUpdateData postUpdateData = JSON.parseObject(jsonParam.toJSONString(), PostUpdateData.class);
            String tableName = pageService.gerTableNameByPageid(postUpdateData.getPageid());//数据库表名
            List<PostValue> list = postUpdateData.getData();
            String id = postUpdateData.getId();
            String sets = "";
            if (list != null && list.size() > 0) {
                for (PostValue pv : list) {
                    sets += pv.getKey() + "='" + pv.getValue() + "',";
                }
                sets = sets.substring(0, sets.length() - 1);
            }
            String sql = "update " + tableName + " set " + sets + " where id='" + id + "'";
            formMapper.doSql(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
