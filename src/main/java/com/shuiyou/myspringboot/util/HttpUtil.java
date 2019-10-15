package com.shuiyou.myspringboot.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Slf4j
public class HttpUtil {

    private static HttpUtil instance;

    private static CloseableHttpClient httpClient;

    public static HttpUtil getInstance() {
        return getInstance(null);
    }

    public static HttpUtil getInstance(CloseableHttpClient client) {
        if (null == instance) {
            synchronized (HttpUtil.class) {
                if (null == instance) {
                    instance = client == null ? new HttpUtil() : new HttpUtil(client);
                }
            }
        }
        return instance;
    }

    private HttpUtil(CloseableHttpClient client) {
        httpClient = client == null ? HttpClients.createDefault() : client;
    }

    private HttpUtil() {
        this(null);
    }

    /**
     * post请求传输
     * @param url
     * @param headerMap
     * @param requestEntity
     * @return
     * @throws Exception
     */
    public String sendPost(String url, Map<String, String> headerMap, HttpEntity requestEntity) throws Exception {
        try {
            HttpPost request = new HttpPost(url);
            // 添加请求头
            Set<Map.Entry<String, String>> entries = headerMap.entrySet();
            for (Map.Entry<String, String> entry : entries){
                request.addHeader(entry.getKey(), entry.getValue());
            }
            request.setEntity(requestEntity);
            request.setConfig(RequestConfig.custom()
                    .setConnectTimeout(5000)
                    .setSocketTimeout(5000)
                    .setConnectionRequestTimeout(1000)
                    .build());
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity responseEntity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();
            String responseString= EntityUtils.toString(responseEntity, "UTF-8");
            if (statusLine.getStatusCode() >= 400) {
                throw new HttpException(String.format("%s接口调用失败：%s,%s",
                        url, statusLine.getStatusCode(), statusLine.getReasonPhrase()));
            }
            return responseString;
        } catch (HttpException httpException) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题。
            log.error("request url:{},发生HttpException异常!", url, httpException);
            throw httpException;
        } catch (IOException ioException) {
            // 发生网络异常
            log.error("request url:{},发生IOException异常!", url, ioException);
            throw ioException;
        }
    }
    /**
     * get请求传输数据
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String sendGetData(String url) {
        String result = "";

        // 创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建get方式请求对象
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-type", "application/json");
        // 通过请求对象获取响应对象
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            // 获取结果实体
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            }
            // 释放链接
            response.close();
        } catch (IOException e) {
            log.error("HttpClient get error:" + e.getMessage(), e);
        }

        return result;
    }

    public static CloseableHttpClient getCloseableHttpClient(){
        return httpClient;
    }
}
