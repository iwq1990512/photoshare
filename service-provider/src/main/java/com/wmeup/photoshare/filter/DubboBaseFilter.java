package com.wmeup.photoshare.filter;


import com.alibaba.dubbo.rpc.*;
import com.alibaba.fastjson.JSON;
import com.wmeup.photoshare.api.base.constants.baseConstants.BaseConstants;
import com.wmeup.photoshare.api.base.constants.codeConstans.CodeConstants;
import com.wmeup.photoshare.api.base.constants.codeConstans.CodeMsgConstants;
import com.wmeup.photoshare.api.base.bo.BaseRequestInfo;
import com.wmeup.photoshare.api.base.bo.BaseResponseInfo;
import com.wmeup.util.exception.BusinessException;
import com.wmeup.util.exception.ValidationException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.lang.reflect.Method;

/**
 * <p>Title: DubboBaseInterceptor</p>
 * <p>Description: DubboBaseInterceptor<p>
 * <p>Copyright: Copyright (c) 2016</p>
 * @author zy
 * @version 1.0
 * @date 16/5/18
 */
public class DubboBaseFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(DubboBaseFilter.class);

    private static final String INTER = "interface";
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        BaseResponseInfo baseResponseInfo = null;
        BaseRequestInfo baseRequestInfo = null;
        Result result = null;
        try {
            //反射获取请求参数
            baseRequestInfo = reflectReq(invocation);
            if (null != baseRequestInfo && StringUtils.isNotBlank(baseRequestInfo.getRequestId())) {
                MDC.put("requestId", baseRequestInfo.getRequestId());
            }
            //反射获取返回相关参数
            baseResponseInfo = reflectRsp(invocation);
            //若参数为空
            if (null == baseRequestInfo) {
                BaseResponseInfo baseResponseInfo2 = new BaseResponseInfo();
                baseResponseInfo2.setVersion("1.0");
                baseResponseInfo2.setRspCode(CodeConstants.PARAM_ERR);
                baseResponseInfo2.setRspMessage(CodeMsgConstants.PARAM_ERR.PARAM_NULL);
                logger.info("返回参数信息{}", JSON.toJSONString(baseResponseInfo2));
                return new RpcResult(baseResponseInfo2);
            }
            logger.info("调用dubbo接口参数是{}", JSON.toJSONString(baseRequestInfo));
            //校验基本参数
            BaseResponseInfo baseResponseInfo1 = checkBasePara(baseRequestInfo, baseResponseInfo);
            //若基本校验不通过
            if (null != baseResponseInfo1) {
                baseResponseInfo.setVersion("1.0");
                //现在只有RPC协议
                logger.info("返回参数信息{}", JSON.toJSONString(baseResponseInfo1));
                return new RpcResult(baseResponseInfo1);
            }
            result = invoker.invoke(invocation);
            //若返回结果中有异常就补获
            if (result.hasException()) {
                Exception exception = (Exception) result.getException();
                baseResponseInfo = checkException(exception, baseResponseInfo);
                baseResponseInfo.setVersion("1.0");
                logger.info("返回参数信息{}", JSON.toJSONString(baseResponseInfo));
                return new RpcResult(baseResponseInfo);
            }
            logger.info("返回参数信息{}", JSON.toJSONString(result));
        } catch (Exception e) {
            if (null == baseResponseInfo) {
                baseResponseInfo = new BaseResponseInfo();
            }
            baseResponseInfo.setVersion("1.0");
            baseResponseInfo.setRspMessage(CodeMsgConstants.SYSTEM_ERR.SYS);
            baseResponseInfo.setRspCode(CodeConstants.SYSTEM_ERR);
            logger.error(String.format("系统异常{系统响应码:%s,响应信息:%s}", baseResponseInfo.getRspCode(), baseResponseInfo.getRspMessage()), e);
            result = new RpcResult(baseRequestInfo);
        } finally {
            MDC.remove("requestId");
        }
        return result;
    }

    private BaseResponseInfo checkBasePara(BaseRequestInfo baseRequestInfo, BaseResponseInfo baseResponseInfo) throws Exception {
        String version = StringUtils.trim(baseRequestInfo.getVersion());
        String requestId = StringUtils.trim(baseRequestInfo.getRequestId());
        String channel = StringUtils.trim(baseRequestInfo.getChannel());
        if (StringUtils.isBlank(version)) {
            baseResponseInfo.setRspCode(CodeConstants.PARAM_ERR);
            baseResponseInfo.setRspMessage(CodeMsgConstants.PARAM_ERR.PARAM_VERNN);
            logger.warn("验证异常[系统响应码:{},响应信息:{}]", baseResponseInfo.getRspCode(), baseResponseInfo.getRspMessage());
            return baseResponseInfo;
        }
        if (StringUtils.isBlank(requestId)) {
            baseResponseInfo.setRspCode(CodeConstants.PARAM_ERR);
            baseResponseInfo.setRspMessage(CodeMsgConstants.PARAM_ERR.PARAM_REQIDNN);
            logger.warn("验证异常[系统响应码:{},响应信息:{}]", baseResponseInfo.getRspCode(), baseResponseInfo.getRspMessage());
            return baseResponseInfo;
        }
        if (requestId.length() > 64) {
            baseResponseInfo.setRspCode(CodeConstants.PARAM_ERR);
            baseResponseInfo.setRspMessage(CodeMsgConstants.PARAM_ERR.PARAM_REQID_TOOLONG);
            logger.warn("验证异常[系统响应码:{},响应信息:{}]", baseResponseInfo.getRspCode(), baseResponseInfo.getRspMessage());
            return baseResponseInfo;
        }
        if (StringUtils.isBlank(channel)) {
            baseResponseInfo.setRspCode(CodeConstants.PARAM_ERR);
            baseResponseInfo.setRspMessage(CodeMsgConstants.PARAM_ERR.PARAM_CHANNELNN);
            logger.warn("验证异常[系统响应码:{},响应信息:{}]", baseResponseInfo.getRspCode(), baseResponseInfo.getRspMessage());
            return baseResponseInfo;
        }
        if (!channelExist(channel)) {
            baseResponseInfo.setRspCode(CodeConstants.PARAM_ERR);
            baseResponseInfo.setRspMessage(CodeMsgConstants.PARAM_ERR.PARAM_CHANNEL_NM);
            logger.warn("验证异常[系统响应码:{},响应信息:{}]", baseResponseInfo.getRspCode(), baseResponseInfo.getRspMessage());
            return baseResponseInfo;
        }
        return null;
    }

    private boolean channelExist(String channel) {
        for (BaseConstants baseConstants : BaseConstants.values()) {
            if (baseConstants.name().contains("Channel") && baseConstants.getCode().equals(channel)) {
                return true;
            }
        }
        return false;
    }

    private BaseRequestInfo reflectReq(Invocation invocation) throws Exception {
        Object[] arguments = invocation.getArguments();
        if (null == arguments) {
            return null;
        }
        //此处因为统一dubbo接口的入参是只能为一个参数并且继承BaseRequestInfo  所以我只需取第一个参数 其他情况暂不做考虑
        if (arguments[0] instanceof BaseRequestInfo) {
            return (BaseRequestInfo) arguments[0];
        } else {
            return null;
        }
    }

    private BaseResponseInfo reflectRsp(Invocation invocation) throws Exception {
        String inter = invocation.getAttachments().get(INTER);
        Class<?> aClass = Class.forName(inter);
        Method method = aClass.getMethod(invocation.getMethodName(), invocation.getParameterTypes());
        logger.info("调用dubbo接口名是:{},调用方法是:{}", inter, invocation.getMethodName());
        Class<?> returnType = method.getReturnType();
        return (BaseResponseInfo) returnType.newInstance();
    }

    private BaseResponseInfo checkException(Exception e, BaseResponseInfo baseResponseInfo) {
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            baseResponseInfo.setRspMessage(businessException.getMsg());
            baseResponseInfo.setRspCode(businessException.getCode());
            logger.warn(String.format("业务异常{系统响应码:%s,响应信息:%s}", baseResponseInfo.getRspCode(), baseResponseInfo.getRspMessage()), e);
            return baseResponseInfo;
        } else if (e instanceof ValidationException) {
            ValidationException validationException = (ValidationException) e;
            baseResponseInfo.setRspMessage(validationException.getMsg());
            baseResponseInfo.setRspCode(validationException.getCode());
            logger.warn(String.format("验证异常{系统响应码:%s,响应信息:%s}", baseResponseInfo.getRspCode(), baseResponseInfo.getRspMessage()), e);
            return baseResponseInfo;
        } else {
            baseResponseInfo.setRspMessage(CodeMsgConstants.SYSTEM_ERR.SYS);
            baseResponseInfo.setRspCode(CodeConstants.SYSTEM_ERR);
            logger.error(String.format("系统异常{系统响应码:%s,响应信息:%s}", baseResponseInfo.getRspCode(), baseResponseInfo.getRspMessage()), e);
            return baseResponseInfo;
        }
    }

}
