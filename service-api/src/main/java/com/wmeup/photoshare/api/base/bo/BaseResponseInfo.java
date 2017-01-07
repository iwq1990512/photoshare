package com.wmeup.photoshare.api.base.bo;

import java.io.Serializable;

/**
 * @author zy
 *         请求的基础信息
 */
public class BaseResponseInfo implements Serializable {
    private static final long serialVersionUID = 6342358416228662776L;
    //响应版本号
    private String version;
    //响应代码
    private String rspCode;
    //响应信息
    private String rspMessage;

    public BaseResponseInfo() {
    }

    public BaseResponseInfo(String rspCode, String rspMessage) {
        this.rspCode = rspCode;
        this.rspMessage = rspMessage;
    }

    public BaseResponseInfo(String version, String rspCode, String rspMessage) {
        this.version = version;
        this.rspCode = rspCode;
        this.rspMessage = rspMessage;
    }

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
}
