package com.challenge.account.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;


@Configuration
@EnableWebFlux
@RequiredArgsConstructor
public class WebFluxConfiguration implements WebFluxConfigurer {

  private final ObjectMapper objectMapper;
  private final ApplicationPropertiesConfiguration propertiesConfiguration;

  /**
   * {@inheritDoc}
   */
  @Override
  public void configureHttpMessageCodecs(ServerCodecConfigurer configure) {
    configure.defaultCodecs().maxInMemorySize((int) propertiesConfiguration.getMaxInMemorySize().toBytes());
    configure.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
    configure.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
  }

  @Bean
  public WebClient webClient() {
    HttpClient httpClient = HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, propertiesConfiguration.getConnectTimeout())
        .doOnConnected(connection -> {
          connection.addHandlerLast(new ReadTimeoutHandler(propertiesConfiguration.getReadTimeout(), TimeUnit.MILLISECONDS));
          connection.addHandlerLast(new WriteTimeoutHandler(propertiesConfiguration.getReadTimeout(), TimeUnit.MILLISECONDS));
        });
    return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient)).build();
  }

}
