package com.challenge.account.service;


import com.challenge.customer.server.models.Movement;
import reactor.core.publisher.Mono;

public interface MovementService {
    Mono<Movement> registerMovement(com.challenge.customer.server.models.Movement movement);
}
