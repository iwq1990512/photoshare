package com.wmeup.photoshare.api.base.bo.enums;

/**
 * 银行配置信息绑卡类型枚举类
 * Created by zy on 2016/8/4.
 */
public enum BankBindTypeEnum {
    UNION_TYPE("03", "银联快捷"),
    DIRECT_TYPE("04", "直连快捷");
    /**
     * 类型码
     */
    private String code;
    /**
     * 名称
     */
    private String name;

    BankBindTypeEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
