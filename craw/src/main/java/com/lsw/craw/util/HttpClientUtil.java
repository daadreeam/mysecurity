package com.lsw.craw.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
public class HttpClientUtil {

    // httpclient连接池
    private PoolingHttpClientConnectionManager cm;

    public HttpClientUtil() {
        this.cm = new PoolingHttpClientConnectionManager();
        // 最大连接数
        this.cm.setMaxTotal(100);
        // 每个主机的最大连接数量
        this.cm.setDefaultMaxPerRoute(10);

    }

    public String doGetHtml(String url){
        // httpclient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.cm).build();
        // httpGet对象
        HttpGet httpGet = new HttpGet(url);
        // 设置请求参数
        httpGet.setConfig(this.getRequestConfig());
        // todo 重要 设置请求Request Headers中的User-Agent，告诉京东说我这是浏览器访问，
        httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
        // 执行请求
        CloseableHttpResponse response = null;
        String content = "";
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                // 得到响应
                HttpEntity entity = response.getEntity();
                assert entity != null;
                content = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert response != null;
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    public String doGetImg(String url){
        // httpclient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.cm).build();
        // httpGet对象
        HttpGet httpGet = new HttpGet(url);
        // 设置请求参数
        httpGet.setConfig(this.getRequestConfig());
        // 执行请求
        CloseableHttpResponse response = null;
        String imgName = "";
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                // todo 这里主要是图片的处理不一样，下载图片，生成图片名字，图片后缀，图片地址
                // 扩展名
                String imgType = url.substring(url.lastIndexOf("."));
                // 图片名
                imgName = UUID.randomUUID().toString() + imgType;
                // 下载图片
                FileOutputStream fos = new FileOutputStream(new File(""));
                response.getEntity().writeTo(fos);
                fos.close();
                // 返回图片名 return
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                assert response != null;
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imgName;
    }

    private RequestConfig getRequestConfig(){
        RequestConfig build = RequestConfig.custom()
                .setConnectTimeout(1000) // 连接超时时长
                .setConnectionRequestTimeout(500) // 获取连接请求的时长
                .setSocketTimeout(1000).build();// 数据传输超时时长
        return build;
    }

    public static void main(String[] args) {
        String s = "www.baidu.com/111.jpg";
        int ss = s.lastIndexOf(".");
        String rr = s.substring(ss);
        System.out.println("rr = " + rr);
    }
}
