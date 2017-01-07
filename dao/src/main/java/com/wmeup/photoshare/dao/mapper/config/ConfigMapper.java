package com.wmeup.photoshare.dao.mapper.config;

import com.wmeup.photoshare.dao.domain.config.Config;

import java.util.List;

/**
* @ClassName: ConfigMapper
* @Description:
* @author zhangyong
* @date 2016-8-2
*/
public interface ConfigMapper{
    /**
     * 获取一个配置
     * @param config flag, state, value必须传
     * @return
     */
    Config queryOneConfig(Config config);

    /**
     * 获取配置列表
     * @param config
     * @return
     */
    List<Config> queryConfigs(Config config);

    /**
     * 插入新的记录
     * @param config
     * @return 成功插入条数
     */
    int insertOne(Config config);

    /**
     * 根据主键id查找
     * @param config
     * @return
     */
    Config selectById(Config config);

    /**
     * 删除一条记录
     * @param config
     * @return
     */
    int delById(Config config);

    /**
     * 更新一条记录
     * @param config
     * @return
     */
    int updateById(Config config);
}