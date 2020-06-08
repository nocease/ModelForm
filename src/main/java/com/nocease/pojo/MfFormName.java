package com.nocease.pojo;

import lombok.Data;

import javax.persistence.Id;

/**
 * @version 1.0
 * @Author nocease
 * @date 2019/12/23 15:05
 * @Description form名字的实体类，用于下拉选form
 */
@Data
public class MfFormName {
    @Id
    private String id;
    private String formName;
}
