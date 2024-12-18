package com.challenge.account.repository.impl;

import com.challenge.account.configuration.ApplicationPropertiesConfiguration;
import com.challenge.account.helper.WebClientHelper;
import com.challenge.account.repository.CustomerRepository;
import com.challenge.account.service.dto.CustomerPerson;
import com.challenge.account.service.dto.CustomerPersonResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CustomerRepositoryImpl implements CustomerRepository {

    ApplicationPropertiesConfiguration properties;
    WebClientHelper webClientHelper;


    @Override
    public Mono<CustomerPersonResponse> getCustomerById(Long id, Map<String, String> headers) {
        return webClientHelper.builder()
                .header(headers)
                .basePath(properties.getServices().getCustomer().getBaseUrl())
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(properties.getServices().getCustomer().getGetIdentificationPath().concat("/").concat(String.valueOf(id))).build()
                )
                .retrieve()
                .bodyToMono(CustomerPersonResponse.class);
    }

    @Override
    public Mono<CustomerPersonResponse> getCustomerByIdentification(Integer identification, Map<String, String> headers) {

        return webClientHelper.builder()
                .header(headers)
                .basePath(properties.getServices().getCustomer().getBaseUrl())
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(properties.getServices().getCustomer().getGetIdentificationPath().concat("/identification/").concat(String.valueOf(identification))).build()
                )
                .retrieve()
                .bodyToMono(CustomerPersonResponse.class);
    }

    @Override
    public Mono<CustomerPersonResponse> PostCustomerCreate(CustomerPerson customerPerson, Map<String, String> headers) {

        return webClientHelper.builder()
                .header(headers)
                .basePath(properties.getServices().getCustomer().getBaseUrl())
                .build()
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(properties.getServices().getCustomer().getGetIdentificationPath()).build()
                )
                .bodyValue(customerPerson)
                .retrieve()
                .onStatus(status -> status.isError(), clientResponse -> {
                    System.err.println("Error: " + clientResponse.statusCode());
                    return Mono.error(new RuntimeException("Error en la solicitud POST"));
                })
                .bodyToMono(CustomerPersonResponse.class);
    }
}
