package com.wmeup.photoshare.api.config.bo;

import com.wmeup.photoshare.api.base.bo.BaseResponseInfo;

/**
 * Created by zy on 2016/8/10.
 */
public class DelConfigRsp extends BaseResponseInfo {
    private static final long serialVersionUID = 2911123283840511431L;
    /**
     * 主键id
     */
    private Long id;

    public DelConfigRsp() {
    }

    public DelConfigRsp(String version, String rspCode, String rspMessage) {
        super(version, rspCode, rspMessage);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
