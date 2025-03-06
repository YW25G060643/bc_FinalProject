package com.yahoofm.pj_yahoofm.service;

import java.net.http.HttpClient;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
    
    public List<QuoteDTO> gQuotes(List<String> symbols) throws 
}
