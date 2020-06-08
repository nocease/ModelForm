package com.nocease.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nocease.mapper.BaiduPageMapper;
import com.nocease.mapper.BaiduStatisticsMapper;
import com.nocease.pojo.MfBaiduPage;
import com.nocease.pojo.MfBaiduStatistics;
import com.nocease.service.BaiduStatisticsService;
import com.nocease.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @version 1.0
 * @Author nocease
 * @date 2020/4/2 15:02
 * @Description
 */
@Service
public class BaiduStatisticsServiceImpl implements BaiduStatisticsService {
    @Autowired
    private BaiduPageMapper baiduPageMapper;
    @Autowired
    private BaiduStatisticsMapper baiduStatisticsMapper;

    @Override
    public PageInfo list(int page, int limit, String str) {
        PageHelper.startPage(page, limit);
        PageInfo pageinfo = new PageInfo(this.list(str));
        return pageinfo;
    }

    private List<MfBaiduStatistics> list(String str) {
        Example example = new Example(MfBaiduStatistics.class);
        example.createCriteria()
                .orLike("bdWebsite", MyUtil.toLike(str))
                .orLike("bdDomain", MyUtil.toLike(str))
                .orLike("bdMsg", MyUtil.toLike(str));
        List<MfBaiduStatistics> list = baiduStatisticsMapper.selectByExample(example);
//获取对应的页面
//        for (MfBaiduStatistics mbs : list) {
//            Example example1 = new Example(MfBaiduPage.class);
//            example1.createCriteria().andEqualTo("bdId", mbs.getBdId());
//            mbs.setMfBaiduPages(baiduPageMapper.selectByExample(example1));
//        }
        return list;
    }

    @Override
    public int update(MfBaiduStatistics mbs) {
        return baiduStatisticsMapper.updateByPrimaryKeySelective(mbs);
    }

    @Override
    public int add(MfBaiduStatistics mbs) {
        mbs.setBdId(MyUtil.getUUID());
        mbs.setBdTime(MyUtil.getNowDate(0));
        return baiduStatisticsMapper.insertSelective(mbs);
    }

    @Override
    public MfBaiduStatistics getOne(String pageid) {
        Example example = new Example(MfBaiduPage.class);
        example.createCriteria().andEqualTo("pageid", pageid);
        String bdId = baiduPageMapper.selectOneByExample(example).getBdId();
        return baiduStatisticsMapper.selectByPrimaryKey(bdId);
    }
}
