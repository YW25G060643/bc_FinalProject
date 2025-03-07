package com.yahoofm.pj_yahoofm.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.yahoofm.pj_yahoofm.model.dto.QuoteDTO;
import com.yahoofm.pj_yahoofm.service.YahooService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class FinanceController {
    private final YahooService yahooService;

    @GetMapping("/quote")
    public String getQuote(
        @RequestParam(defaultValue = "0388.HK") String symbol,
        Model model) {
      try {
        List<QuoteDTO> quotes = yahooService.getQuotes(Collections.singletonList(symbol));
        model.addAttribute("quotes", quotes);
      } catch (Exception e) {
        model.addAttribute("error", e.getMessage());
      }
        return "quote";
    }
    
}
