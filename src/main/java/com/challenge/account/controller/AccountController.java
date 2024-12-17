package com.challenge.account.controller;

import com.challenge.account.service.AccountService;
import com.challenge.customer.AccountsApi;
import com.challenge.customer.server.models.Account;
import com.challenge.customer.server.models.AccountPersonRequest;
import com.challenge.customer.server.models.UpdateAccountStatusRequest;
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
        return accountService.getAccountByAccountNumber(accountNumber)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @Override
    public Mono<ResponseEntity<Flux<Account>>> getAllAccounts(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(accountService.getAllAccounts()));
    }

    @Override
    public Mono<ResponseEntity<Account>> updateAccountStatus(Integer accountNumber, UpdateAccountStatusRequest updateAccountStatusRequest, ServerWebExchange exchange) {
        return accountService.updateAccount(accountNumber, updateAccountStatusRequest)
                .map(ResponseEntity::ok);
    }


}
