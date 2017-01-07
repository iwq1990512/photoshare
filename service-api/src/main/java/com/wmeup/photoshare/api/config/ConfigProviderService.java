package com.wmeup.photoshare.api.config;

import com.wmeup.photoshare.api.config.bo.*;

/**
 * 配置项服务
 * Created by zy on 2016/8/3.
 */
public interface ConfigProviderService {
    /**
     * 银行是否可用
     * @param req
     * @return
     */
    BankAvailRsp isBankAvailable(BankAvailReq req);

    /**
     * 增加一条配置
     * @param addOneConfigReq flag 和 value不能为空，且需要唯一
     * @return
     */
    AddOneConfigRsp addOneConfig(AddOneConfigReq addOneConfigReq);

    /**
     * 分页获取配置项列表
     * @param qryPageConfigsReq
     * @return
     */
    QryPageConfigsRsp queryConfigsByPage(QryPageConfigsReq qryPageConfigsReq);

    /**
     * 根据主键id删除一条数据
     * @param delConfigReq
     * @return
     */
    DelConfigRsp delById(DelConfigReq delConfigReq);

    /**
     * 更新一条数据
     * @param updateConfigReq
     * @return
     */
    UpdateConfigRsp updateById(UpdateConfigReq updateConfigReq);

    /**
     * 通过主键id获取
     * @param qryConfigByIdReq
     * @return
     */
    QryConfigByIdRsp queryById(QryConfigByIdReq qryConfigByIdReq);

}
