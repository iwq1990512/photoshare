package com.wmeup.photoshare.redis.impl;

import com.wmeup.photoshare.redis.RedisCache;
import com.wmeup.photoshare.redis.RedisCacheCatchEx;
import com.wmeup.photoshare.redis.enums.RedisCacheKeyPre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: redis捕获异常</p>
 * <p>Description: redis捕获异常<p>
 * <p>Copyright: Copyright (c) 2016</p>
 *
 * @author zy
 * @version 1.0
 * @date 16/8/17
 */

public class RedisCacheCatchExImpl implements RedisCacheCatchEx {
    private static final Logger logger = LoggerFactory.getLogger(RedisCacheCatchExImpl.class);
    private RedisCache redisCache;

    @Override
    public <T> T getVal(RedisCacheKeyPre pre, String key, Class<T> tClass) {
        T result = null;
        try {
            result = redisCache.getVal(pre, key, tClass);
        } catch (Exception e) {
            logger.error("redis捕获到异常", e);
        }
        return result;
    }

    @Override
    public List<String> mgetVal(RedisCacheKeyPre pre, List<String> keys) {
        List<String> result = new ArrayList<String>();
        try {
            result = redisCache.mgetVal(pre, keys);
        } catch (Exception e) {
            logger.error("redis捕获到异常", e);
        }
        return result;
    }

    @Override
    public <T> void putVal(RedisCacheKeyPre pre, String key, T t) {
        try {
            redisCache.putVal(pre, key, t);
        } catch (Exception e) {
            logger.error("redis捕获到异常", e);
        }
    }

    @Override
    public void mputVal(Map<String, String> map) {
        try {
            redisCache.mputVal(map);
        } catch (Exception e) {
            logger.error("redis捕获到异常", e);
        }
    }

    @Override
    public <T> void putVal(RedisCacheKeyPre pre, String key, T t, Long Timeout) {
        try {
            redisCache.putVal(pre, key, t, Timeout);
        } catch (Exception e) {
            logger.error("redis捕获到异常", e);
        }
    }

    @Override
    public <T> T delVal(RedisCacheKeyPre pre, String key, Class<T> tClass) {
        try {
            return redisCache.delVal(pre, key, tClass);
        } catch (Exception e) {
            logger.error("redis捕获到异常", e);
        }
        return null;
    }

    public void setRedisCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }
}
