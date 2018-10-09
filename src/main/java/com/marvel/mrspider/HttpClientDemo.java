package com.marvel.mrspider;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;

/**
 * Created by Marvel on 18/10/09.
 */
public class HttpClientDemo {

    public static void main(String[] args) throws Exception {
        System.out.println(downloadHtml("https://www.baidu.com/s?wd=cc"));
//        System.out.println(downloadHtml("http://www.isuwang.com"));
//        System.out.println(downloadHtml("http://www.qq.com"));
    }

    public static String downloadHtml(String url) throws IOException {
        String content = "";

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", "Chrome");
        CloseableHttpResponse response = client.execute(httpGet);

        // 获取头部信息
//        Header[] allHeaders = response.getAllHeaders();
//        for (Header h : allHeaders) {
//            System.out.println(h.toString());
//        }

        HttpEntity entity = response.getEntity();
        if (entity != null) {
            content = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);
        }

        client.close();
        return content;
    }
}
