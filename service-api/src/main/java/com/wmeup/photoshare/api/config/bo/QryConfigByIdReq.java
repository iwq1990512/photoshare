package com.wmeup.photoshare.api.config.bo;

import com.wmeup.photoshare.api.base.bo.BaseRequestInfo;

/**
 * Created by zy on 2016/8/15.
 */
public class QryConfigByIdReq extends BaseRequestInfo {
    private static final long serialVersionUID = 7373846742315994627L;
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
