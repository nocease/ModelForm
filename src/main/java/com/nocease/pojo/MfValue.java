package com.nocease.pojo;

import lombok.Data;

import javax.persistence.Id;

/**
 * @version 1.0
 * @Author nocease
 * @date 2020/3/14 15:43
 * @Description 选项表
 */
@Data
public class MfValue {
    @Id
    private String fieldid;
    private String value;

    public MfValue() {
    }


    public MfValue(String fieldid, String value) {
        this.fieldid = fieldid;
        this.value = value;
    }
}
