package com.example.bc_xfin_service.scheduler;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.bc_xfin_service.config.WebClientConfig;
import com.example.bc_xfin_service.entity.TStocksPrice;
import com.example.bc_xfin_service.fetcher.CrumbFetcher;
import com.example.bc_xfin_service.repository.TStockPriceRepository;
import com.example.bc_xfin_service.service.StockService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StockPriceScheduler {
    @Autowired private StockService stockService;
    @Autowired private TStockPriceRepository priceRepo;
    @Autowired private WebClientConfig webClient;
    @Autowired private CrumbFetcher crumbFetcher; // 注入 CrumbFetcher

    @Scheduled(cron = "0 */5 * * * *")
    public void fetchAndStorePrices() {
        List<String> symbols = stockService.getStockList();
        symbols.forEach(symbol -> {
            String crumb = crumbFetcher.getCrumb(); // 通过组件获取 crumb
            String url = "https://query1.finance.yahoo.com/v7/finance/quote?symbols=" 
                + symbol + "&crumb=" + crumb;

            webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(response -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode root = mapper.readTree(response);
                        JsonNode quote = root.path("quoteResponse").path("result").get(0);

                        TStocksPrice priceData = new TStocksPrice();
                        priceData.setSymbol(symbol);
                        priceData.setType("SM");
                        priceData.setApiDatetime(LocalDateTime.now());
                        priceData.setRegularMarketTime(quote.path("regularMarketTime").asLong());
                        
                        // 转换 Unix 时间戳为 LocalDateTime
                        LocalDateTime marketTime = Instant.ofEpochSecond(priceData.getRegularMarketTime())
                                .atZone(ZoneId.of("Asia/Hong_Kong"))
                                .toLocalDateTime();
                        priceData.setMarketTime(marketTime);

                        priceData.setRegularMarketPrice(quote.path("regularMarketPrice").asDouble());
                        priceData.setRegularMarketChangePercent(quote.path("regularMarketChangePercent").asDouble());
                        priceData.setBid(quote.path("bid").asDouble());
                        priceData.setAsk(quote.path("ask").asDouble());

                        priceRepo.save(priceData); // 确保 Repository 正确继承 JpaRepository
                    } catch (Exception e) {
                        log.error("解析失败", e);
                    }
                }, error -> log.error("API 调用失败", error));
        });
    }
}