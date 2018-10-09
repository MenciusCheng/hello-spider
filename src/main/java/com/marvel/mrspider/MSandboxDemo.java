package com.marvel.mrspider;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marvel on 18/10/09.
 */
public class MSandboxDemo {

//    private final String loginUrl = "http://oauth.sandbox1.oa.isuwang.com/login.do";
//    private final String mainUrl = "http://m.sandbox1.oa.isuwang.com/";
//    private final String testUrl = "http://m.sandbox1.oa.isuwang.com/crm/crmcustomersnew/companies.do?&start=0&limit=10&type=2&kuaisuSupplierFlag=-1&taosuContractSupplierFlag=-1&virtualized=-1";

    private final String loginUrl = "http://oauth.kuaisuwang.com.cn/login.do";
    private final String mainUrl = "http://oss.kuaisuwang.com.cn/";
    private final String testUrl = "http://oss.kuaisuwang.com.cn/crm/crmcustomersnew/companies.do?&start=0&limit=10&type=2&kuaisuSupplierFlag=-1&taosuContractSupplierFlag=-1&virtualized=-1";

    private CloseableHttpClient client = HttpClientBuilder.create().build();

    public String login() {
        HttpPost httpPost = new HttpPost(loginUrl);
        httpPost.setHeader("User-Agent", "Chrome");

        List<NameValuePair> nvps = new ArrayList<>();
//        nvps.add(new BasicNameValuePair("username", "tangyan"));
//        nvps.add(new BasicNameValuePair("password", "a123456.."));
        nvps.add(new BasicNameValuePair("username", "master"));
        nvps.add(new BasicNameValuePair("password", "#jsb123456"));

        String location = "";
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            CloseableHttpResponse response = client.execute(httpPost);

            Header locationHeader = response.getFirstHeader("Location");
            location = locationHeader.getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    public String downloadHtml(String url) throws IOException {
        String content = "";

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", "Chrome");
        CloseableHttpResponse response = client.execute(httpGet);

        HttpEntity entity = response.getEntity();
        if (entity != null) {
            content = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);
        }

        return content;
    }

    public static void main(String[] args) throws Exception {
        MSandboxDemo m = new MSandboxDemo();
        String location = m.login();
        System.out.println("location: " + location);
        System.out.println(m.downloadHtml(m.mainUrl));
        System.out.println("==================");
        System.out.println(m.downloadHtml(m.testUrl));
    }
}
