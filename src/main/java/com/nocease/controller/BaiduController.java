package com.nocease.controller;

import com.nocease.mapper.FormMapper;
import com.nocease.pojo.LayuiTable;
import com.nocease.pojo.MfBaiduStatistics;
import com.nocease.service.BaiduStatisticsService;
import com.nocease.util.MyUtil;
import com.nocease.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author nocease
 * @date 2020/4/2 15:050
 * @Description
 */
@RestController
@RequestMapping("/baidu")
public class BaiduController {
    @Autowired
    private BaiduStatisticsService baiduStatisticsService;
    @Autowired
    private FormMapper fm;
    @Autowired
    private RedisUtil redisUtil;

    //查看全部
    @RequestMapping("/list")
    public LayuiTable list(Integer page, Integer limit, String str) {
        return new LayuiTable(baiduStatisticsService.list(MyUtil.null2o(page), MyUtil.null2o(limit), MyUtil.Null2Str(str)));
    }

    //修改
    @RequestMapping("/upd")
    public int update(MfBaiduStatistics mbs) {
        return baiduStatisticsService.update(mbs);
    }

    //删除
    @RequestMapping("/del")
    public void delete(String id) {
        redisUtil.delByStr("baidu");
        //删除一列和删除所有对应的页面
        String bdId = MyUtil.Null2Str(id);
        fm.doSql("delete from mf_baidu_statistics where bd_id='" + bdId + "'");
        fm.doSql("delete from mf_baidu_page where bd_id='" + bdId + "'");
    }

    //添加
    @RequestMapping("/add")
    public int add(MfBaiduStatistics mbs) {
        return baiduStatisticsService.add(mbs);
    }

    //添加页面
    @RequestMapping("/addpage")
    public void addpage(String[] pageids, String bdId) {
        redisUtil.delByStr("baidu");
        //删除已添加的全部，再添加新的
        fm.doSql("delete from mf_baidu_page where bd_id='" + MyUtil.Null2Str(bdId) + "'");
        if (pageids != null && pageids.length > 0) {
            for (String pageid : pageids) {
                try {
                    fm.doSql("insert into mf_baidu_page(pageid,bd_id) values('" + pageid + "','" + MyUtil.Null2Str(bdId) + "')");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("错误！一个页面最多加入一个统计代码");
                }
            }
        }
    }

    //根据pageid获取统计js
    @RequestMapping("/getJSbypage")
    @Cacheable(value = "baidu", key = "#pageid")
    public String getJSbypage(String pageid) {
        try {
            return baiduStatisticsService.getOne(MyUtil.Null2Str(pageid)).getBdJs().replace("<script>", "").replace("</script>", "");
        } catch (Exception e) {
            return "";
        }
    }

    //获取全部可选页面
    @RequestMapping("/getAllPage")
    public List<Map<String, String>> getAllPage() {
        List<Map<String, String>> list = fm.getResault("select id,title from mf_page where id not in(select pageid from mf_baidu_page)");
        if (list != null && list.size() > 0) {
            if (list.get(0).get("id") == null) {
                for (Map<String, String> map : list) {
                    map.put("id", map.get("ID"));
                    map.put("title", map.get("TITLE"));
                }
            }
        }
        return list;
    }

    //获取全部已选页面
    @RequestMapping("/getAllisPage")
    public List<Map<String, String>> getAllisPage(String bdId) {
        List<Map<String, String>> list = fm.getResault("select mf_page.id,mf_page.title from mf_page,mf_baidu_page where mf_baidu_page.bd_id='" + MyUtil.Null2Str(bdId) + "' and mf_baidu_page.pageid=mf_page.id");
        if (list != null && list.size() > 0) {
            if (list.get(0).get("id") == null) {
                for (Map<String, String> map : list) {
                    map.put("id", map.get("ID"));
                    map.put("title", map.get("TITLE"));
                }
            }
        }
        return list;
    }


}
