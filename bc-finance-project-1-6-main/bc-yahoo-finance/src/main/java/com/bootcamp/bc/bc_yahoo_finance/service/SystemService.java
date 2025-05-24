package com.bootcamp.bc.bc_yahoo_finance.service;

import java.time.LocalDate;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface SystemService {
  LocalDate getSysDate(String symbol) throws JsonProcessingException; 
}
