package com.wmeup.photoshare.provider.config;

import com.alibaba.fastjson.JSON;
import com.wmeup.photoshare.api.base.constants.baseConstants.BaseConstants;
import com.wmeup.photoshare.api.base.constants.codeConstans.CodeConstants;
import com.wmeup.photoshare.api.base.constants.codeConstans.CodeMsgConstants;
import com.wmeup.photoshare.api.base.bo.BaseRequestInfo;
import com.wmeup.photoshare.api.base.bo.BaseResponseInfo;
import com.wmeup.photoshare.api.config.ConfigProviderService;
import com.wmeup.photoshare.biz.service.base.page.PagedResult;
import com.wmeup.photoshare.biz.service.config.ConfigInfoService;
import com.wmeup.photoshare.biz.service.config.bo.ConfigPageReq;
import com.wmeup.photoshare.common.utils.hibvalidator.HibValidatorUtil;
import com.wmeup.photoshare.dao.domain.config.Config;
import com.wmeup.photoshare.api.config.bo.*;
import com.wmeup.util.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * Created by zy on 2016/8/5.
 */
public class ConfigProviderServiceImpl implements ConfigProviderService{
    private static final Logger logger = LoggerFactory.getLogger(ConfigProviderServiceImpl.class);
    private ConfigInfoService configInfoService;
    private HibValidatorUtil hibValidatorUtil;

    @Override
    public BankAvailRsp isBankAvailable(BankAvailReq req) {
        logger.info("isBankAvailable接口传入参数为：{}", JSON.toJSONString(req));
        String msg = hibValidatorUtil.getFullValidatorMessage(req);
        if (StringUtils.isNotBlank(msg)){
            logger.warn("参数异常,系统响应码:{},响应信息:{}",CodeConstants.PARAM_ERR, msg);
            return new BankAvailRsp(BaseConstants.VERSION_V1.getCode(), CodeConstants.PARAM_ERR, msg);
        }
        Config param = new Config();
        param.setValue(req.getBankCode());
        //银行配置是否禁止
        Config bannedConfig = configInfoService.queryBannedBank(param);
        //银行系统是否升级
        Config upgradeConfig = configInfoService.queryBankUpgrade(param);

        BankAvailRsp rsp = new BankAvailRsp();
        rsp.setRspMessage(CodeMsgConstants.SUCCESS_CODE.QUERY_SUCCESS);
        rsp = convertBankAvailableRsp(rsp, bannedConfig,upgradeConfig);
        rsp.setRspCode(CodeConstants.SUCCESS_CODE);
        rsp.setVersion(req.getVersion());
        return rsp;
    }

    @Override
    public AddOneConfigRsp addOneConfig(AddOneConfigReq addOneConfigReq) {
        logger.info("addOneConfig接口传入参数为：{}", JSON.toJSONString(addOneConfigReq));

        List<String> paramList = new ArrayList<String>();
        paramList.add("flag");
        paramList.add("value");
        //配置类型和value不能为空
        String msg = hibValidatorUtil.getPropertyValidatorMessage(addOneConfigReq, paramList);
        if (StringUtils.isNotBlank(msg)){
            logger.warn("参数异常,系统响应码:{},响应信息:{}",CodeConstants.PARAM_ERR, msg);
            return new AddOneConfigRsp(BaseConstants.VERSION_V1.getCode(),CodeConstants.PARAM_ERR, msg);
        }
        AddOneConfigRsp rsp = new AddOneConfigRsp();
        rsp.setVersion(BaseConstants.VERSION_V1.getCode());

        /**
         * 先根据配置类型和value查询，防止重复记录
         */
        Config qryParam = new Config();
        qryParam.setFlag(addOneConfigReq.getFlag());
        qryParam.setValue(addOneConfigReq.getValue());
        Config qryFromDB = configInfoService.queryByFlagAndValue(qryParam);
        if (null != qryFromDB){
            logger.info("该条配置信息已存在：{}", JSON.toJSONString(qryFromDB));
            rsp.setRspCode(CodeConstants.INSTER_ERR);
            rsp.setRspMessage(CodeMsgConstants.INSTER_ERR.CONFIG_EXIST);
            return rsp;
        }
        Config configParam = convertRequestToConfig(addOneConfigReq);
        Config configInfo = configInfoService.addOneConfig(configParam);
        if (null != configInfo){
            rsp = (AddOneConfigRsp) configInfoConvertToRsp(configInfo, rsp);
            rsp.setRspCode(CodeConstants.SUCCESS_CODE);
            rsp.setRspMessage(CodeMsgConstants.SUCCESS_CODE.ADD_SUCCESS);
        }
        return rsp;
    }

    @Override
    public QryPageConfigsRsp queryConfigsByPage(QryPageConfigsReq qryPageConfigsReq) {
        logger.info("queryConfigsByPage分页查找接口入参：{}",JSON.toJSONString(qryPageConfigsReq));
        ConfigPageReq configPageReq = convertToConfigPageReq(qryPageConfigsReq);
        PagedResult<Config> result = configInfoService.queryByPage(configPageReq);
        QryPageConfigsRsp rsp = convertToQryPageConfigsRsp(result);
        rsp.setRspCode(CodeConstants.SUCCESS_CODE);
        rsp.setRspMessage(CodeMsgConstants.SUCCESS_CODE.QUERY_SUCCESS);
        rsp.setVersion(BaseConstants.VERSION_V1.getCode());
        return rsp;
    }

    @Override
    public DelConfigRsp delById(DelConfigReq delConfigReq) {
        logger.info("delById接口参数信息：{}", JSON.toJSONString(delConfigReq));
        String msg = hibValidatorUtil.getFullValidatorMessage(delConfigReq);
        if (StringUtils.isNotBlank(msg)){
            logger.warn("参数异常,系统响应码:{},响应信息:{}",CodeConstants.PARAM_ERR, msg);
            return new DelConfigRsp(BaseConstants.VERSION_V1.getCode(), CodeConstants.PARAM_ERR, msg);
        }
        DelConfigRsp rsp = new DelConfigRsp();
        rsp.setVersion(BaseConstants.VERSION_V1.getCode());
        Config configParam = new Config();
        configParam.setId(delConfigReq.getId());
        if (configInfoService.delById(configParam) > 0){
            rsp.setId(delConfigReq.getId());
            rsp.setRspCode(CodeConstants.SUCCESS_CODE);
            rsp.setRspMessage(CodeMsgConstants.SUCCESS_CODE.DELETE_SUCCESS);
        }else {
            rsp.setId(delConfigReq.getId());
            rsp.setRspCode(CodeConstants.DELETE_ERR);
            rsp.setRspMessage(CodeMsgConstants.DELETE_ERR.CONFIG_NOT_EXIST);
        }
        return rsp;
    }

    @Override
    public UpdateConfigRsp updateById(UpdateConfigReq updateConfigReq) {
        logger.info("updateById接口参数信息：{}", JSON.toJSONString(updateConfigReq));
        List<String> paramList = new ArrayList<String>();
        paramList.add("id");
        String msg = hibValidatorUtil.getPropertyValidatorMessage(updateConfigReq, paramList);
        if (StringUtils.isNotBlank(msg)){
            logger.warn("参数异常,系统响应码:{},响应信息:{}",CodeConstants.PARAM_ERR, msg);
            return new UpdateConfigRsp(BaseConstants.VERSION_V1.getCode(),CodeConstants.PARAM_ERR, msg);
        }
        UpdateConfigRsp rsp = new UpdateConfigRsp();
        rsp.setVersion(BaseConstants.VERSION_V1.getCode());
        Config configParam = convertRequestToConfig(updateConfigReq);
        configInfoService.updateById(configParam);
        rsp.setId(updateConfigReq.getId());
        rsp.setRspCode(CodeConstants.SUCCESS_CODE);
        rsp.setRspMessage(CodeMsgConstants.SUCCESS_CODE.UPDATE_SUCCESS);
        return rsp;
    }

    @Override
    public QryConfigByIdRsp queryById(QryConfigByIdReq qryConfigByIdReq) {
        logger.info("queryById接口参数信息：{}", JSON.toJSONString(qryConfigByIdReq));
        String msg = hibValidatorUtil.getFullValidatorMessage(qryConfigByIdReq);
        if (StringUtils.isNotBlank(msg)){
            logger.warn("参数异常,系统响应码:{},响应信息:{}",CodeConstants.PARAM_ERR, msg);
            return new QryConfigByIdRsp(BaseConstants.VERSION_V1.getCode(), CodeConstants.PARAM_ERR, msg);
        }
        QryConfigByIdRsp  rsp = new QryConfigByIdRsp();
        rsp.setVersion(BaseConstants.VERSION_V1.getCode());
        Config configParam = new Config();
        configParam.setId(qryConfigByIdReq.getId());
        Config configInfo = configInfoService.selectById(configParam);
        rsp =(QryConfigByIdRsp)configInfoConvertToRsp(configInfo, rsp);
        rsp.setRspCode(CodeConstants.SUCCESS_CODE);
        rsp.setRspMessage(CodeMsgConstants.SUCCESS_CODE.QUERY_SUCCESS);
        return rsp;
    }

    /**
     * 配置转换返回报文
     * @param config 配置信息
     * @param rsp 返回报文
     * @return
     */
    private BaseResponseInfo configInfoConvertToRsp(Config config, BaseResponseInfo rsp){
        if (rsp instanceof AddOneConfigRsp) {
            AddOneConfigRsp addOneConfigRsp = (AddOneConfigRsp) rsp;
            addOneConfigRsp.setId(config.getId());
            addOneConfigRsp.setFlag(config.getFlag());
            addOneConfigRsp.setValue(config.getValue());
            addOneConfigRsp.setState(config.getState());
            addOneConfigRsp.setBeginTime(config.getBeginTime());
            addOneConfigRsp.setCreateTime(config.getCreateTime());
            addOneConfigRsp.setDescription(config.getDescription());
            addOneConfigRsp.setEndTime(config.getEndTime());
            addOneConfigRsp.setUpdateTime(config.getUpdateTime());
            addOneConfigRsp.setError(config.getError());
            return addOneConfigRsp;
        } else if (rsp instanceof QryConfigByIdRsp){
            QryConfigByIdRsp qryConfigByIdRsp = (QryConfigByIdRsp) rsp;
            qryConfigByIdRsp.setId(config.getId());
            qryConfigByIdRsp.setFlag(config.getFlag());
            qryConfigByIdRsp.setValue(config.getValue());
            qryConfigByIdRsp.setState(config.getState());
            qryConfigByIdRsp.setBeginTime(config.getBeginTime());
            qryConfigByIdRsp.setCreateTime(config.getCreateTime());
            qryConfigByIdRsp.setDescription(config.getDescription());
            qryConfigByIdRsp.setEndTime(config.getEndTime());
            qryConfigByIdRsp.setUpdateTime(config.getUpdateTime());
            qryConfigByIdRsp.setError(config.getError());
            return qryConfigByIdRsp;
        }

        return rsp;
    }
    /**
     * 根据请求构建Config
     * @param req
     * @return
     */
    private Config convertRequestToConfig(BaseRequestInfo req){
        Config config = new Config();
        if (req instanceof AddOneConfigReq){
            AddOneConfigReq addOneConfigReq = (AddOneConfigReq) req;
            config.setFlag(addOneConfigReq.getFlag());
            config.setValue(addOneConfigReq.getValue());
            config.setState(addOneConfigReq.getState());
            config.setBeginTime(addOneConfigReq.getBeginTime());
            config.setCreateTime(addOneConfigReq.getCreateTime());
            config.setDescription(addOneConfigReq.getDescription());
            config.setEndTime(addOneConfigReq.getEndTime());
            config.setUpdateTime(addOneConfigReq.getUpdateTime());
            config.setError(addOneConfigReq.getError());
            return config;
        } else if (req instanceof UpdateConfigReq){
            UpdateConfigReq updateConfigReq = (UpdateConfigReq) req;
            config.setId(updateConfigReq.getId());
            config.setFlag(updateConfigReq.getFlag());
            config.setValue(updateConfigReq.getValue());
            config.setState(updateConfigReq.getState());
            config.setBeginTime(updateConfigReq.getBeginTime());
            config.setCreateTime(updateConfigReq.getCreateTime());
            config.setDescription(updateConfigReq.getDescription());
            config.setEndTime(updateConfigReq.getEndTime());
            config.setUpdateTime(updateConfigReq.getUpdateTime());
            config.setError(updateConfigReq.getError());
            return config;
        }
        return config;
    }
    /**
     * 构建分页查询返回信息
     * @param result
     * @return
     */
    private QryPageConfigsRsp convertToQryPageConfigsRsp(PagedResult<Config> result){
        QryPageConfigsRsp qryPageConfigsRsp = new QryPageConfigsRsp();
        List<ConfigInfo> configInfos = convertToConfigInfos(result.getDataList());
        qryPageConfigsRsp.setTotal(result.getTotal());
        qryPageConfigsRsp.setConfigInfos(configInfos);
        return qryPageConfigsRsp;
    }

    /**
     * 构建配置列表
     * @param configs
     * @return
     */
    private List<ConfigInfo> convertToConfigInfos(List<Config> configs){
        List<ConfigInfo> configInfos = new ArrayList<ConfigInfo>();
        if (null != configs && configs.size()>0){
            for (Config config : configs){
                ConfigInfo configInfo = new ConfigInfo();
                configInfo.setId(config.getId());
                configInfo.setFlag(config.getFlag());
                configInfo.setValue(config.getValue());
                configInfo.setState(config.getState());
                configInfo.setBeginTime(config.getBeginTime());
                configInfo.setCreateTime(config.getCreateTime());
                configInfo.setDescription(config.getDescription());
                configInfo.setEndTime(config.getEndTime());
                configInfo.setUpdateTime(config.getUpdateTime());
                configInfo.setError(config.getError());
                configInfos.add(configInfo);
            }
        }
        return configInfos;
    }
    /**
     * 构建分页请求报文
     * @param qryPageConfigsReq
     * @return
     */
    private ConfigPageReq convertToConfigPageReq(QryPageConfigsReq qryPageConfigsReq){
        ConfigPageReq configPageReq = new ConfigPageReq();
        Config config = new Config();
        config.setFlag(qryPageConfigsReq.getFlag());
        config.setState(qryPageConfigsReq.getState());
        config.setValue(qryPageConfigsReq.getValue());
        configPageReq.setConfig(config);
        configPageReq.setPageNo(qryPageConfigsReq.getPageNo());
        configPageReq.setPageSize(qryPageConfigsReq.getPageSize());
        return configPageReq;
    }

    /**
     * 构建银行渠道是否可用返回值
     * @param rsp
     * @param bannedConfig
     * @param upgradeConfig
     * @return
     */
    private BankAvailRsp convertBankAvailableRsp(BankAvailRsp rsp, Config bannedConfig, Config upgradeConfig){
        //默认返回可用
        rsp.setFlag(true);
        if (null != bannedConfig){
            rsp.setFlag(false);
            rsp.setRspMessage("该银行支付通道被禁止");
            return rsp;
        }
        if (null != upgradeConfig){
            Date now = new Date();
            //如果当前时间在配置时间的开始结束之间
            if (now.after(upgradeConfig.getBeginTime()) && now.before(upgradeConfig.getEndTime())){
                rsp.setFlag(false);
                String endTime =  DateUtil.dateToString(upgradeConfig.getEndTime(),DateUtil.DEFAULT_DATETIME_FORMAT_SEC);
                rsp.setRspMessage(String.format("银行系统正在升级维护中，预计于%s恢复", endTime));
                if (StringUtils.isNotBlank(upgradeConfig.getError())){
                    rsp.setRspMessage(String.format(upgradeConfig.getError(),endTime));
                }
            }
            return rsp;
        }

        return rsp;
    }

    public void setConfigInfoService(ConfigInfoService configInfoService) {
        this.configInfoService = configInfoService;
    }

    public void setHibValidatorUtil(HibValidatorUtil hibValidatorUtil) {
        this.hibValidatorUtil = hibValidatorUtil;
    }
}
