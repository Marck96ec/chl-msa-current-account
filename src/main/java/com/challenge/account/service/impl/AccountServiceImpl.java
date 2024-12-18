package com.challenge.account.service.impl;


import com.challenge.account.repository.AccountRepository;
import com.challenge.account.repository.CustomerRepository;
import com.challenge.account.service.AccountService;
import com.challenge.account.service.dto.CustomerPerson;
import com.challenge.account.service.mapper.AccountMapper;
import com.challenge.customer.server.models.Account;
import com.challenge.customer.server.models.AccountPersonRequest;
import com.challenge.customer.server.models.UpdateAccountStatusRequest;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {

    public static final String EL_CLIENTE_NO_EXISTE_COMPLETE_LA_INFORMACION_NECESARIA = "el cliente no existe complete la informacion necesaria";
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountMapper accountMapper;


    @Override
    public Flux<Account> getAllAccounts() {
        return accountRepository.findAll()
                .map(accountMapper::toAccount);
    }

    @Override
    public Mono<Account> createAccount(AccountPersonRequest accountPersonRequest) {
        Integer identification = Integer.valueOf(accountPersonRequest.getPerson().getIdentification());
        return customerRepository.getCustomerByIdentification(identification, null)
                .flatMap(existingCustomer -> {
                    return accountRepository.save(accountMapper.toAccountCreate(accountPersonRequest, existingCustomer))
                            .map(accountMapper::toAccount);
                })
                .switchIfEmpty(
                        customerRepository.PostCustomerCreate(
                                        CustomerPerson.builder()
                                                .password(getValidatedField(accountPersonRequest.getCustomer().getPassword(), EL_CLIENTE_NO_EXISTE_COMPLETE_LA_INFORMACION_NECESARIA))
                                                .status(Boolean.valueOf(getValidatedField(accountPersonRequest.getCustomer().getStatus().toString(), EL_CLIENTE_NO_EXISTE_COMPLETE_LA_INFORMACION_NECESARIA)))
                                                .name(getValidatedField(accountPersonRequest.getPerson().getName(), EL_CLIENTE_NO_EXISTE_COMPLETE_LA_INFORMACION_NECESARIA))
                                                .gender(getValidatedField(accountPersonRequest.getPerson().getGender(), EL_CLIENTE_NO_EXISTE_COMPLETE_LA_INFORMACION_NECESARIA))
                                                .age(String.valueOf(getValidatedField(accountPersonRequest.getPerson().getAge(), EL_CLIENTE_NO_EXISTE_COMPLETE_LA_INFORMACION_NECESARIA)))
                                                .identification(getValidatedField(accountPersonRequest.getPerson().getIdentification(), EL_CLIENTE_NO_EXISTE_COMPLETE_LA_INFORMACION_NECESARIA))
                                                .address(getValidatedField(accountPersonRequest.getPerson().getAddress(), EL_CLIENTE_NO_EXISTE_COMPLETE_LA_INFORMACION_NECESARIA))
                                                .phone(getValidatedField(accountPersonRequest.getPerson().getPhone(), EL_CLIENTE_NO_EXISTE_COMPLETE_LA_INFORMACION_NECESARIA))
                                                .build(), buildHeaders(new HttpHeaders()))
                                .flatMap(newCustomer ->
                                        accountRepository.save(accountMapper.toAccountCreate(accountPersonRequest, newCustomer))
                                                .map(accountMapper::toAccount)
                                )
                );
    }

    @Override
    public Mono<Account> getAccountByAccountNumber(Integer accountNumber) {
        return accountRepository.findById(Long.valueOf(accountNumber))
                .map(accountMapper::toAccount);
    }


    @Override
    public Mono<Account> updateAccount(Integer accountNumber, UpdateAccountStatusRequest updateAccountStatusRequest) {
        return accountRepository.findById(Long.valueOf(accountNumber))
                .flatMap(existingAccount -> {
                    existingAccount.setStatus(Boolean.valueOf(updateAccountStatusRequest.getStatus()));
                    return accountRepository.save(existingAccount)
                            .map(accountMapper::toAccount);
                });
    }

    @Override
    public Flux<Account> getAccountByPersonId(Integer personId) {
        return accountRepository.findByPersonId(Long.valueOf(personId))
                .map(accountMapper::toAccount);
    }

    private <T> T getValidatedField(T field, String errorMessage) {
        return Optional.ofNullable(field).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage));
    }

    public static Map<String, String> buildHeaders(HttpHeaders headers) {
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("accept", "application/json");
        headersMap.put("Content-Type", "application/json");
        return headersMap;
    }
}
