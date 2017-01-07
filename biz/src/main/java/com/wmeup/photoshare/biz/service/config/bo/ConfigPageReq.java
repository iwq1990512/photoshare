package com.wmeup.photoshare.biz.service.config.bo;

import com.wmeup.photoshare.biz.service.base.page.BaseReq;
import com.wmeup.photoshare.dao.domain.config.Config;

/**
 * Created by zy on 2016/8/10.
 */
public class ConfigPageReq extends BaseReq {

    private Config config;

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
