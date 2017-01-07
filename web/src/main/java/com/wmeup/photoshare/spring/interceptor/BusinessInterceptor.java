package com.wmeup.photoshare.spring.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.ext.FastJsonHttpMessageConverterExt;
import com.wmeup.photoshare.api.base.constants.baseConstants.BaseConstants;
import com.wmeup.photoshare.api.base.constants.codeConstans.CodeConstants;
import com.wmeup.photoshare.api.base.constants.codeConstans.CodeMsgConstants;
import com.wmeup.photoshare.api.base.bo.BaseResponseInfo;
import com.wmeup.util.md5.MD5Util;
import com.wmeup.util.md5.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.TEXT_HTML;

/**
 * <p>Title: BusinessInterceptor</p>
 * <p>Description: 基础拦截器<p>
 * <p>Copyright: Copyright (c) 2016</p>
 *
 * @author zy
 * @version 1.0
 * @date 16/5/12
 */
public class BusinessInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(BusinessInterceptor.class);
    @Autowired
    private ServletContext servletContext;

    private String MD5Switch;

    private String secretKey;


    public void setMD5Switch(String MD5Switch) {
        this.MD5Switch = MD5Switch;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }


    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false 返回
     * 如果返回true
     * 执行下一个拦截器,直到所有的拦截器都执行完毕
     * 再执行被拦截的Controller
     * 然后进入拦截器链,
     * 从最后一个拦截器往回执行所有的postHandle()
     * 接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO: 2016/9/6 consumer
        // ConsumerProClass consumerProClass = SpringContextHolder.getBean(ConsumerProClass.class);
        String requestUri = request.getRequestURI();
        BaseResponseInfo baseResponseInfo = checkBasePara(request, handler);
        if (null != baseResponseInfo) {
            baseResponseInfo.setVersion("1.0");
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext, "org.springframework.web.servlet.FrameworkServlet.CONTEXT.springmvc");
            FastJsonHttpMessageConverterExt fastJsonHttpMessageConverter = webApplicationContext.getBean(FastJsonHttpMessageConverterExt.class);
            fastJsonHttpMessageConverter.write(baseResponseInfo, TEXT_HTML, new ServletServerHttpResponse(response));
            return false;
        }
        return true;
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     * 可在modelAndView中加入数据，比如当前时间
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //结束后处理删除requestId
        MDC.remove("requestId");
    }


    private BaseResponseInfo checkBasePara(HttpServletRequest request, Object handler) throws Exception {
        String channel = StringUtils.trim(request.getParameter("channel"));
        String version = StringUtils.trim(request.getParameter("version"));
        String requestId = StringUtils.trim(request.getParameter("requestId"));
        String signType = StringUtils.trim(request.getParameter("signType"));
        String sign = StringUtils.trim(request.getParameter("sign"));
        BaseResponseInfo baseResponseInfo = null;
        if (StringUtils.isBlank(requestId)) {
            baseResponseInfo = reflect(handler);
            baseResponseInfo.setRspCode(CodeConstants.PARAM_ERR);
            baseResponseInfo.setRspMessage(CodeMsgConstants.PARAM_ERR.PARAM_REQIDNN);
            return baseResponseInfo;
        }
        if (requestId.length() > 64) {
            baseResponseInfo = reflect(handler);
            baseResponseInfo.setRspCode(CodeConstants.PARAM_ERR);
            baseResponseInfo.setRspMessage(CodeMsgConstants.PARAM_ERR.PARAM_REQID_TOOLONG);
            return baseResponseInfo;
        }
        MDC.put("requestId", request.getParameter("requestId"));
        String requestUri = request.getRequestURI();
        logger.info(String.format("requestUri:%s,调用http接口参数是:%s", requestUri, JSON.toJSONString(request.getParameterMap())));
        if (StringUtils.isBlank(version)) {
            baseResponseInfo = reflect(handler);
            baseResponseInfo.setRspCode(CodeConstants.PARAM_ERR);
            baseResponseInfo.setRspMessage(CodeMsgConstants.PARAM_ERR.PARAM_VERNN);
            return baseResponseInfo;
        }
        if (!versionExist(version)) {
            baseResponseInfo = reflect(handler);
            baseResponseInfo.setRspCode(CodeConstants.PARAM_ERR);
            baseResponseInfo.setRspMessage(CodeMsgConstants.PARAM_ERR.PARAM_VERNM);
            return baseResponseInfo;
        }
        if (StringUtils.isBlank(signType)) {
            baseResponseInfo = reflect(handler);
            baseResponseInfo.setRspCode(CodeConstants.PARAM_ERR);
            baseResponseInfo.setRspMessage(CodeMsgConstants.PARAM_ERR.PARAM_SIGNTYPNN);
            return baseResponseInfo;
        }
        if (!signTypeExist(signType)) {
            baseResponseInfo = reflect(handler);
            baseResponseInfo.setRspCode(CodeConstants.PARAM_ERR);
            baseResponseInfo.setRspMessage(CodeMsgConstants.PARAM_ERR.PARAM_SIGNTYP_NM);
            return baseResponseInfo;
        }
        if (signType.length() > 20) {
            baseResponseInfo = reflect(handler);
            baseResponseInfo.setRspCode(CodeConstants.PARAM_ERR);
            baseResponseInfo.setRspMessage(CodeMsgConstants.PARAM_ERR.PARAM_SIGNTYP_TOOLONG);
            return baseResponseInfo;
        }
        if (StringUtils.isBlank(sign)) {
            baseResponseInfo = reflect(handler);
            baseResponseInfo.setRspCode(CodeConstants.PARAM_ERR);
            baseResponseInfo.setRspMessage(CodeMsgConstants.PARAM_ERR.PARAM_SIGNNN);
            return baseResponseInfo;
        }
        if (StringUtils.isBlank(channel)) {
            baseResponseInfo = reflect(handler);
            baseResponseInfo.setRspCode(CodeConstants.PARAM_ERR);
            baseResponseInfo.setRspMessage(CodeMsgConstants.PARAM_ERR.PARAM_CHANNELNN);
            return baseResponseInfo;
        }
        if (!channelExist(channel)) {
            baseResponseInfo = reflect(handler);
            baseResponseInfo.setRspCode(CodeConstants.PARAM_ERR);
            baseResponseInfo.setRspMessage(CodeMsgConstants.PARAM_ERR.PARAM_CHANNEL_NM);
            return baseResponseInfo;
        }
        if ("true".equals(MD5Switch)) {
            Map map = request.getParameterMap();
            Map<String, String> map1 = new HashMap<String, String>();
            for (Object s : map.keySet()) {
                String s1 = (String) s;
                map1.put(s1, request.getParameter(s1));
            }
            JSONObject signJson = encryPtion(map1);
            if (!signJson.getString("signMD5").equals(request.getParameter("sign"))) {
                logger.error("MD5校验失败，请求sign：" + request.getParameter("sign") + "服务器MD5：" + signJson.getString("signMD5"));
                baseResponseInfo = reflect(handler);
                baseResponseInfo.setRspCode(CodeConstants.PARAM_ERR);
                baseResponseInfo.setRspMessage(CodeMsgConstants.PARAM_ERR.PARAM_MD5_FAIL);
                return baseResponseInfo;
            }
        }
        return baseResponseInfo;
    }

    private boolean channelExist(String channel) {
        for (BaseConstants baseConstants : BaseConstants.values()) {
            if (baseConstants.name().contains("Channel") && baseConstants.getCode().equals(channel)) {
                return true;
            }
        }
        return false;
    }

    private boolean signTypeExist(String signType) {
        for (BaseConstants baseConstants : BaseConstants.values()) {
            if (baseConstants.name().contains("SIGNTYPE") && baseConstants.getCode().equals(signType)) {
                return true;
            }
        }
        return false;
    }

    private boolean versionExist(String version) {
        for (BaseConstants baseConstants : BaseConstants.values()) {
            if (baseConstants.name().contains("VERSION") && baseConstants.getCode().equals(version)) {
                return true;
            }
        }
        return false;
    }

    private BaseResponseInfo reflect(Object handler) throws Exception {
        //研究发现，只有当GET请求是请求静态文件时(在spring配置文件里会配置静态文件的URI)，handler的实际类型会是DefaultServletHttpRequestHandler，此时强制转换就会报错,在执行强制转换之前用instanceof检查参数handler的实际类型
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Class<?> returnType = method.getReturnType();
            return (BaseResponseInfo) returnType.newInstance();
        }
        return null;
    }

    /**
     * @param map
     * @return
     * @brief 拼接参数进行加密 校验
     * @author - yzf 2016/5/9
     */
    private JSONObject encryPtion(Map<String, String> map) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String charset = "UTF-8";
        JSONObject jsonObject = new JSONObject();
        map.remove("sign");
        map = MapUtils.removeNull(map);
        String sortString = MapUtils.sortStringByLine(map);
        String signMD5 = MD5Util.getMD5String((sortString + secretKey), charset);
        jsonObject.put("map", map);
        jsonObject.put("signMD5", signMD5);
        return jsonObject;
    }


}
