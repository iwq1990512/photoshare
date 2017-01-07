package com.wmeup.photoshare.consumer.third.idauth.domain;

/**
 * Created by zy on 2016/9/21.
 */
public class CertificationRsp {
    /**
     *版本号
     */
    private String version;
    /**
     *应答码
     */
    private String rspCode;
    /**
     *应答信息
     */
    private String rspMessage;
    /**
     *签名类型
     */
    private Boolean signType;
    /**
     *签名信息
     */
    private String serverSign;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspMessage() {
        return rspMessage;
    }

    public void setRspMessage(String rspMessage) {
        this.rspMessage = rspMessage;
    }

    public Boolean getSignType() {
        return signType;
    }

    public void setSignType(Boolean signType) {
        this.signType = signType;
    }

    public String getServerSign() {
        return serverSign;
    }

    public void setServerSign(String serverSign) {
        this.serverSign = serverSign;
    }
}
