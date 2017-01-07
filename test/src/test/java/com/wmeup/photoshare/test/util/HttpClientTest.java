package com.wmeup.photoshare.test.util;

import com.alibaba.fastjson.JSON;
import com.wmeup.util.exception.HttpRequestFailException;
import com.wmeup.util.exception.HttpRequestUnknownException;
import com.wmeup.util.http.HttpConfigCallback;
import com.wmeup.util.http.HttpcomponentsUtils;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author zy
 */
public class HttpClientTest {
    @Test
    public void testHttp() throws UnsupportedEncodingException, HttpRequestFailException, HttpRequestUnknownException {
        //100.66.162.149
        String xx = "{\"channel\":\"PGW\",\"idNo\":\"21018119871018801X\",\"idType\":\"00\",\"mblNo\":\"18767121008\",\"newUsrName\":\"叶子法\",\"oldUsrName\":\"叶子法是是\",\"requestId\":\"201505101339\",\"sign\":\"423d2f012b7e75fb367f1fb5f1c540bf\",\"signType\":\"MD5\",\"usrPtpNo\":\"1001000000001045\",\"version\":\"1.0\"}";
        Map map = JSON.parseObject(xx,Map.class);
        HttpcomponentsUtils.post(map, "http://100.73.12.17:8080/idauth/checkId", String.class, new HttpConfigCallback() {
            @Override
            public void callback(Request request, Form form) {
                request.connectTimeout(1000);
                request.socketTimeout(5000);
                request.bodyForm(form.build(), Charset.forName("UTF-8"));
            }
        });
    }
}
