package com.challenge.account.service.impl;


import com.challenge.account.repository.AccountRepository;
import com.challenge.account.repository.MovementRepository;
import com.challenge.account.service.MovementService;
import com.challenge.account.service.mapper.CustomerMapper;
import com.challenge.customer.server.models.Movement;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MovementServiceImpl implements MovementService {

    MovementRepository movementRepository;
    AccountRepository accountRepository;
    CustomerMapper customerMapper;


    @Override
    public Mono<Movement> registerMovement(com.challenge.customer.server.models.Movement movement) {
        return accountRepository.findById(Long.valueOf(movement.getAccountNumber()))
                .flatMap(account -> {
                    double newBalance = account.getInitial_balance() + movement.getValue();
                    account.setInitial_balance(newBalance);
                    return accountRepository.save(account)
                            .then(movementRepository.save(customerMapper.toMovement(movement)))
                            .thenReturn(movement);
                });
    }
}
