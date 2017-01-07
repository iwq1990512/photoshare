package com.wmeup.photoshare.redis.impl;

import com.alibaba.fastjson.JSON;
import com.wmeup.photoshare.redis.RedisCache;
import com.wmeup.photoshare.redis.enums.RedisCacheKeyPre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zy
 */
public class RedisCacheImpl implements RedisCache {
    private static final Logger logger = LoggerFactory.getLogger(RedisCacheImpl.class);

    private StringRedisTemplate valueOperations;

    @Override
    public <T> T getVal(RedisCacheKeyPre pre, String key, Class<T> tClass) {
        if (tClass == null) {
            return null;
        }
        if (pre == null) {
            return null;
        }
        if (!StringUtils.hasText(key)) {
            return null;
        }
        T result = null;
        String reallyKey = createKey(pre, key);
        String value = valueOperations.opsForValue().get(reallyKey);
        if (StringUtils.hasText(value)) {
            result = JSON.parseObject(value, tClass);
            logger.info("根据KEY {} 查询到Val{}", reallyKey, value);
        } else {
            logger.info("未找到指定的{}", reallyKey);
        }
        return result;
    }

    @Override
    public List<String> mgetVal(RedisCacheKeyPre pre, List<String> keys) {
        if (pre == null) {
            return null;
        }
        if (CollectionUtils.isEmpty(keys)) {
            return null;
        }
        List<String> reallyKeys = new ArrayList<String>();
        for (String key : keys) {
            String reallyKey = createKey(pre, key);
            reallyKeys.add(reallyKey);
        }
        List<String> strings = valueOperations.opsForValue().multiGet(reallyKeys);
        return strings;
    }

    @Override
    public <T> void putVal(RedisCacheKeyPre pre, String key, T t) {
        if (t == null) {
            return;
        }
        if (pre == null) {
            return;
        }
        if (!StringUtils.hasText(key)) {
            return;
        }
        String reallyKey = createKey(pre, key);
        String value = JSON.toJSONString(t);
        valueOperations.opsForValue().set(reallyKey, value);
        logger.info("插入KEY {} Val{} 成功", reallyKey, value);
    }

    @Override
    public void mputVal(Map<String, String> map) {
        if (CollectionUtils.isEmpty(map)) {
            return;
        }
        valueOperations.opsForValue().multiSet(map);
        logger.info("批量插入Key Val 成功");
    }

    @Override
    public <T> void putVal(RedisCacheKeyPre pre, String key, T t, Long timeout) {
        if (t == null) {
            return;
        }
        if (pre == null) {
            return;
        }
        if (timeout == null) {
            timeout = 60000l;
        }
        if (!StringUtils.hasText(key)) {
            return;
        }
        String reallyKey = createKey(pre, key);
        String value = JSON.toJSONString(t);
        valueOperations.opsForValue().set(reallyKey, value, timeout, TimeUnit.MILLISECONDS);
        logger.info("插入KEY {} Val{} 成功", reallyKey, value);
    }

    @Override
    public <T> T delVal(RedisCacheKeyPre pre, String key, Class<T> tClass) {
        if (tClass == null) {
            return null;
        }
        if (!StringUtils.hasText(key)) {
            return null;
        }
        String reallyKey = createKey(pre, key);
        T result = getVal(pre, reallyKey, tClass);
        valueOperations.delete(reallyKey);
        logger.info("删除KEY {} 成功", reallyKey);
        return result;
    }

    protected String createKey(RedisCacheKeyPre pre, String list) {
        return pre.getCode() + list;
    }

    public void setValueOperations(StringRedisTemplate valueOperations) {
        this.valueOperations = valueOperations;
    }
}
