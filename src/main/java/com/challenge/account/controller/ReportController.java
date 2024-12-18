package com.challenge.account.controller;

import com.challenge.account.service.MovementService;
import com.challenge.account.service.ReportService;
import com.challenge.customer.ReportsApi;
import com.challenge.customer.server.models.AccountStatement;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequestMapping("/api/v1")
public class ReportController implements ReportsApi {

    ReportService reportService;

    @Override
    public Mono<ResponseEntity<Flux<AccountStatement>>> getAccountStatement(LocalDate startDate, LocalDate endDate, Integer clientId, ServerWebExchange exchange) {
        return reportService.getAccountStatement(startDate, endDate, clientId)
                .map(ResponseEntity::ok);
    }
}
