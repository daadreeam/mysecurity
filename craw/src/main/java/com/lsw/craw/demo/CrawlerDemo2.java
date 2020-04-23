package com.lsw.craw.demo;

import com.lsw.craw.util.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerDemo2 {
    public static void main(String[] args) {
        String html = new HttpClientUtil().doGetHtml("https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&page=1");
        Document document = parseHtml(html);
        Elements elements = document.select("div#J_goodsList > ul > li");
        Element element = elements.get(0);
        String text = element.text();
        System.out.println("text = " + text);
    }

    public static Document parseHtml(String htmlStr) {
        return Jsoup.parse(htmlStr);
    }
}
