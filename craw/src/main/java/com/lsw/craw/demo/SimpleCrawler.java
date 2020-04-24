package com.lsw.craw.demo;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.lsw.craw.entity.JdPhone;
import com.lsw.craw.mapper.JdPhoneMapper;
import com.lsw.craw.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.LinkedList;

@Slf4j
@Component
public class SimpleCrawler {
//    public static void main(String[] args) {
//
//    }
    @Autowired
    private JdPhoneMapper phoneMapper;

    @Autowired
    private HttpClientUtil hcUtil;

    private static final String PRICE_URL = "https://p.3.cn/prices/mgets?skuIds=J_";

    public SimpleCrawler() {
    }

    public void crawlerJd(String prodSearchUrl){
//        HttpClientUtil hcUtil = new HttpClientUtil();
        String htmlStr = hcUtil.doGetHtml(prodSearchUrl);
//        System.out.println(htmlStr);
        Document doc = this.parseHtml(htmlStr);
        this.doJd(doc);
    }

    public void doJd(Document document){
        // 商品列表
        Elements elements = document.select("div#J_goodsList > ul > li");

        String dataStr = DateUtil.format(DateTime.now(), DatePattern.PURE_DATETIME_PATTERN);
        for (Element spuElem : elements) {
            // 先保存第一个试一下
//            spuElem = elements.first();

            String title = spuElem.select("div.p-name-type-2 > a > em").first().text();
            String spuAttr = spuElem.attr("data-spu");
            if (StrUtil.isBlank(spuAttr)) {
                continue;
            }
            Long spuId = Long.parseLong(spuAttr);
            String promoWord = spuElem.select("i.promo-words").first().text();


            Elements skuElems = spuElem.select("ul.ps-main > li");
            LinkedList<JdPhone> list = new LinkedList<>();

            // 遍历多个sku
            // 获取价格
            for (Element skuElem : skuElems) {
                Element imgTag = skuElem.select("a > img").first();
                Long skuId = Long.parseLong(imgTag.attr("data-sku")) ;
                // todo 加上skuid的判断重复
                if (phoneMapper.selectBySkuId(skuId) != null) {
                    continue;
                }

                try {

                    String originPicUrl = imgTag.attr("data-lazy-img");
                    log.info("原始的图片地址" + originPicUrl);
                    String picUrl;
                    String imgName = "空";
                    if (!StrUtil.isEmpty(originPicUrl)) {
                        picUrl = originPicUrl.substring(2).replaceFirst("/n9/", "/n1/");
                        imgName = hcUtil.doGetImg("https://" + picUrl, dataStr);
                    }
                    String color = skuElem.select("a").first().attr("title");
                    String imgPath = "/Users/daadreeam/Downloads/baidudisk/crawler-images/" + dataStr;

                    String priceJsonStr = hcUtil.doGetHtml(PRICE_URL + skuId);
                    BigDecimal price = new BigDecimal(((JSONObject) JSONUtil.parseArray(priceJsonStr).get(0)).get("p").toString());

                    JdPhone build = JdPhone.builder().title(title)
                            .color(color)
                            .imgName(imgName).imgPath(imgPath).price(price).promoWord(promoWord)
                            .skuId(skuId).spuId(spuId).createTime(DateTime.now()).updateTime(DateTime.now()).build();
                    list.addLast(build);
                } catch (NumberFormatException e) {
                    log.info("出错的skuid: " + skuId);
                    e.printStackTrace();
                }
            }

            if (!list.isEmpty()) {
                phoneMapper.batchInsert(list);
            }
        }


    }

    public Document parseHtml(String htmlStr) {
        return Jsoup.parse(htmlStr);
    }
}
