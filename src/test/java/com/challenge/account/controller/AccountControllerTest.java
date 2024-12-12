package com.challenge.account.controller;

import com.challenge.account.server.models.CustomerPerson;
import com.challenge.account.server.models.CustomerPersonResponse;
import com.challenge.account.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AccountControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private ServerWebExchange exchange;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer_createsCustomerSuccessfully() {
        CustomerPerson customerPerson = new CustomerPerson();
        CustomerPersonResponse response = new CustomerPersonResponse();

        when(customerService.createCustomer(any(CustomerPerson.class))).thenReturn(Mono.just(response));

        Mono<ResponseEntity<CustomerPersonResponse>> result = accountController.createCustomer(customerPerson, exchange);

        StepVerifier.create(result)
                .assertNext(res -> {
                    assertEquals(ResponseEntity.ok(response), res);
                })
                .verifyComplete();
    }

    @Test
    void deleteCustomer_deletesCustomerSuccessfully() {
        Integer id = 1;

        when(customerService.deleteCustomer(any(Integer.class))).thenReturn(Mono.empty());

        Mono<ResponseEntity<Void>> result = accountController.deleteCustomer(id, exchange);

        StepVerifier.create(result)
                .assertNext(res -> {
                    assertEquals(ResponseEntity.noContent().<Void>build(), res);
                })
                .verifyComplete();
    }

    @Test
    void getCustomerById_returnsCustomerById() {
        Integer id = 1;
        CustomerPersonResponse response = new CustomerPersonResponse();

        when(customerService.getCustomerById(any(Integer.class))).thenReturn(Mono.just(response));

        Mono<ResponseEntity<CustomerPersonResponse>> result = accountController.getCustomerById(id, exchange);

        StepVerifier.create(result)
                .assertNext(res -> {
                    assertEquals(ResponseEntity.ok(response), res);
                })
                .verifyComplete();
    }

    @Test
    void updateCustomer_updatesCustomerSuccessfully() {
        Integer id = 1;
        CustomerPerson customerPerson = new CustomerPerson();
        CustomerPersonResponse response = new CustomerPersonResponse();

        when(customerService.updateCustomer(any(Integer.class), any(CustomerPerson.class))).thenReturn(Mono.just(response));

        Mono<ResponseEntity<CustomerPersonResponse>> result = accountController.updateCustomer(id, customerPerson, exchange);

        StepVerifier.create(result)
                .assertNext(res -> {
                    assertEquals(ResponseEntity.ok(response), res);
                })
                .verifyComplete();
    }

}