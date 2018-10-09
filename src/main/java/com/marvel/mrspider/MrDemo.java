package com.marvel.mrspider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Marvel on 18/10/09.
 */
public class MrDemo {

    public static void main(String[] args) throws Exception {
        getTitleAndLinks();
    }

    public static void getTitleAndLinks() throws IOException {
        String url = "http://china.cnr.cn/yaowen/";
        Document doc = Jsoup.connect(url).timeout(5000).get();
        Elements links = doc.select("body > div.wrapper > div.middle.clearfix > div.main > div.articleList > ul > li > div > strong > a");
        for (Element link : links) {
            System.out.println("title: " + link.text());
            System.out.println("link:  " + link.attr("href"));
        }
    }

    /**
     * 提取链接
     */
    public static void getLinks() throws IOException {
        String url = "https://www.baidu.com/";
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            String linkHref = link.attr("href");
            System.out.println(linkHref);
        }
    }
}
