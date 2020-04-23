package com.lsw.craw.demo;

import com.lsw.craw.util.HttpClientUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CrawlerBase {

    /**
     * jsoup方式 获取虎扑新闻列表页
     * @param url 虎扑新闻列表页url
     */
    public void jsoupList(String url){
        try {
            Document document = Jsoup.connect(url).get();
            // 使用 css选择器 提取列表新闻 a 标签
            // <a href="https://voice.hupu.com/nba/2484553.html" rel="external nofollow" target="_blank">霍华德：夏休期内曾节食30天，这考验了我的身心</a>
            Elements elements = document.select("div.news-list > ul > li > div.list-hd > h4 > a");
//            Elements elements = document.select("div.news-list > ul > li");
            for (Element element:elements){
//    System.out.println(element);
                // 获取详情页链接
                String d_url = element.attr("href");
                // 获取标题
                String title = element.ownText();
                String title2 = element.text();

                //=========

                System.out.println("详情页链接："+d_url+" ,详情页标题："+title);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void httpClientList(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                System.out.println(content);
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

    }

    public static void main(String[] args) {
        String url = "https://voice.hupu.com/nba";
        String url2 = "https://www.baidu.com";
        CrawlerBase crawlerBase = new CrawlerBase();
//        crawlerBase.jsoupList(url);
//        crawlerBase.httpClientList(url2);
        HttpClientUtil httpClientUtil = new HttpClientUtil();
        String content = httpClientUtil.doGetHtml(url);
        System.out.println("content = " + content);

    }

}
