package com.wmeup.photoshare.controller.generalconfig;

import com.wmeup.photoshare.api.config.ConfigProviderService;
import com.wmeup.photoshare.api.config.bo.QryConfigByIdReq;
import com.wmeup.photoshare.api.config.bo.QryConfigByIdRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 公共配置服务http接口
 * Created by zy on 2016/10/28.
 */
@Controller
@RequestMapping("/service/generalConfig")
public class GeneralConfigHttp {
    @Autowired
    private ConfigProviderService configProviderService;

    @RequestMapping(value = "queryConfigById")
    public QryConfigByIdRsp queryBankWithholdingFull(QryConfigByIdReq req){
        return configProviderService.queryById(req);
    }
}
