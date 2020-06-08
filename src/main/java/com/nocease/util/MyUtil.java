package com.nocease.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @version 1.0
 * @Author nocease
 * @Date 2019/11/12 16:04
 * @Description 本项目常用工具类：字符串、时间
 */
public class MyUtil {

    /**
     * null转"",防止出错
     *
     * @param str
     * @return
     */
    public static String Null2Str(String str) {
        try {
            return str == null ? "" : str;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 字符串转数字，有错返回initial
     *
     * @param str
     * @return
     */
    public static int Str2Int(String str, int initial) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return initial;
        }
    }

    /**
     * 把null的integer转化为0
     *
     * @param i
     * @return
     */
    public static int null2o(Integer i) {
        return i == null ? 0 : i;
    }

    /**
     * @param str
     * @return 拼接%，方便模糊查询
     */
    public static String toLike(String str) {
        return new StringBuffer("%").append(Null2Str(str)).append("%").toString();
    }

    /**
     * 根据i获取时间转化str
     *
     * @param i 当i=1，获取时间；当i=2，获取日期；当i=0，获取日期+时间；
     * @return
     */
    private static String getSimpleDateFormat(int i) {
        String sdfStr = "";
        switch (i) {
            case 1:
                sdfStr = "HH:mm:ss";
                break;
            case 2:
                sdfStr = "yyyy-MM-dd";
                break;
            default:
                sdfStr = "yyyy-MM-dd HH:mm:ss";
                break;
        }
        return sdfStr;
    }

    /**
     * 获取当前日期时间，字符串类型
     *
     * @param i
     * @return
     */
    public static String getNowDate(int i) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getSimpleDateFormat(i));
        return simpleDateFormat.format(date);
    }

    /**
     * 字符串转时间
     *
     * @param str
     * @param i
     * @return 有异常返回当前时间
     */
    public static Date Str2Date(String str, int i) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getSimpleDateFormat(i));
        try {
            Date date = simpleDateFormat.parse(str);
            return date;
        } catch (Exception e) {
            return new Date();
        }
    }

    /**
     * 时间转字符串
     *
     * @param date
     * @param i
     * @return
     */
    public static String Date2Str(Date date, int i) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getSimpleDateFormat(i));
        return simpleDateFormat.format(date);
    }

    /**
     * 获取一个字符串的md5
     *
     * @param s
     * @return
     */
    public static String getMd5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = s.getBytes("UTF-8");
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取一个uuid
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }


}
