package com.marvel.mrspider;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * 爬 http://china.cnr.cn/yaowen/ 的所有要闻
 * Created by Marvel on 18/10/09.
 */
public class ChinaCnrCnDemo {

    private static Queue<String> todo = new LinkedList<String>();
    private static Set<String> visited = new HashSet<String>();
    private static WebClient webClient = null;

    public static void main(String[] args) throws Exception {
        String firstUrl = "http://china.cnr.cn/yaowen/";
        todo.add(firstUrl);
        WebClient wc = getWebClient();

        while (!todo.isEmpty()) {
            URL url = new URL(todo.poll());
            System.out.println("【url】: " + url);

            // 由于该网站的下一页路径是通过 js 计算得出，所以要借助 HtmlUnit 来渲染网页
            HtmlPage page = wc.getPage(url);
            wc.waitForBackgroundJavaScript(1000);
            String pageXml = page.asXml();
            Document doc = Jsoup.parse(pageXml);//获取html文档

            // 内容解析
            Elements ul = doc.select("body > div.wrapper > div.middle.clearfix > div.main > div.articleList > ul > li");
            for (Element li : ul) {
                Element titleLink = li.selectFirst("strong > a");
                Element description = li.selectFirst("div > p");
                Element publishTime = li.selectFirst("li > span");
                System.out.println("title: " + titleLink.text());
                System.out.println("href: " + titleLink.attr("href"));
                System.out.println("description: " + description.text());
                System.out.println("publishTime: " + publishTime.text());
                System.out.println("---------------------------------------------------------------------------------------------");
            }

            // 已访问
            visited.add(url.toString());

            // 下一页
            Element nextEl = doc.selectFirst("#pagination > div.paginationMain > a.next.cnrfont");
            if (nextEl != null) {
                String nextUrl = nextEl.attr("href");
                if (nextUrl != null && !nextUrl.startsWith("javascript")) {
                    todo.offer(new URL(url, nextUrl).toString());
                }
            }
        }

        System.out.println("todo:");
        System.out.println(todo);
        System.out.println("visited:");
        System.out.println(visited);
    }

    public static WebClient getWebClient() {
        if (webClient == null) {
            webClient = new WebClient(BrowserVersion.CHROME);//新建一个模拟谷歌Chrome浏览器的浏览器客户端对象
            webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
            webClient.getOptions().setActiveXNative(false);
            webClient.getOptions().setCssEnabled(false);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
            webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX
        }
        return webClient;
    }

}
