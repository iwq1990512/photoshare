package com.wmeup.photoshare.spring.exceptionhandler;

import com.alibaba.fastjson.support.spring.ext.FastJsonHttpMessageConverterExt;
import com.wmeup.photoshare.api.base.constants.codeConstans.CodeConstants;
import com.wmeup.photoshare.api.base.constants.codeConstans.CodeMsgConstants;
import com.wmeup.photoshare.api.base.bo.BaseResponseInfo;
import com.wmeup.util.exception.BaseException;
import com.wmeup.util.exception.BusinessException;
import com.wmeup.util.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

import static org.springframework.http.MediaType.TEXT_HTML;

/**
 * @author zy
 * @version 1.0
 * @date 16/5/19
 */

public class JsonExceptionHandler implements HandlerExceptionResolver, PriorityOrdered {
    private static final Logger logger = LoggerFactory.getLogger(JsonExceptionHandler.class);
    @Autowired
    private ServletContext servletContext;
    private int order = Ordered.HIGHEST_PRECEDENCE;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext, "org.springframework.web.servlet.FrameworkServlet.CONTEXT.springmvc");
        FastJsonHttpMessageConverterExt fastJsonHttpMessageConverter = webApplicationContext.getBean(FastJsonHttpMessageConverterExt.class);
       // ConsumerProClass consumerProClass = SpringContextHolder.getBean(ConsumerProClass.class);
        BaseResponseInfo baseResponseInfo = null;
        try {
            if (null != ex) {
                BaseException baseException = null;
                if (ex instanceof BusinessException) {
                    baseException = (BusinessException) ex;
                    logger.warn(String.format("业务异常{系统响应码:%s,响应信息:%s}", baseException.getCode(), baseException.getMsg()), ex);
                } else if (ex instanceof ValidationException) {
                    baseException = (ValidationException) ex;
                    logger.warn(String.format("参数异常{系统响应码:%s,响应信息:%s}", baseException.getCode(), baseException.getMsg()), ex);
                } else if (ex instanceof BindException) {
                    // FIXME: 16/5/31 SpringMVC中其他BUG未捕获
                    BindException o = (BindException) ex;
                    if (o.getBindingResult().getAllErrors().get(0) instanceof FieldError) {
                        FieldError fieldError = (FieldError) o.getBindingResult().getAllErrors().get(0);
                        baseException = new BaseException(CodeConstants.PARAM_ERR, fieldError.getField() + "格式错误");
                    } else {
                        baseException = new BaseException(CodeConstants.SUCCESS_CODE, CodeMsgConstants.SYSTEM_ERR.SYS);
                    }
                    logger.error(String.format("StringMVC绑定错误{系统响应码:%s,响应信息:%s}", baseException.getCode(), baseException.getMsg()), ex);
                } else {
                    baseException = new BaseException(CodeConstants.SYSTEM_ERR, CodeMsgConstants.SYSTEM_ERR.SYS);
                    logger.error(String.format("系统异常{系统响应码:%s,响应信息:%s}", baseException.getCode(), baseException.getMsg()), ex);
                }
                baseResponseInfo = reflect(handler);
                if (null == baseResponseInfo) {
                    baseResponseInfo = new BaseResponseInfo();
                }
                baseResponseInfo.setVersion("1.0");
                baseResponseInfo.setRspMessage(baseException.getMsg());
                baseResponseInfo.setRspCode(baseException.getCode());
            }
            fastJsonHttpMessageConverter.write(baseResponseInfo, TEXT_HTML, new ServletServerHttpResponse(response));
        } catch (Exception e) {
            logger.error("JsonExceptionHandler 异常捕获器 error", e);
        }
        return new ModelAndView();
    }

    private BaseResponseInfo reflect(Object handler) throws Exception {
        //研究发现，只有当GET请求是请求静态文件时(在spring配置文件里会配置静态文件的URI)，handler的实际类型会是DefaultServletHttpRequestHandler，此时强制转换就会报错,在执行强制转换之前用instanceof检查参数handler的实际类型
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Class<?> returnType = method.getReturnType();
            return (BaseResponseInfo) Class.forName(returnType.getName()).newInstance();
        }
        return null;
    }

    @Override
    public int getOrder() {
        return this.order;
    }
}
