package com.challenge.account.helper;

import com.challenge.account.configuration.ApplicationPropertiesConfiguration;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;
import reactor.netty.http.client.HttpClient;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static lombok.AccessLevel.PRIVATE;

@Component
@FieldDefaults(level = PRIVATE)
public class WebClientHelper {

  @Autowired
  ApplicationPropertiesConfiguration properties;


  WebClient.Builder builder;

  public WebClientHelper builder() {
    HttpClient httpClient = HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, properties.getConnectTimeout())
        .doOnConnected(connection -> {
          connection.addHandlerLast(new ReadTimeoutHandler(properties.getReadTimeout(), TimeUnit.MILLISECONDS));
          connection.addHandlerLast(new WriteTimeoutHandler(properties.getReadTimeout(), TimeUnit.MILLISECONDS));
        });
    builder = WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient));
    return this;
  }

  public WebClientHelper header(Map<String, String> headers) {
    if (headers == null) {
      return this;
    }
    headers.forEach((key, value) -> this.builder = builder.defaultHeader(key, value));
    return this;
  }

  public WebClientHelper basePath(String basePath) {
    DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(basePath);
    factory.setEncodingMode(EncodingMode.NONE);
    builder.baseUrl(basePath);
    builder.uriBuilderFactory(factory);
    return this;
  }

  public WebClient build() {
    return builder.build();
  }
}
