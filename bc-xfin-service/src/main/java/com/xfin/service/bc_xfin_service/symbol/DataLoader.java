package com.xfin.service.bc_xfin_service.symbol;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private StockSymbolRepository repository;

    @Override
    public void run(String... args) {
        List<String> symbols = Arrays.asList("0388.HK", "0700.HK");
        symbols.forEach(s -> repository.save(new StockSymbol(s)));
    }
}