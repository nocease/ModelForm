package com.nocease.pojo;

import lombok.Data;

import java.util.List;

/**
 * @version 1.0
 * @Author nocease
 * @date 2020/3/19 11:49
 * @Description
 */
@Data
public class PostUpdateData {
    private String pageid;
    private List<PostValue> data;
    private String id;
}
