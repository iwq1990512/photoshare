package com.wmeup.photoshare.api.base.constants.enums;

/**
 * 配置类型枚举
 * Created by zy on 2016/8/18.
 */
public enum ConfigFlagEnum {

    /**
     * 配置类型
     *
     upop_upgrade：银行系统升级
     banned_bank：银行配置禁止
     bank_white：银行支持直连开关

     */

    Flag_upop_upgrade("upop_upgrade"),
    Flag_banned_bank("banned_bank"),
    Flag_bank_white("bank_white");

    private String code;

    ConfigFlagEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
