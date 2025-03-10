package com.yahoofm.pj_yahoofm.service;

import java.io.IOException;
import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CrumbService {
    private final HttpClient httpClient;
    private final CookieManager cookieManager;

    public CrumbService(@Qualifier("yahooHttpClient") HttpClient httpClient) {
        this.httpClient = httpClient;
        this.cookieManager = (CookieManager) httpClient.cookieHandler()
                .orElseThrow(() -> new IllegalStateException("Missing CookieManager"));
    }

    public String getFreshCrumb() throws IOException, InterruptedException {
        cookieManager.getCookieStore().removeAll();

        HttpRequest initRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://finance.yahoo.com"))
                .headers(
                    "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36...",
                    "Accept-Language", "en-US,en;q=0.9",
                    "Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
                    "Referer", "https://www.google.com/"
                )
                .GET()
                .build();
        
        HttpResponse<Void> initResponse = httpClient.send(initRequest, HttpResponse.BodyHandlers.discarding());
        System.out.println("Init Cookies: " + cookieManager.getCookieStore().getCookies());

        Thread.sleep(1500 + new Random().nextInt(1500));

        HttpRequest crumbRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://query1.finance.yahoo.com/v1/test/getcrumb"))
                .headers(
                    "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36...",
                    "Origin", "https://finance.yahoo.com",
                    "Referer", "https://finance.yahoo.com/"
                )
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(crumbRequest, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200 || response.body().length() > 20) { 
            throw new IOException("Crumb 获取失败: " + response.body());
        }
        
        return response.body();
    }
}