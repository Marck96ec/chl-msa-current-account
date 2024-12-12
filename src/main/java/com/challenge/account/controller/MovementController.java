package com.challenge.account.controller;

import com.challenge.customer.AccountsApi;
import com.challenge.customer.MovementsApi;
import com.challenge.customer.server.models.Account;
import com.challenge.customer.server.models.AccountPersonRequest;
import com.challenge.customer.server.models.Movement;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static lombok.AccessLevel.PRIVATE;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequestMapping("/api/v1")
public class MovementController implements MovementsApi {

    @Override
    public Mono<ResponseEntity<Movement>> createMovement(Movement movement, ServerWebExchange exchange) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Flux<Movement>>> getAllMovements(ServerWebExchange exchange) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Movement>> getMovementById(Integer id, ServerWebExchange exchange) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Movement>> updateMovement(Integer id, Movement movement, ServerWebExchange exchange) {
        return null;
    }
}
