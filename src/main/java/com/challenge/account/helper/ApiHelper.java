package com.challenge.account.helper;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ApiHelper {

  WebClient client;

  public <T> Mono<T> doPost(String url, HttpHeaders headers, Object request, Class<T> typeReference) {
    return client.post()
        .uri(url)
        .headers(httpHeaders -> MDC.getCopyOfContextMap().forEach(headers::add))
        .headers(httpHeaders -> httpHeaders.addAll(headers))
        .bodyValue(request)
        .retrieve()
        .bodyToMono(typeReference);
  }
}
