package com.wmeup.util.http;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.wmeup.util.exception.HttpRequestFailException;
import com.wmeup.util.exception.HttpRequestUnknownException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

/**
 * @author  zy
 */
public abstract class HttpcomponentsUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpcomponentsUtils.class);

    /**
     * HTTPPost请求处理
     * @param prameters 请求参数
     * @param url 请求地址
     * @param resultClass 返回的json结果
     * @param callback 请求配置的回调
     * @param <T>
     * @return
     * @throws HttpRequestFailException http调用失败的异常
     * @throws HttpRequestUnknownException http调用未知异常
     */
    public static  final  <T>  T post(Map<String,String> prameters,String url,Class<T> resultClass,HttpConfigCallback callback) throws HttpRequestFailException, HttpRequestUnknownException {
        T t = null;
        if(prameters==null){
            throw  new IllegalArgumentException("prameters not null");
        }
        if(url==null){
            throw  new IllegalArgumentException("url不能为空");
        }
        if(resultClass==null){
            throw  new IllegalArgumentException("resultClass not null");
        }
        if(callback==null){
            throw  new IllegalArgumentException("callback not null");
        }
        logger.info("调用外部系统接口地址:{}",url);
        Form form = Form.form();
        Iterator<Entry<String, String>> iterator = prameters.entrySet().iterator();
        while(iterator.hasNext()){
        	Entry<String, String> next = iterator.next();
        	if(StringUtils.hasText(next.getValue())){
        		form.add(next.getKey(),next.getValue());
        	}
        }
        Request request = Request.Post(url);
        //参数回调
        callback.callback(request,form);
        logger.info("请求参数:{}",JSON.toJSON(prameters));
        Response response = null;
        try {
            response = request.execute();
        } catch (ConnectException e) {
            logger.error("请求外部接口超时");
            throw new HttpRequestFailException(e);
        }catch (ClientProtocolException e) {
            logger.error("请求外部接口异常");
            throw new HttpRequestFailException(e);
        }catch (SocketTimeoutException e) {
            logger.error("读取外部接口返回信息超时");
            throw new HttpRequestUnknownException(e);
        } catch (IOException e) {
            logger.error("调用外部接口异常");
            throw  new HttpRequestUnknownException(e);
        }
        String context = null;
        try {
            context = response.returnContent().asString();
            logger.info("返回结果:{}",context);
        } catch (IOException e) {
        	logger.error("返回结果异常:{}",context);
            throw  new HttpRequestUnknownException(e);
        }
        try{
            t =   JSON.parseObject(context,resultClass);
        }catch (JSONException e){
            logger.error("无法序列化成Json");
            throw new HttpRequestFailException(e);
        }
        return  t;
    }
    
   

}
