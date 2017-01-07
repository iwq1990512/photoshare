package com.wmeup.util.constants;

/**
 * 通用业务类型枚举类
 * Created by zy on 2016/10/11.
 */
public enum  GeneralBusinessType {

    BANK_FULL_TYPE("bank_full","银行支付渠道全量配置");
    /**
     * 业务类型code
     */
    private String code;
    /**
     * 业务类型名称
     */
    private String typeName;

    GeneralBusinessType(String code, String typeName){
        this.code = code;
        this.typeName = typeName;
    }
    public String getCode(){
        return code;
    }
    public String getTypeName() {
        return typeName;
    }
}
