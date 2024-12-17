package com.challenge.account.service;


import com.challenge.account.domain.db.Account;

import com.challenge.customer.server.models.AccountPersonRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

    Flux<com.challenge.customer.server.models.Account> getAllAccounts();

    Mono<com.challenge.customer.server.models.Account> createAccount(AccountPersonRequest accountPersonRequest);

    Mono<com.challenge.customer.server.models.Account> getAccountByAccountNumber(Integer accountNumber);

    Mono<com.challenge.customer.server.models.Account> updateAccount(Integer accountNumber, com.challenge.customer.server.models.Account account);

}
