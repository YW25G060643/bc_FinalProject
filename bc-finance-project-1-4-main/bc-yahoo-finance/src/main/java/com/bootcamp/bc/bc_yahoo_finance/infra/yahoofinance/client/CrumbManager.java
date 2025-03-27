package com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.util.Yahoo;

public class CrumbManager {
  private RestTemplate restTemplate;
  private CookieManager cookieManager;

  public CrumbManager(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    this.cookieManager = new CookieManager(restTemplate);
  }

  public String getCrumb() {
    try {
      String cookie = this.cookieManager.getCookie();
      // System.out.println("cookie=" + cookie);
      HttpHeaders headers = new HttpHeaders();
      headers.add("Cookie", cookie);
      HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

      String crumbUrl = UriComponentsBuilder.newInstance()
          .scheme("https") //
          .host(Yahoo.DOMAIN) //
          .path(Yahoo.VERSION_CRUMB) //
          .path(Yahoo.ENDPOINT_CRUMB) //
          .toUriString();
      // System.out.println("crumb url=" + crumbUrl);
      return restTemplate
          .exchange(crumbUrl, HttpMethod.GET, entity, String.class) //
          .getBody();
    } catch (RestClientException e) {
      return null;
    }
  }
}
