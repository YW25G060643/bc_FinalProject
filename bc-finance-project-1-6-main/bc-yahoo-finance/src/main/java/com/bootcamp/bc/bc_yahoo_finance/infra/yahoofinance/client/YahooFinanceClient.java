package com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.client;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;
import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.api.QuoteFunction;

public class YahooFinanceClient implements QuoteFunction {
  private static final String USER_AGENT_STRING = "Mozilla/5.0";

  private RestTemplate restTemplate;
  private CrumbManager crumbManager;

  public YahooFinanceClient() {
    List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
    interceptors.add(new UserAgentInterceptor(USER_AGENT_STRING));

    // Set Connection and Read Timeout.
    this.restTemplate = new RestTemplateBuilder() //
        .setConnectTimeout(Duration.ofSeconds(5)) //
        .setReadTimeout(Duration.ofSeconds(5)) //
        .build();

    // for user-agent
    this.restTemplate.setInterceptors(interceptors);
    this.crumbManager = new CrumbManager(this.restTemplate);
  }

  @Override
  public RestTemplate getRestTemplate() {
    return this.restTemplate;
  }

  @Override
  public String getCrumbKey() {
    return this.crumbManager.getCrumb();
  }

  private static class UserAgentInterceptor
      implements ClientHttpRequestInterceptor {
    private final String userAgent;

    public UserAgentInterceptor(String userAgent) {
      this.userAgent = userAgent;
    }

    @Override
    public @NonNull ClientHttpResponse intercept(@NonNull HttpRequest request,
        @NonNull byte[] body, @NonNull ClientHttpRequestExecution execution)
        throws IOException {
      request.getHeaders().set("User-Agent", userAgent);
      return execution.execute(request, body);
    }
  }
}
