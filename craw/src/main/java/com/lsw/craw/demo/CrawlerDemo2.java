package com.lsw.craw.demo;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.lsw.craw.util.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerDemo2 {
    public static void main(String[] args) {
//         https://item.jd.com/100012014948.html 京东的商品详情，后边是sku
        String skuId = "100012014948";
        String priceUrl = "https://p.3.cn/prices/mgets?skuIds=J_";
        HttpClientUtil httpClientUtil = new HttpClientUtil();
        String html = httpClientUtil.doGetHtml("https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&page=1");
        Document document = parseHtml(html);
        Elements elements = document.select("div#J_goodsList > ul > li");
        Element element = elements.first();
        String title = element.select("div.p-name-type-2 > a > em").first().text();
//        String price = element.select("div.p-price > strong > i").first().text();
        String spu = element.attr("data-spu");
        String promoWord = element.select("i.promo-words").first().text();
        Element psItem = element.select("ul.ps-main > li").first();
        String color = psItem.select("a.curr").first().attr("title");
        Element imgTag = psItem.select("a.curr > img").first();
        String sku = imgTag.attr("data-sku");
        String picSrc = imgTag.attr("data-lazy-img").substring(2);

        // 获取价格
        String priceJsonStr = httpClientUtil.doGetHtml(priceUrl + skuId);
        String price = ((JSONObject) JSONUtil.parseArray(priceJsonStr).get(0)).get("p").toString();
//        String text = element.html();
        System.out.println("========================");
        System.out.println(title);
        System.out.println(price);
        System.out.println(spu);
        System.out.println(promoWord);
        System.out.println("========================");

        // 商品详情页面
//        String html = new HttpClientUtil().doGetHtml("https://item.jd.com/100012014948.html");
//        Document doc = parseHtml(html);
//        String skuTitle = doc.select("div.sku-name").first().text().trim();
//        String img = doc.select("img#spec-img").attr("data-origin");
//        String price = doc.select("span.p-price").html();
//
//        System.out.println("========================");
//        System.out.println(skuTitle);
//        System.out.println(img.substring(2));
//        System.out.println(price);
//        System.out.println("========================");


    }

    public static Document parseHtml(String htmlStr) {
        return Jsoup.parse(htmlStr);
    }
}
