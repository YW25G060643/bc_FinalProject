package com.yahoofm.pj_yahoofm.service;

import java.io.IOException;
import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CrumbService {
    private HttpClient httpClient;
    private CookieManager cookieManager;

    public CrumbService(@Qualifier("yahooHttpClient") HttpClient httpClient) {
        this.httpClient = httpClient;
        this.cookieManager = (CookieManager) httpClient.cookieHandler().orElseThrow();
    }

    public String getFreshCrumb() throws IOException, InterruptedException {
        cookieManager.getCookieStore().removeAll();

        HttpRequest crumbRequest = HttpRequest.newBuilder()
            .uri(URI.create("https://fc.yahoo.com"))
            .header("User-Agent", "Mozilla/5.0 (Java)")
            .build();

        return httpClient.send(crumbRequest, HttpResponse.BodyHandlers.ofString()).body();
    }
}
