package com.nocease.util;

import java.io.*;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.yaml.snakeyaml.Yaml;

/**
 * @version 1.0
 * @Author nocease
 * @date 2020/3/22 14:22
 * @Description 实时获取和修改yaml文件的值
 */
public class YamlUtil {

    //private static final String yamlPath = "src/main/resources/key.yaml";
    private static final String yamlPath = "key.yaml";

    //获取yaml文件内容
    public static Map getValue() {
        try {
            Yaml yaml = new Yaml();
            FileInputStream fileInputStream = new FileInputStream(yamlPath);
            Map map = (Map) yaml.load(fileInputStream);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //修改yaml文件内容
    public static void setValue(JSONObject json) {
        Yaml yaml = new Yaml();
        try {
            yaml.dump(json, new OutputStreamWriter(new FileOutputStream(yamlPath), "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
