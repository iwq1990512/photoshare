package com.wmeup.photoshare.biz.service.config;

import com.wmeup.photoshare.biz.service.base.page.PagedResult;
import com.wmeup.photoshare.biz.service.config.bo.ConfigPageReq;
import com.wmeup.photoshare.dao.domain.config.Config;

/**
 * 配置项基础服务类
 * Created by zy on 2016/8/5.
 */
public interface ConfigInfoService {
    /**
     * 通过flag和value查询
     * @param config
     * @return
     */
    Config queryByFlagAndValue(Config config);
    /**
     * 查询禁止银行配置信息
     * @param config
     * @return
     */
    Config queryBannedBank(Config config);

    /**
     * 查询银行升级信息
     * @param config
     * @return
     */
    Config queryBankUpgrade(Config config);

    /**
     * 主键查询
     * @param config
     * @return
     */
    Config selectById(Config config);

    /**
     * 分页查询
     * @param pageReq
     * @return
     */
    PagedResult<Config> queryByPage(ConfigPageReq pageReq);

    /**
     * 添加
     * @param config
     * @return
     */
    Config addOneConfig(Config config);

    /**
     * 删除一条记录
     * @param config
     * @return
     */
    int delById(Config config);

    /**
     * 更新
     * @param config
     * @return
     */
    int updateById(Config config);
}
