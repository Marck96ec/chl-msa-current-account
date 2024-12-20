package com.challenge.account.service;


import com.challenge.account.domain.db.Account;

import com.challenge.customer.server.models.AccountPersonRequest;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<com.challenge.customer.server.models.Account> createAccount(AccountPersonRequest accountPersonRequest);

}
