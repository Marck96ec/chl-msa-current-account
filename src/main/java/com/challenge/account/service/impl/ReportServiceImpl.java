package com.challenge.account.service.impl;

import com.challenge.account.domain.db.Movement;
import com.challenge.account.repository.CustomerRepository;
import com.challenge.account.repository.MovementRepository;
import com.challenge.account.service.AccountService;
import com.challenge.account.service.ReportService;
import com.challenge.account.service.dto.CustomerPersonResponse;
import com.challenge.customer.server.models.Account;
import com.challenge.customer.server.models.AccountStatement;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ReportServiceImpl implements ReportService {

    AccountService accountService;
    MovementRepository movementRepository;
    CustomerRepository customerRepository;

    @Override
    public Mono<Flux<AccountStatement>> getAccountStatement(LocalDate startDate, LocalDate endDate, Integer identification) {
        return customerRepository.getCustomerByIdentification(identification, null)
                .flatMapMany(customer -> accountService.getAccountByPersonId(Integer.valueOf(customer.getPerson_id()))
                        .map(account -> Tuples.of(customer, account)))
                .flatMap(tuple -> {
                    CustomerPersonResponse customer = tuple.getT1();
                    Account account = tuple.getT2();
                    return movementRepository.findAllByAccountNumberAndDateBetween(account.getAccountNumber(), startDate, endDate)
                            .map(movement -> Tuples.of(customer, account, movement));
                })
                .map(tuple -> {
                    CustomerPersonResponse customer = tuple.getT1();
                    Account account = tuple.getT2();
                    Movement movement = tuple.getT3();
                    AccountStatement statement = new AccountStatement();
                    statement.setDate(String.valueOf(movement.getDate_movement()));
                    statement.setAccountNumber(Integer.valueOf(account.getAccountNumber().toString()));
                    statement.setAccountType(account.getAccountType());
                    statement.setInitialBalance(movement.getBalance());
                    statement.setStatus(Boolean.valueOf(account.getStatus()));
                    statement.setMovement(movement.getValue_movement());
                    statement.setAvailableBalance(movement.getBalance() + movement.getValue_movement());
                    statement.setClient(customer.getName());
                    return statement;
                })
                .collectList()
                .flatMapMany(Flux::fromIterable)
                .collectList()
                .map(Flux::fromIterable);
    }
}
