package com.nocease.controller;

import com.nocease.pojo.LayuiTable;
import com.nocease.pojo.MfForm;
import com.nocease.pojo.MfFormName;
import com.nocease.service.FormService;
import com.nocease.util.MyDataSource;
import com.nocease.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Author nocease
 * @Date 2019/11/12 15:59
 * @Description form的controller
 */
@RestController
@RequestMapping("/form")
public class FormController {
    @Autowired
    private FormService fs;

    @Autowired
    private MyDataSource mds;

    //获取全部表单
    @RequestMapping("/get")
    public LayuiTable getAllForm(Integer page, Integer limit, String name) {
        return fs.getAllForm(MyUtil.null2o(page), MyUtil.null2o(limit), MyUtil.Null2Str(name));
    }

    //添加
    @RequestMapping("/add")
    @Transactional
    public boolean addForm(MfForm mfForm) {
        try {
            fs.addForm(mfForm);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //修改表单
    @RequestMapping("/upd")
    public int updForm(MfForm mfForm) {
        return fs.updateForm(mfForm);
    }

//    //修改表单状态
//    @RequestMapping("/updateState")
//    public int updFormState(MfForm mfForm) {
//        if (mfForm.getFormState().equals("0"))
//            mfForm.setFormState("1");
//        else if (mfForm.getFormState().equals("1"))
//            mfForm.setFormState("0");
//        return fs.updateForm(mfForm);
//    }

    //判断表单表名是否存在
    @RequestMapping("/ifHas")
    public boolean ifHas(String tableName) {
        return fs.ifHas(MyUtil.Null2Str(tableName));
    }

    //根据id获取一个表单
    @RequestMapping("/getOnrFormById")
    public MfForm getOnrFormById(String id) {
        return fs.getOnrFormById(MyUtil.Null2Str(id));
    }

//    //获取全部表单id，名称，表名
//    @RequestMapping("/getAllForm")
//    public List<MfFormName> getAllForm() {
//        List<MfFormName> list = new ArrayList<MfFormName>();
//        List<MfForm> allForm = fs.getAllForm();
//        for (MfForm f : allForm) {
//            MfFormName formName = new MfFormName();
//            formName.setId(f.getFormId());
//            formName.setFormName(new StringBuffer().append(f.getFormName()).append(":").append(f.getFormTablename()).toString());
//            list.add(formName);
//        }
//        return list;
//    }

    //获取未初始化的全部表单（id，名称，表名，表单类型）
    @RequestMapping("/getAllFormNoPage")
    public List<MfFormName> getAllFormNoPage(String pagetype) {
        List<MfFormName> list = new ArrayList<MfFormName>();
        List<MfForm> allForm = fs.getAllFormNoPage(pagetype);
        for (MfForm f : allForm) {
            MfFormName formName = new MfFormName();
            formName.setId(f.getFormId());
            formName.setFormName(new StringBuffer().append(f.getFormName()).append(":").append(f.getFormTablename()).toString());
            list.add(formName);
        }
        return list;
    }
}
