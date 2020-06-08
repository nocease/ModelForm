package com.nocease.controller;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.nocease.mapper.FormMapper;
import com.nocease.pojo.LayuiTable;
import com.nocease.pojo.MfPageSetAdd;
import com.nocease.pojo.PostData;
import com.nocease.pojo.PostValue;
import com.nocease.service.PageService;
import com.nocease.service.PageSetAddService;
import com.nocease.util.MyUtil;
import com.nocease.util.YamlUtil;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.util.*;

/**
 * @version 1.0
 * @Author nocease
 * @date 2019/12/24 17:00
 * @Description
 */
@RestController
@RequestMapping("/pageAddSet")
public class PageSetAddController {
    @Autowired
    private PageSetAddService pageSetAddService;
    @Autowired
    private PageService pageService;
    @Autowired
    private FormMapper fm;

    //获取一个页面中所有字段设置
    @RequestMapping("/getFieldSetById")
    public LayuiTable getFieldSetById(String pageid, Integer limit, Integer page) {
        return pageSetAddService.getFieldSetById(MyUtil.null2o(page), MyUtil.null2o(limit), MyUtil.Null2Str(pageid));
    }

    //修改某个字段是否提交
    @CacheEvict(value = "addHtml", key = "#pageid")
    @RequestMapping("/UpdFieldIsPost")
    public boolean UpdFieldIsPost(String fieldid, String ispost, String pageid) {
        return pageSetAddService.UpdFieldIsPost(MyUtil.Null2Str(fieldid), ispost.equals("0") ? "1" : "0");
    }

    //修改某个字段属性
    @CacheEvict(value = "addHtml", key = "#pageid")
    @RequestMapping("/UpdFieldState")
    public boolean UpdFieldState(String fieldid, String state, String pageid) {
        return pageSetAddService.UpdFieldState(MyUtil.Null2Str(fieldid), MyUtil.Null2Str(state));
    }

    //修改某个字段显示顺序
    @CacheEvict(value = "addHtml", key = "#pageid")
    @RequestMapping("/UpdFieldOrderBy")
    public boolean UpdFieldOrderBy(String fieldid, String orderby, String pageid) {
        return pageSetAddService.UpdFieldOrderBy(MyUtil.Null2Str(fieldid), MyUtil.Null2Str(orderby));
    }

    //提交默认值
    @CacheEvict(value = "addHtml", key = "#mfPageSetAddsa.pageid")
    @RequestMapping("/updDefaultValue")
    public boolean updDefaultValue(MfPageSetAdd mfPageSetAddsa) {
        return pageSetAddService.updDefaultValue(mfPageSetAddsa);
    }

    //初始化add页面！！！联查setadd表、字段表、selectValue表、页面表
    @RequestMapping("/addHtmlInitialization")
    @Cacheable(value = "addHtml", key = "#pageid")
    public List<MfPageSetAdd> addHtmlInitialization(String pageid) {
        return pageSetAddService.addHtmlInitialization(MyUtil.Null2Str(pageid));
    }

    //提交add页面!!
    @RequestMapping("/addHtmlPost")
    public boolean addHtmlPost(@RequestBody JSONObject jsonParam) {
        try {
            PostData postData = JSON.parseObject(jsonParam.toJSONString(), PostData.class);
            String tableName = pageService.gerTableNameByPageid(postData.getPageid());//数据库表名
            List<PostValue> list = postData.getData();
            String fields = "";
            String values = "";
            if (list != null && list.size() > 0) {
                for (PostValue pv : list) {
                    fields += "," + pv.getKey();
                    values += ",'" + pv.getValue() + "'";
                }
            }
            String sql = "insert into " + tableName + "(id,time,pageid" + fields + ") values('" + MyUtil.getUUID() + "','" + MyUtil.getNowDate(0) + "','" + postData.getPageid() + "'" + values + ")";
            fm.doSql(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    //上传到七牛云
    @RequestMapping("/upToQiniu")
    public Map<String, Object> upToQiniu(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map prop = (Map) YamlUtil.getValue().get("file_system");
        try {
            Collection<Part> parts = request.getParts();
            for (Iterator<Part> iterator = parts.iterator(); iterator.hasNext(); ) {
                Part part = iterator.next();
                InputStream inputStream = part.getInputStream();
                // 3.得到文件名
                // 3.1得到头:form-data; name="photo"; filename="cs10005.jpg"
                String header = part.getHeader("Content-Disposition");
                // 3.2根据头得到filename的值
                String filename = header.substring(header.indexOf("filename=") + 10, header.length() - 1);
                // 文件后缀名
                String etc = filename.substring(filename.lastIndexOf(".") + 1);
                //构造一个带指定 Region 对象的配置类，指定上传到华北
                Configuration cfg = new Configuration(Region.autoRegion());
                //...其他参数参考类注释
                UploadManager uploadManager = new UploadManager(cfg);
                //...生成上传凭证，然后准备上传
                String accessKey = (String) prop.get("accessKey");
                String secretKey = (String) prop.get("secretKey");
                String bucket = (String) prop.get("bucket");
                //默认不指定key的情况下，以文件内容的hash值作为文件名
                String key = MyUtil.getUUID();
                Auth auth = Auth.create(accessKey, secretKey);
                String upToken = auth.uploadToken(bucket);
                Response response = uploadManager.put(inputStream, key + "." + etc, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                map.put("code", "200");
                map.put("msg", "success！");
                map.put("fileName", filename);
                map.put("url", (String) prop.get("domain") + key + "." + etc);
            }
            return map;
        } catch (Exception e) {
            map.put("code", "500");
            map.put("msg", e.getMessage());
            map.put("fileName", "");
            map.put("url", "");
            return map;
        }
    }

}
