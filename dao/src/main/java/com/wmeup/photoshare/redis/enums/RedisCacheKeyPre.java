package com.wmeup.photoshare.redis.enums;

/**
 * @author zy
 */
public enum RedisCacheKeyPre {
    //查询银行限额列表信息
    QueryBankLimits("jdb_commconf_100$");



    private String code;

    RedisCacheKeyPre(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
