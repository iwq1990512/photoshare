package com.wmeup.photoshare.biz.service.third.idauth.impl;

import com.wmeup.photoshare.biz.service.third.idauth.IdauthService;
import com.wmeup.photoshare.consumer.property.ConsumerProClass;
import com.wmeup.photoshare.consumer.third.idauth.CertificationService;
import com.wmeup.photoshare.consumer.third.idauth.domain.CertificationRsp;

import java.util.HashMap;
import java.util.Map;

/**
 * 实名认证相关接口
 * Created by zy on 2016/9/21.
 */
public class IdauthServiceImpl implements IdauthService {
    private ConsumerProClass consumerProClass;
    private CertificationService certificationService;
    @Override
    public CertificationRsp importAuthInfo(Map<String, String> reqParam) {
        if (null == reqParam)
            reqParam = new HashMap<String, String>();
        //线下实名认证导入url
        reqParam.put("certUrl", consumerProClass.getCertUrl());
        return certificationService.certificationHttp(reqParam);
    }

    public void setConsumerProClass(ConsumerProClass consumerProClass) {
        this.consumerProClass = consumerProClass;
    }

    public void setCertificationService(CertificationService certificationService) {
        this.certificationService = certificationService;
    }
}
