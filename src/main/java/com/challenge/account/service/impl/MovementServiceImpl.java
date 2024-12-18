package com.challenge.account.service.impl;


import com.challenge.account.repository.AccountRepository;
import com.challenge.account.repository.MovementRepository;
import com.challenge.account.service.MovementService;
import com.challenge.account.service.mapper.MovementMapper;
import com.challenge.customer.server.models.Movement;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MovementServiceImpl implements MovementService {

    MovementRepository movementRepository;
    AccountRepository accountRepository;
    MovementMapper movementMapper;


    @Override
    public Mono<Movement> registerMovement(Movement movement) {
        return accountRepository.findById(Long.valueOf(movement.getAccountNumber()))
                .flatMap(account -> {
                    if (!account.getStatus()) {
                        return Mono.error(new RuntimeException("La cuenta se encuentra desactivada"));
                    }
                    double newBalance = account.getInitial_balance() + movement.getValue();
                    double initialBalance = account.getInitial_balance();
                    if (newBalance < 0) {
                        return Mono.error(new RuntimeException("Saldo no disponible"));
                    }
                    account.setInitial_balance(newBalance);
                    movement.setBalance(initialBalance);
                    return accountRepository.save(account)
                            .then(movementRepository.save(movementMapper.toMovement(movement)))
                            .map(movementMapper::toMovementResponse);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Cuenta no encontrada")));
    }

    @Override
    public Flux<Movement> getAllMovements() {
        return movementRepository.findAll()
                .map(movementMapper::toMovementResponse);
    }

    @Override
    public Mono<Movement> getMovementById(Integer id) {
        return movementRepository.findById(Long.valueOf(id))
                .map(movementMapper::toMovementResponse);
    }


}
