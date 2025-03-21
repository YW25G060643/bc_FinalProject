package com.example.bc_xfin_service.fetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.bc_xfin_service.config.WebClientConfig;

// src/main/java/com/example/bcxfin/service/util/CrumbFetcher.java
@Component
public class CrumbFetcher {
    @Autowired private WebClientConfig webClient;

    public String getCrumb() {
        return webClient.get()
            .uri("https://query1.finance.yahoo.com/v1/test/getcrumb")
            .retrieve()
            .bodyToMono(String.class)
            .block(); // 同步获取
    }
}