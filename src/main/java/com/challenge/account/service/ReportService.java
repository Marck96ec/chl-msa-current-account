package com.challenge.account.service;


import com.challenge.customer.server.models.AccountStatement;
import com.challenge.customer.server.models.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface ReportService {
    Mono<Flux<AccountStatement>> getAccountStatement(LocalDate startDate, LocalDate endDate, Integer clientId);

}
