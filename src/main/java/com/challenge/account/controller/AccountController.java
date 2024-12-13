package com.challenge.account.controller;

import com.challenge.account.service.AccountService;
import com.challenge.customer.AccountsApi;
import com.challenge.customer.server.models.Account;
import com.challenge.customer.server.models.AccountPersonRequest;
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
public class AccountController implements AccountsApi {

    AccountService accountService;

    @Override
    public Mono<ResponseEntity<Account>> createAccount(AccountPersonRequest accountPersonRequest, ServerWebExchange exchange) {
        return accountService.createAccount(accountPersonRequest)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Account>> getAccountByAccountNumber(Integer accountNumber, ServerWebExchange exchange) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Flux<Account>>> getAllAccounts(ServerWebExchange exchange) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Account>> updateAccount(Integer accountNumber, Account account, ServerWebExchange exchange) {
        return null;
    }
}
