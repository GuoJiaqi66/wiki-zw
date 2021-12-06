package com.zw.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/*
 * 远程id+redis解决：重复点赞问题
 * 线程本地变量问题
 * */
@Component
public class RedisUtil {

    private static final Logger LOG = LoggerFactory.getLogger(com.zw.util.RedisUtil.class);

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * true：不存在，放一个KEY
     * false：已存在
     * @param key
     * @param second
     * @return
     */
    public boolean validateRepeat(String key, long second) {
        if (redisTemplate.hasKey(key)) {
            LOG.info("key已存在：{}", key);
            return false;
        } else {
            LOG.info("key不存在，放入：{}，过期 {} 秒", key, second);
            redisTemplate.opsForValue().set(key, key, second, TimeUnit.SECONDS);
            return true;
        }
    }
}
