package com.example.bc_xfin_service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.bc_xfin_service.entity.TStock;
import com.example.bc_xfin_service.repository.StockRepository;

@Component
public class StockLoader implements CommandLineRunner {
    @Autowired private StockRepository repo;
    @Override
    public void run(String... args) {
        List.of("0388.HK", "0700.HK", "0005.HK").forEach(symbol -> {
            if (!repo.existsBySymbol(symbol)) {
                repo.save(new TStock(null, symbol));
            }
        });
    }
}