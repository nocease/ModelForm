package com.nocease.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

/**
 * @version 1.0
 * @Author nocease
 * @date 2020/4/2 14:41
 * @Description 百度统计
 */
@Data
public class MfBaiduStatistics {
    @Id
    private String bdId;//字段主键
    private String bdTime;
    private String bdWebsite;//网站名称
    private String bdDomain;//域名
    private String bdMsg;//备注
    private String bdStatisticsUrl;//统计地址
    private String bdJs;//嵌入js

    @Transient
    private List<MfBaiduPage> MfBaiduPages;//所有统计页面
}
