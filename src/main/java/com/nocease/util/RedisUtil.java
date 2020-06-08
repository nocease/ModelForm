package com.nocease.util;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @version 1.0
 * @Author nocease
 * @Date 2019/11/12 16:02
 * @Description redis的存取操作工具类
 */
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate redisJsonTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    //存对象
    public void set(String name, Object obj) {
        ValueOperations ops = redisJsonTemplate.opsForValue();
        ops.set(name, obj);
    }

    //存集合(覆盖原有的)
    public void set(String ListName, List list) {
        ListOperations ops = redisJsonTemplate.opsForList();
        if (ops.size(ListName) > 0)
            redisJsonTemplate.delete(ListName);
        for (Object obj : list) {
            ops.leftPush(ListName, obj);
        }
    }

    //取字符串
    public String get(String name) {
        try {
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            return ops.get("\"" + name + "\"");
        } catch (Exception e) {
            return null;
        }
    }

    //删除
    public void del(String name) {
        try {
            //redisJsonTemplate.delete(name);
            stringRedisTemplate.delete(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //模糊批量删除
    public void delByStr(String str) {
        try {
            Set<String> keys = stringRedisTemplate.keys(str + ":*");
            stringRedisTemplate.delete(keys);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
