package com.marvel.mrspider.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

/**
 * Created by Marvel on 18/10/09.
 */
public class RetrivePage {

    public static void main(String[] args) throws Exception {
        System.out.println(downloadPage("http://www.163.com"));

        // 根据域名获取ip
//        InetAddress[] addresses = InetAddress.getAllByName("www.baidu.com");
//        System.out.println(addresses);
    }

    public static String downloadPage(String path) throws IOException {
        URL url = new URL(path);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        url.openStream(), "gbk"));

        StringBuilder pageBuffer = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            pageBuffer.append(line);
            pageBuffer.append("\n");
        }

        return pageBuffer.toString();
    }
}
