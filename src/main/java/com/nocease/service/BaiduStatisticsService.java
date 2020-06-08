package com.nocease.service;

import com.github.pagehelper.PageInfo;
import com.nocease.pojo.MfBaiduStatistics;

public interface BaiduStatisticsService {
    PageInfo list(int page, int limit, String str);

    int update(MfBaiduStatistics mbs);

    int add(MfBaiduStatistics mbs);

    MfBaiduStatistics getOne(String pageid);
}
