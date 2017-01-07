package com.wmeup.photoshare.biz.service.third.idauth;

import com.wmeup.photoshare.consumer.third.idauth.domain.CertificationRsp;

import java.util.Map;

/**
 * 实名认证接口
 * Created by zy on 2016/9/21.
 */
public interface IdauthService {
    /**
     * 线下实名认证信息导入
     * @param reqParam
     * @return
     */
    CertificationRsp importAuthInfo(Map<String, String> reqParam);
}
