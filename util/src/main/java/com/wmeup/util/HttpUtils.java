package com.wmeup.util;

import com.wmeup.util.exception.HttpRequestFailException;
import com.wmeup.util.exception.HttpRequestUnknownException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;

/**
 * http请求工具类
 *
 * @author zhengchao
 * @version $Id: HttpUtils.java, v 0.1 2016年5月6日 下午3:04:55 jin.qian Exp $
 */
public class HttpUtils {

    private static final String URL_PARAM_CONNECT_FLAG = "&";

    private static final int connectTimeout = 1000;

    /**
     * 日志
     */
    private static final Logger logger = Logger.getLogger(HttpUtils.class);

    private HttpUtils() {
    }

    /**
     * http get请求
     *
     * @param url     请求地址
     * @param map     请求参数
     * @param charSet 编码
     * @return
     * @throws IOException
     */
    public static List URLGet(String url, Map map, String charSet) throws IOException {
        String strtTotalURL = "";
        List result = new ArrayList();
        if (strtTotalURL.indexOf("?") == -1) {
            strtTotalURL = url + "?" + getUrl(map, charSet);
        } else {
            strtTotalURL = url + "&" + getUrl(map, charSet);
        }
        BufferedReader in = null;
        try {
            URL strUrl = new URL(strtTotalURL);
            HttpURLConnection con = (HttpURLConnection) strUrl.openConnection();
            con.setUseCaches(false);
            con.setFollowRedirects(true);
            con.setConnectTimeout(connectTimeout);
            in = new BufferedReader(new InputStreamReader(con.getInputStream(), charSet));
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                } else {
                    result.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                in.close();
        }
        return (result);
    }

    public static List URLGet2(String strUrl, Map map, String charSet) throws IOException {
        String strtTotalURL = "";
        List result = new ArrayList();
        if (strtTotalURL.indexOf("?") == -1) {
            strtTotalURL = strUrl + "?" + getUrl(map, charSet);
        } else {
            strtTotalURL = strUrl + "&" + getUrl(map, charSet);
        }
        BufferedReader in = null;
        try {
            URL url = new URL(strtTotalURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setFollowRedirects(true);
            con.setConnectTimeout(connectTimeout);
            in = new BufferedReader(new InputStreamReader(con.getInputStream(), charSet));
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                } else {
                    result.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                in.close();
        }
        return (result);
    }

    /**
     *
     */
    public static String URLSentByGet(String strUrl, String charSet) throws IOException {
        StringBuffer response = new StringBuffer();
        BufferedReader in = null;
        try {
            URL url = new URL(strUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setFollowRedirects(true);
            con.setConnectTimeout(connectTimeout);
            in = new BufferedReader(new InputStreamReader(con.getInputStream(), charSet));
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                } else {
                    response.append(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                in.close();
        }
        return response.toString();
    }

    /**
     * POST METHOD
     *
     * @param strUrl
     * @return String
     * @throws IOException
     */
    public static String URLPost(String strUrl, Map map, String charSet) throws HttpRequestFailException, HttpRequestUnknownException {
        String content = "";
        content = getUrl(map, charSet);
        URL url;
        HttpURLConnection con;
        try {
            url = new URL(strUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setAllowUserInteraction(false);
            con.setConnectTimeout(connectTimeout);
            if (null != map.get("$ReadTimeOut$")) {
                con.setReadTimeout(Integer.parseInt(map.get("$ReadTimeOut$").toString()));
            }
            con.setUseCaches(false);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=GBK");
            BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), charSet));
            bout.write(content);
            bout.flush();
            bout.close();
        } catch (Exception e) {
            throw new HttpRequestFailException(e);
        }
        return urlPostResponseRead(con, charSet);
    }

    /**
     * urlPostResponseRead 读取post请求结果
     *
     * @param connection
     * @return String
     * @throws HttpRequestUnknownException
     */
    private static String urlPostResponseRead(HttpURLConnection connection, String charSet) throws HttpRequestUnknownException {
        BufferedReader bufferedReader;
        StringBuffer result = new StringBuffer();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charSet));
            String line = bufferedReader.readLine();
            while (line != null) {
                result.append(line).append("\r\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new HttpRequestUnknownException(e);
        }
        return result.toString();

    }

    /**
     * 将map参数转换成get请求参数
     *
     * @param map Map 请求参数
     * @return String 请求url
     */
    public static String getUrl(Map map, String charSet) {
        if (null == map || map.keySet().size() == 0) {
            return "";
        }
        StringBuffer url = new StringBuffer();
        Set keys = map.keySet();
        for (Iterator i = keys.iterator(); i.hasNext(); ) {
            String key = String.valueOf(i.next());
            if (map.containsKey(key)) {
                Object val = map.get(key);
                String str = val != null ? val.toString() : "";
                try {
                    str = URLEncoder.encode(str, charSet);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                url.append(key).append("=").append(str).append(URL_PARAM_CONNECT_FLAG);
            }
        }
        String strURL = "";
        strURL = url.toString();
        if (URL_PARAM_CONNECT_FLAG.equals("" + strURL.charAt(strURL.length() - 1))) {
            strURL = strURL.substring(0, strURL.length() - 1);
        }
        return strURL;
    }

    /**
     * 执行一个HTTP GET请求，返回请求响应的HTML
     *
     * @param url         请求的URL地址
     * @param queryString 请求的查询参数,可以为null
     * @param charset     字符集
     * @param pretty      是否美化
     * @return 返回请求响应的HTML
     * @throws IOException
     */
    public static String sendByGet(String url, String queryString, String charset, boolean pretty) throws IOException {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        BufferedReader reader = null;
        try {
            // 对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串
            method.setQueryString(URIUtil.encodeQuery(queryString));
            int statusCode = client.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Send req failed: " + method.getStatusLine());
            } else {
                reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (pretty)
                        response.append(line).append(System.getProperty("line.separator"));
                    else
                        response.append(line);
                }
            }
        } catch (URIException e) {
            logger.error("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！", e);
        } catch (IOException e) {
            throw e;
        } finally {
            if (reader != null)
                reader.close();
            method.releaseConnection();
        }
        return response.toString();
    }

    /**
     * @param url     请求URL地址
     * @param params  参数
     * @param charSet 编码
     * @return 响应信息
     * @throws HttpRequestFailException    请求结果失败异常
     * @throws HttpRequestUnknownException 请求结果未知异常
     */
    public static String sendByPost(String url, Map<String, String> params, String charSet)
            throws HttpRequestFailException, HttpRequestUnknownException {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        // method.setRequestHeader("Content-Type",
        // "application/x-www-form-urlencoded");
        BufferedReader reader = null;
        // 设置Http Post数据
        if (params != null) {
            int len = params.size();
            Part[] np = new Part[len]; // new
            // NameValuePair("access_token","2.00HGyLyDoELnsC2f47a3e724sKMIRD")
            int i = 0;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                np[i++] = new StringPart(entry.getKey(), entry.getValue());
            }
            method.setRequestEntity(new MultipartRequestEntity(np, method.getParams()));
        }
        int statusCode = 0;
        try {
            statusCode = client.executeMethod(method);
        } catch (IOException e) {
            throw new HttpRequestFailException(e);
        }
        if (statusCode != HttpStatus.SC_OK) {
            throw new HttpRequestFailException("httpStatus响应不成功状态为:" + statusCode);
        } else {
            try {
                reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charSet));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (IOException e) {
                throw new HttpRequestUnknownException(e);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                        method.releaseConnection();
                    } catch (IOException e) {
                        logger.error(e);
                    }
                }
            }
        }
        return response.toString();
    }

    /**
     * @param url     请求URL地址
     * @param params  参数
     * @param charSet 编码
     * @return 响应信息
     * @throws HttpRequestFailException    请求结果失败异常
     * @throws HttpRequestUnknownException 请求结果未知异常
     * @throws IOException
     */
    public static String sendByPost(String url, byte[] params, String charSet)
            throws HttpRequestFailException, HttpRequestUnknownException, IOException {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        // method.setRequestHeader("Content-Type",
        // "application/x-www-form-urlencoded");
        BufferedReader reader = null;
        // 设置Http Post数据
        if (params != null) {
            method.setRequestEntity(new ByteArrayRequestEntity(params, charSet));
        }
        int statusCode = 0;
        try {
            statusCode = client.executeMethod(method);
        } catch (IOException e) {
            throw new HttpRequestFailException(e);
        }
        if (statusCode != HttpStatus.SC_OK) {
            throw new HttpRequestFailException("httpStatus响应不成功状态为:" + statusCode);
        } else {
            try {
                reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(),
                        charSet));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (IOException e) {
                throw new HttpRequestUnknownException(e);
            } finally {
                if (reader != null) {
                    reader.close();
                    method.releaseConnection();
                }
            }
        }
        return response.toString();
    }

    public static String sendByHttpsGet(String url, String queryString, String charset,
                                        boolean pretty) throws HttpRequestFailException,
            HttpRequestUnknownException, IOException {
        StringBuffer response = new StringBuffer();
        Protocol myhttps = new Protocol("https", new SSLProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", myhttps);
        HttpClient client = new HttpClient();
        client.getHostConfiguration().setHost("spm-api.shengpay.com", 443, myhttps);
        HttpMethod method = new GetMethod("/spm/api/realname/cached.htm");
        BufferedReader reader = null;
        method.setQueryString(URIUtil.encodeQuery(queryString));
        int statusCode = 0;
        try {
            statusCode = client.executeMethod(method);
        } catch (IOException e) {
            throw new HttpRequestFailException(e);
        }
        if (statusCode != HttpStatus.SC_OK) {
            throw new HttpRequestFailException("httpStatus响应不成功状态为:" + statusCode);
        } else {
            try {
                reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(),
                        charset));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (IOException e) {
                throw new HttpRequestUnknownException(e);
            } finally {
                if (null != reader) {
                    reader.close();
                    method.releaseConnection();
                }
            }
        }
        return response.toString();
    }

    public static String sendPost(String url, String param) throws HttpRequestUnknownException, IOException {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            throw new HttpRequestUnknownException(e);
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
        return result.toString();
    }

}
