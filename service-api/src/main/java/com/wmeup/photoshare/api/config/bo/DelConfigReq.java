package com.wmeup.photoshare.api.config.bo;

import com.wmeup.photoshare.api.base.bo.BaseRequestInfo;

/**
 * Created by zy on 2016/8/10.
 */
public class DelConfigReq extends BaseRequestInfo {
    private static final long serialVersionUID = 7725025395192168858L;
    /**
     * 主键id
     */
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
