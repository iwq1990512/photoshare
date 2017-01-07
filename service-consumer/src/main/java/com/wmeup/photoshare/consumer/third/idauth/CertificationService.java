package com.wmeup.photoshare.consumer.third.idauth;

import com.wmeup.photoshare.consumer.third.idauth.domain.CertificationRsp;

import java.util.Map;

/**
 * 实名认证接口
 * Created by zy on 2016/9/21.
 */
public interface CertificationService {
    /**
     * 实名认证http接口
     * @param reqParam 请求参数
     * @return
     */
    CertificationRsp certificationHttp(Map<String, String> reqParam);
}
