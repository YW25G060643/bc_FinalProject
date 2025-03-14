package com.xfin.service.bc_xfin_service.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xfin.service.bc_xfin_service.model.StockSymbol;
import com.xfin.service.bc_xfin_service.repository.StockSymbolRepository;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initDataBase(StockSymbolRepository repo) {
        return args -> {
            repo.saveAll(List.of(
                new StockSymbol("0388.HK"),
                new StockSymbol(null, "0700.HK"),
                new StockSymbol("0005.HK")
            ));
        };
    }
}
