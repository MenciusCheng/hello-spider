package com.marvel.crawler4j.dian;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by Marvel on 18/10/15.
 */
public class DianCrawler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile("https://www.bbc.com/news/technology-\\d+");
    private static int count = 0;

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return FILTERS.matcher(href).matches();
//        return href.startsWith("");
    }

    @Override
    public void visit(Page page) {
        logger.info("==================================");
        String url = page.getWebURL().getURL();
        logger.info("URL {}", url);

        if (page.getParseData() instanceof HtmlParseData) {
            count++;
            if (count % 300 == 0) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
            }

            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();

            Set<WebURL> links = htmlParseData.getOutgoingUrls();
            logger.info("Title {}",  htmlParseData.getTitle());
            logger.info("Number of outgoing links {}", links.size());
        }
        logger.info("------------------------------------");
    }
}
