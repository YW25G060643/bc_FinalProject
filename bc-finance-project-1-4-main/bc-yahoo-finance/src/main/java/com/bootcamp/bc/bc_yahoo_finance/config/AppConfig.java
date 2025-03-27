package com.bootcamp.bc.bc_yahoo_finance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.client.YahooFinanceClient;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AppConfig {
  @Bean
  YahooFinanceClient yahooFinanceClient() {
    return new YahooFinanceClient();
  }

  @Bean
  ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(factory);
    redisTemplate.setKeySerializer(RedisSerializer.string());
    redisTemplate.setValueSerializer(RedisSerializer.json());
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }
}
