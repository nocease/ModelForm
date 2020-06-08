package com.nocease.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nocease.mapper.FieldMapper;
import com.nocease.mapper.FormMapper;
import com.nocease.pojo.LayuiTable;
import com.nocease.pojo.MfField;
import com.nocease.pojo.MfForm;
import com.nocease.pojo.MfValue;
import com.nocease.service.FieldService;
import com.nocease.service.PageSetAddService;
import com.nocease.service.ValueService;
import com.nocease.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @version 1.0
 * @Author nocease
 * @date 2019/11/26 14:20
 * @Description 表单字段的service
 */
@Service
public class FieldServiceImpl implements FieldService {

    @Autowired
    private FieldMapper fm;
    @Autowired
    private FormMapper formMapper;
    @Autowired
    private ValueService vs;
    @Autowired
    private PageSetAddService pageSetAddService;


    /**
     * 根据表单id获取该表单所有字段
     *
     * @param page
     * @param limit
     * @param tableId
     * @param str
     * @return
     */
    @Override
    public LayuiTable getAllFieldByForm(int page, int limit, String tableId, String str) {
        PageHelper.startPage(page, limit);
        PageInfo pageinfo = new PageInfo(getAllField(tableId, str));
        return new LayuiTable(pageinfo);
    }

    //根据id获取表单字段，不分页，先把主键、time手动放进去
    public List<MfField> getAllField(String tableId, String str) {
        Example example = new Example(MfField.class);
        example.createCriteria()
                .andLike("formId", tableId)
                .andLike("fieldNameDesc", MyUtil.toLike(str));
        List<MfField> list = fm.selectByExample(example);
        //选择框的情况下，取可选值
        for (MfField mf : list) {
            if (mf.getFieldType().equals("2") || mf.getFieldType().equals("3")) {
                String selectId = mf.getFieldSelectValue();
                List<MfValue> mfvalues = vs.getById(selectId);
                String selectItem = "";
                if (mfvalues != null && mfvalues.size() > 0) {
                    for (MfValue mv : mfvalues) {
                        selectItem += mv.getValue() + ",";
                    }
                    selectItem = selectItem.substring(0, selectItem.length() - 1);
                }
                mf.setFieldSelectValue(selectItem);
            }
        }
        // list.add(new MfField("系统默认", tableId, "pageid", "对应页面id"));
        // list.add(new MfField("系统默认", tableId, "time", "新建时间"));
        // list.add(new MfField("系统默认", tableId, "id", "主键uuid"));
        return list;
    }

    //判断某个表单是否存在某个字段
    @Override
    public boolean ifHas(String tableId, String fieldName) {
        Example example = new Example(MfField.class);
        example.createCriteria().andLike("formId", tableId).andLike("fieldName", fieldName);
        int n = fm.selectCountByExample(example);
        return n == 0 ? false : true;
    }

    //修改字段
    @Override
    public int updateField(MfField mfField) {
        return fm.updateByPrimaryKeySelective(mfField);
    }

    //根据id获取一个字段的详细信息
    @Override
    public MfField getOneFieldById(String fieldid) {
        Example example = new Example(MfField.class);
        example.createCriteria().andLike("fieldId", fieldid);
        MfField mf = fm.selectOneByExample(example);
        //追加上默认值
        mf.setDefaultValue(pageSetAddService.getOneFieldAddSet(mf.getFieldId()).getDefaultvalue());
        //选项id不是空
        if (!MyUtil.Null2Str(mf.getFieldSelectValue()).equals("")) {
            mf.setMfValues(vs.getById(mf.getFieldSelectValue()));
        }
        //正则表达式不是空
        if (!MyUtil.Null2Str(mf.getFieldZzTest()).equals(""))
            mf.setFieldZzTest(mf.getFieldZzTest().substring(1, mf.getFieldZzTest().length() - 1));
        return mf;
    }

    //添加字段
    @Override
    public void addField(MfField mf, String selectJson) {
        mf.setFieldId(MyUtil.getUUID());
        mf.setFieldTime(MyUtil.getNowDate(0));

        //加字段
        alterField(mf.getFormId(), mf.getFieldName());
        //处理文件、时间、选择框情况下的字段
        if (!mf.getFieldType().equals("5")) mf.setFieldFiletype("");
        if (!mf.getFieldType().equals("6")) mf.setFieldTimetype("");
        if (mf.getFieldType().equals("2") || mf.getFieldType().equals("3")) mf.setFieldSelectValue(MyUtil.getUUID());
        //记录到字段表
        fm.insertSelective(mf);
        //记录到选项表
        if (mf.getFieldType().equals("2") || mf.getFieldType().equals("3")) {
            if (selectJson != "") {
                String[] strs = selectJson.split(",");
                for (String str : strs) {
                    MfValue mfvalue = new MfValue(mf.getFieldSelectValue(), str);
                    vs.addValue(mfvalue);
                }
            }
        }
    }

    //向表中添加字段
    private void alterField(String tableId, String fieldName) {
        //根据表id查表名
        Example example = new Example(MfForm.class);
        example.createCriteria().andEqualTo("formId", tableId);
        String tableName = formMapper.selectOneByExample(example).getFormTablename();
        //向该表加字段
        String sql = "alter table " + tableName + " add " + fieldName + " varchar(255)";
        formMapper.doSql(sql);
    }
}
