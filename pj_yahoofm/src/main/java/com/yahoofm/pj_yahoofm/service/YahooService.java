package com.yahoofm.pj_yahoofm.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yahoofm.pj_yahoofm.model.dto.QuoteDTO;

@Service
public class YahooService {
    private CrumbService crumbService;
    private HttpClient httpClient;
    private ObjectMapper objectMapper;

    public YahooService(
        CrumbService crumbService,
        @Qualifier("yahooHttpClient") HttpClient httpClient,
        ObjectMapper objectMapper) {
          this.crumbService = crumbService;
          this.httpClient = httpClient;
          this.objectMapper = objectMapper;
        }
    
    public List<QuoteDTO> getQuotes(List<String> symbols) throws Exception{
        String crumb = crumbService.getFreshCrumb();
        String url = String.format("https://query1.finance.yahoo.com/v7/finance/quote?symbols=%s&crumb=%s"
            , String.join(",", symbols), crumb);
        
        String json = sendRequest(url);
        return parseQuotes(json);
    }
    
    private String sendRequest(String url) throws Exception{
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("User-Agent", "YahooFinanceManager/1.0")
            .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    private List<QuoteDTO> parseQuotes(String json) throws Exception{
        JsonNode root = objectMapper.readTree(json);
        return objectMapper.readValue(
          root.path("quoteResponse").path("result").toString(),
          new TypeReference<List<QuoteDTO>>() {}
          );
            
    }
}
