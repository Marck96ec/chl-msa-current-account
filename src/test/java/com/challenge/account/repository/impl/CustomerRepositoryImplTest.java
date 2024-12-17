package com.challenge.account.repository.impl;


import com.challenge.account.configuration.ApplicationPropertiesConfiguration;
import com.challenge.account.helper.WebClientHelper;
import com.challenge.account.service.dto.CustomerPersonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.Map;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CustomerRepositoryImpl.class},
        initializers = ConfigDataApplicationContextInitializer.class)
@EnableConfigurationProperties(value = ApplicationPropertiesConfiguration.class)
class CustomerRepositoryImplTest {

    @Mock
    private WebClientHelper webClientHelper;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    @SuppressWarnings("rawtypes")
    private WebClient.RequestHeadersSpec requestHeadersMock;

    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @Mock
    private WebClient.ResponseSpec responseMock;

    @Autowired
    private CustomerRepositoryImpl customerRepositoryImpl;

    @BeforeEach
    void setUp() {
        when(webClientHelper.builder()).thenReturn(webClientHelper);
        when(webClientHelper.header(any())).thenReturn(webClientHelper);
        when(webClientHelper.basePath(anyString())).thenReturn(webClientHelper);
        when(webClientHelper.build()).thenReturn(webClient);
    }

    @Test
    void getCustomerByIdentification_ReturnsCustomerPersonResponse() {
        CustomerPersonResponse expectedResponse = new CustomerPersonResponse();
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(CustomerPersonResponse.class)).thenReturn(Mono.just(expectedResponse));

        StepVerifier.create(customerRepositoryImpl.getCustomerByIdentification(123, Map.of("Authorization", "Bearer token")))
                .expectNext(expectedResponse)
                .verifyComplete();
    }

    @Test
    void getCustomerByIdentification_ReturnsEmptyWhenNotFound() {
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(CustomerPersonResponse.class)).thenReturn(Mono.empty());

        StepVerifier.create(customerRepositoryImpl.getCustomerByIdentification(123, Map.of("Authorization", "Bearer token")))
                .verifyComplete();
    }

    @Test
    void getCustomerByIdentification_ThrowsErrorOnClientError() {
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(CustomerPersonResponse.class)).thenReturn(Mono.error(new RuntimeException("Client error")));

        StepVerifier.create(customerRepositoryImpl.getCustomerByIdentification(123, Map.of("Authorization", "Bearer token")))
                .expectError(RuntimeException.class)
                .verify();
    }
}