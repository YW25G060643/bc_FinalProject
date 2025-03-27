package com.bootcamp.bc.bc_yahoo_finance.infra.web;

import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

public class UrlManager {
  private Scheme scheme;
  private String domain;
  private String version;
  private String endpoint;
  private MultiValueMap<String, String> params;

  private UrlManager(Builder builder) {
    this.scheme = builder.scheme == null ? Scheme.HTTPS : builder.scheme;
    this.domain = builder.domain;
    this.version = builder.version;
    this.endpoint = builder.endpoint;
    this.params = builder.params;
  }

  public static UrlManager.Builder builder() {
    return new Builder();
  }

  public String toUriString() {
    return UriComponentsBuilder.newInstance() //
        .scheme(this.scheme.name().toLowerCase()) //
        .host(this.domain) //
        .path(this.version) //
        .path(this.endpoint) //
        .queryParams(this.params) //
        .toUriString();
  }

  public static class Builder {
    private Scheme scheme;
    private String domain;
    private String version;
    private String endpoint;
    private MultiValueMap<String, String> params;

    public Builder scheme(Scheme scheme) {
      this.scheme = scheme;
      return this;
    }

    public Builder domain(String domain) {
      this.domain = domain;
      return this;
    }

    public Builder version(String version) {
      this.version = version;
      return this;
    }

    public Builder endpoint(String endpoint) {
      this.endpoint = endpoint;
      return this;
    }

    public Builder params(MultiValueMap<String, String> params) {
      this.params = params;
      return this;
    }

    public UrlManager build() {
      return new UrlManager(this);
    }
  }
}
