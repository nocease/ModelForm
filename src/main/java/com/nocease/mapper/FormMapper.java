package com.nocease.mapper;

import com.nocease.pojo.MfForm;
import com.nocease.util.TkMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author nocease
 * @Date 2019/11/12 16:00
 * @Description form的mapper
 */
public interface FormMapper extends TkMapper<MfForm> {
    //执行传入的sql语句
    @Insert("${sql}")
    int doSql(String sql);

    //执行传入的sql语句返回lsit<map>
    @Select("${sql}")
    List<Map<String, String>> getResault(String sql);

    //执行传入的sql语句返回map
    @Select("${sql}")
    Map<String, String> getOneResult(String sql);

    //获取未初始化的全部表单
    @Select("select * from mf_form where form_id not in(select formid from mf_page where pagetype=#{pagetype})")
    List<MfForm> getAllNoPageForm(String pagetype);

}
