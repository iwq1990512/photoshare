package com.wmeup.photoshare.api.base.constants.baseConstants;

/**
 * Created by yzf on 2016/5/25.
 */
public enum BaseConstants {

    /**
     * 请求报文公共参数version
     */
    VERSION_V1("1.0"),

    /**
     * 请求报文公共参数channel
     *  PTP：p2p交易
     PGW：支付网关
     PRT：支付路由
     UMG：用户中心
     ACM：P2P账务
     ACT：P2P会计
     OPER：运营平台
     DBUS：数据中心
     CPP:企业前置
     FSM:理财超市 Financial supermarket
     PRDF:订单中心前置
     PRD:订单中心
     ORDERCORE:红包前置
     COMMCONF:配置中心
     SKYSERVERJ:天网后台
     CDS:理财超市cds
     */

    Channel_PTP("PTP"),
    Channel_PGW("PGW"),
    Channel_PRT("PRT"),
    Channel_UMG("UMG"),
    Channel_ACM("ACM"),
    Channel_ACT("ACT"),
    Channel_OPER("OPER"),
    Channel_DBUS("DBUS"),
    Channel_CPP("CPP"),
    Channel_FSM("FSM"),
    Channel_PRDF("PRDF"),
    Channel_PRD("PRD"),
    Channel_ORDERCORE("ORDERCORE"),
    Channel_COMMCONF("COMMCONF"),
    Channel_SKYSERVERJ("SKYSERVERJ"),
    Channel_CDS("CDS"),
    /**
     * 请求报文公共参数signtype
     */

    SIGNTYPE_MD5("MD5"),
    SIGNTYPE_RSA("RSA");

    private String code;

    BaseConstants(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static BaseConstants getBaseConstants(String code){
        for (BaseConstants baseConstants : BaseConstants.values()) {
            if(baseConstants.getCode().equals(code)){
                return baseConstants;
            }
        }
        return null;
    }

}
