package com.challenge.account.service.mapper;


import com.challenge.account.domain.db.Account;
import com.challenge.account.service.dto.CustomerPersonResponse;
import com.challenge.customer.server.models.AccountPersonRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(source = "customerPersonResponse.person_id", target = "person_id")
    @Mapping(source = "accountPersonRequest.account.accountType", target = "account_type")
    @Mapping(constant="0", target = "initial_balance")
    @Mapping(constant="true", target = "status")
    Account toAccountCreate(AccountPersonRequest accountPersonRequest, CustomerPersonResponse customerPersonResponse);


    @Mapping(source = "account.account_number", target = "accountNumber")
    @Mapping(source = "account.account_type", target = "accountType")
    @Mapping(source = "account.initial_balance", target = "initialBalance")
    @Mapping(source = "account.status", target = "status")
    com.challenge.customer.server.models.Account toAccount(Account account);

}