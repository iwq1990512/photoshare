package com.wmeup.photoshare.api.base.bo;

import java.io.Serializable;

/**
 * @author zy
 */
public class BaseRequestInfo implements Serializable {
    private static final long serialVersionUID = -1344069010706603299L;
    //请求版本号
    private String version;
    //请求流水号
    private String requestId;
    //加密类型
    private String signType;
    //加密信息
    private String sign;
    //渠道信息
    private String channel;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
