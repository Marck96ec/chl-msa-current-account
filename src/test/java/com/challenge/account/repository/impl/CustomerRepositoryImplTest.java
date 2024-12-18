package com.challenge.account.repository.impl;


import com.challenge.account.configuration.ApplicationPropertiesConfiguration;
import com.challenge.account.helper.WebClientHelper;
import com.challenge.account.repository.CustomerRepository;
import com.challenge.account.service.dto.CustomerPerson;
import com.challenge.account.service.dto.CustomerPersonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CustomerRepositoryImpl.class},
        initializers = ConfigDataApplicationContextInitializer.class)
@EnableConfigurationProperties(value = ApplicationPropertiesConfiguration.class)
class CustomerRepositoryImplTest {

    @Autowired
    private CustomerRepository customerRepository;

    @MockBean
    private WebClientHelper webClientHelper;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    @SuppressWarnings("rawtypes")
    private WebClient.RequestHeadersSpec requestHeadersMock;

    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @Mock
    private WebClient.ResponseSpec responseMock;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(webClientHelper.builder()).thenReturn(webClientHelper);
        when(webClientHelper.header(any())).thenReturn(webClientHelper);
        when(webClientHelper.basePath(anyString())).thenReturn(webClientHelper);
        when(webClientHelper.build()).thenReturn(webClient);
    }

    @Test
    void getCustomerByIdentification_ReturnsCustomerPersonResponse() {
        CustomerPersonResponse expectedResponse = new CustomerPersonResponse();
        expectedResponse.setIdentification("123456789");
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri((Function<UriBuilder, URI>) any()))
                .thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any()))
                .thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.onStatus(any(), any())).thenReturn(responseMock);
        when(responseMock.bodyToMono(CustomerPersonResponse.class))
                .thenReturn(Mono.just(expectedResponse));

        StepVerifier.create(customerRepository
                        .PostCustomerCreate(CustomerPerson.builder()
                                .password("password")
                                .status(true)
                                .name("name")
                                .gender("masculino")
                                .age("30")
                                .identification("123456789")
                                .address("address")
                                .phone("123456789")
                                .build(), null)).expectNext(expectedResponse)
                .verifyComplete();
    }




}