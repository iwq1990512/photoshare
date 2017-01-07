package com.wmeup.photoshare.api.config.bo;

import com.wmeup.photoshare.api.base.bo.BaseResponseInfo;

import java.util.List;

/**
 * Created by zy on 2016/8/10.
 */
public class QryPageConfigsRsp extends BaseResponseInfo {
    private static final long serialVersionUID = -1696499470440067857L;
    /**
     * 总数
     */
    private long total;
    /**
     * 配置列表
     */
    private List<ConfigInfo> configInfos;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<ConfigInfo> getConfigInfos() {
        return configInfos;
    }

    public void setConfigInfos(List<ConfigInfo> configInfos) {
        this.configInfos = configInfos;
    }
}
