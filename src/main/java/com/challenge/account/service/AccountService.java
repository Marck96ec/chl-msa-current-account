package com.challenge.account.service;


import com.challenge.customer.server.models.Account;
import com.challenge.customer.server.models.AccountPersonRequest;
import com.challenge.customer.server.models.UpdateAccountStatusRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

    Flux<com.challenge.customer.server.models.Account> getAllAccounts();

    Mono<com.challenge.customer.server.models.Account> createAccount(AccountPersonRequest accountPersonRequest);

    Mono<com.challenge.customer.server.models.Account> getAccountByAccountNumber(Integer accountNumber);

    Mono<com.challenge.customer.server.models.Account> updateAccount(Integer accountNumber, UpdateAccountStatusRequest updateAccountStatusRequest);
    Flux<com.challenge.customer.server.models.Account> getAccountByPersonId(Integer personId);

}
