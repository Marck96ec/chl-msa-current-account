package com.challenge.account.repository;

import com.challenge.account.service.dto.CustomerPerson;
import com.challenge.account.service.dto.CustomerPersonResponse;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface CustomerRepository {
    Mono<CustomerPersonResponse> getCustomerById(Long id, Map<String, String> headers);
    Mono<CustomerPersonResponse> getCustomerByIdentification(Integer identification, Map<String, String> headers);
    Mono<CustomerPersonResponse> PostCustomerCreate(CustomerPerson customerPerson, Map<String, String> headers);
}
