package com.lagou;/*
 * @ClassName Test1
 * @Desc TODO
 * @Author 19637
 * @Date 2021/11/21 20:09
 * @Version 1.0
 */

import com.lagou.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class Test1 {
    @Autowired
    UserMapper mapper;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Test
    public void test1(){
        System.out.println(mapper.selectById(1));
    }
}
