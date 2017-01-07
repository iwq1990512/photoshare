package com.wmeup.photoshare.redis;


import com.wmeup.photoshare.redis.enums.RedisCacheKeyPre;

import java.util.List;
import java.util.Map;

/**
 * @author zy
 */
public interface RedisCache {

    /**
     * 获取指定key的缓存值
     *
     * @param pre
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
    <T> T getVal(RedisCacheKeyPre pre, String key, Class<T> tClass);

    /**
     * 获取指定keys列表的缓存值
     *
     * @param pre
     * @param keys
     * @return
     */
    List<String> mgetVal(RedisCacheKeyPre pre, List<String> keys);

    /**
     * 设置指定的key value
     *
     * @param pre
     * @param key
     * @param t
     * @param <T>
     */
    <T> void putVal(RedisCacheKeyPre pre, String key, T t);

    /**
     * 设置指定的Map  key value 对
     *
     * @param map
     */
    void mputVal(Map<String, String> map);

    /**
     * 设置参数带失效时间
     *
     * @param pre
     * @param key
     * @param t
     * @param Timeout 失效时间，毫秒级
     * @param <T>
     */
    <T> void putVal(RedisCacheKeyPre pre, String key, T t, Long Timeout);

    /**
     * 删除指定的key 并且返回
     *
     * @param pre
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
    <T> T delVal(RedisCacheKeyPre pre, String key, Class<T> tClass);
}
