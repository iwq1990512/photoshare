package com.wmeup.photoshare.biz.service.config.impl;

import com.github.pagehelper.PageHelper;
import com.wmeup.photoshare.biz.service.base.page.PageBeanUtil;
import com.wmeup.photoshare.biz.service.base.page.PagedResult;
import com.wmeup.photoshare.biz.service.config.ConfigInfoService;
import com.wmeup.photoshare.biz.service.config.bo.ConfigPageReq;
import com.wmeup.photoshare.dao.domain.config.Config;
import com.wmeup.photoshare.dao.mapper.config.ConfigMapper;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * Created by zy on 2016/8/8.
 */
public class ConfigInfoServiceImpl implements ConfigInfoService {
    private ConfigMapper configMapper;

    @Override
    public Config queryByFlagAndValue(Config config) {
        if (StringUtils.isBlank(config.getFlag()) || StringUtils.isBlank(config.getValue()))
            return null;
        Config configFromDB = configMapper.queryOneConfig(config);
        return configFromDB;
    }

    @Override
    public Config queryBannedBank(Config config) {
        config.setFlag("banned_bank");
        config.setState(1);
        Config configFromDB = configMapper.queryOneConfig(config);

        return configFromDB;
    }

    @Override
    public Config queryBankUpgrade(Config config) {
        config.setFlag("upop_upgrade");
        config.setState(1);
        Config configFromDB = configMapper.queryOneConfig(config);

        return configFromDB;
    }

    @Override
    public Config selectById(Config config) {
        Config configFromDB = configMapper.selectById(config);
        return configFromDB;
    }



    @Override
    public PagedResult<Config> queryByPage(ConfigPageReq req) {
        Integer pageNo = (null != req.getPageNo()?req.getPageNo():1);
        Integer pageSize = (null!=req.getPageSize()?req.getPageSize():10);
        //开始分页
        PageHelper.startPage(pageNo, pageSize);
        PagedResult<Config> result = PageBeanUtil.toPagedResult(configMapper.queryConfigs(req.getConfig()));
        return result;
    }

    @Override
    public Config addOneConfig(Config config) {
        configMapper.insertOne(config);
        return config;
    }

    @Override
    public int delById(Config config) {
        int num = configMapper.delById(config);
        return num;
    }

    @Override
    public int updateById(Config config) {
        int num = configMapper.updateById(config);
        return num;
    }

    public void setConfigMapper(ConfigMapper configMapper) {
        this.configMapper = configMapper;
    }


}
