package com.lsw.craw.util;

import cn.hutool.core.date.DateUtil;
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
import java.util.Date;
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
//        httpGet.addHeader("authority", "item.jd.com");
//        httpGet.addHeader("cookie", "shshshfpa=37c61904-166b-676c-0abd-72e2594b360d-1572847513; shshshfpb=uNXZdXNBR3RKq87kmzgiAZw%3D%3D; pinId=VoAr9w2AxXXUtYvudZGMkLV9-x-f3wj7; UM_distinctid=16efa947ebcaf7-0b299cd0d09bd7-3964720e-13c680-16efa947ebd9ca; __jdu=1579516996961315294019; TrackID=15BTg0u4MqnLnxHng-7DYyGzheB14uuS5xoJCIwdDZ4RVZ3FMhODWXUUK7-uwfJx0QDf80CArBqfECBVsEz-hw5swVUdoCcyPsbAmBjQaelDRKTylWmx0Yv3ndfvN-ERp; areaId=1; ft_qgd=1582636930000; ipLoc-djd=1-2800-55811-0; __jdc=122270672; 3AB9D23F7A4B3C9B=WG4MPV6T5ESBN3QWMMEENFYX7A3XAV24WMVOU7DZ72DQUIDNTBMVQKPVGHW74Q2HCXU6KVM5MRIIXKARNEPPBRMUNI; shshshfp=f1e094e483fff47d9179e8e9cffac530; unpl=V2_ZzNtbUteShYlAE8BKUkIAGICF11LB0VGdQpCUytMCQcyBBJaclRCFnQUR1FnGl4UZwQZWEVcRxRFCEZkexhdBW4AGl9DVXMldQlHVXoYVQBiBiJeQmdCJXUMQFx%2fGVsNbwQaWkJVQxF2AENVexFsNWcLFW1CVkIUdQtHVH4RWQxXVUwIQlJFJXQ4R2R7EVwHbwoQVEBec0cbCEVVcxxUAW4BDllHUkcWc0VGUH0RWAVgCxpaSlBDF3UMRVx%2bGFwNVwIiXg%3d%3d; CCC_SE=ADC_cZjbGk%2bSIRzSKQBSvhX0flAIuUl4IYbrm7wZauujt8yOwgtK%2b4%2b41UntRNEZuXp95ubM4CBDd3lzTtV5cW8zw5skavtL%2bU1prsT4R4wA8qdT3GUnaglj6doFjDNkrSMb%2bVrwZjYE4AOmRsCzGdR3BIltgbFhGfJ4mrUbqts1DnIxB7FPqD0Bldu3b3mnDVNFwf5q4TRciwXZf61zTqySbO2IqMQQOyxJ86H%2b3VVGvHIZZuzFoiDySRhggRl9Kv0F11F7rBxDzhyqAPVMNly19nAYPOJEaJ%2f1oTIsxMVCsuqwJ%2bEpGmAFvWU0KbGHZI6LD20ONPS5RaVc9LmRMw46KzwuZW5ZUrzEHK1HMMe6xVj%2bXz7stguy9h0WqJp4uu%2bNkZM%2bbULxqnbmNUKXgXEM6ySzPg1vwNz1O8%2bIg%2byfdc19qzV%2fhzpA87sG7m0QPHzwfo0lWfsCWHXX%2fVG6JcygXRcn2UIk5Qj0Evf%2fSgxnt534LFaaGYAVExyYZH%2fLjY%2figPmY5BaIFYowKRq%2fv1J5Gx5sGeuoNezFQmlKmt%2fny43RtrtQPTacvPXyENjYJzej6w2RShpbTeegXewSo5Jk6g%3d%3d; wq_logid=1587631610.1527428508; wxa_level=1; retina=1; cid=3; wqmnx1=MDEyNjM5Ni9qZWswPV9jaWRmYV8wZTk0NjFhY0kgMWw1SGVDLjYvMTJhM09PJkg%3D; webp=1; __jda=122270672.1579516996961315294019.1579516997.1587630109.1587631612.10; __jdv=122270672|xcx.ubja.vip|t_1001829303_|jingfen|8882a98dcae440418a7b1356add3d616|1587631611814; shshshsID=554483982ea759a27720bcaa8ff2ecc4_5_1587632827292; __jdb=122270672.3.1579516996961315294019|10.1587631612; CNZZDATA1256793290=1123343977-1576158077-https%253A%252F%252Fsearch.jd.com%252F%7C1587628577");
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

    public String doGetImg(String url, String timeStr){
        // httpclient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.cm).build();
        // httpGet对象
        HttpGet httpGet = new HttpGet(url);
        // 设置请求参数
        httpGet.setConfig(this.getRequestConfig());
        httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
//        httpGet.addHeader("authority", "item.jd.com");
//        httpGet.add
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
                File file = new File("/Users/daadreeam/Downloads/baidudisk/crawler-images/" + timeStr + "/" + imgName);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdir();
                }
                FileOutputStream fos = new FileOutputStream(file);
                response.getEntity().writeTo(fos);
                fos.close();
                // 返回图片名 return
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (response != null) {

                    response.close();
                }
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
//        String s = "www.baidu.com/111.jpg";
//        int ss = s.lastIndexOf(".");
//        String rr = s.substring(ss);
//        System.out.println("rr = " + rr);
        String url = "http://img14.360buyimg.com/n1/jfs/t1/59932/7/11066/271889/5d898aeeE8f6658db/dc5164850933d966.jpg";
        String imgName = new HttpClientUtil().doGetImg(url, DateUtil.format(new Date(), "yyyyMMddHHmmss"));
        System.out.println("imgName = " + imgName);
    }
}
