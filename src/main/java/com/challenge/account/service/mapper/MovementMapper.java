package com.challenge.account.service.mapper;


import com.challenge.account.domain.db.Account;
import com.challenge.account.service.dto.CustomerPersonResponse;
import com.challenge.customer.server.models.AccountPersonRequest;
import com.challenge.customer.server.models.Movement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import reactor.core.publisher.Mono;

@Mapper(componentModel = "spring")
public interface MovementMapper {

    @Mapping(expression = "java(java.time.LocalDate.now())", target = "date_movement")
    @Mapping(source = "movement.accountNumber", target = "account_number")
    @Mapping(source = "movement.movementType", target = "movement_type")
    @Mapping(source = "movement.value", target = "value_movement")
    @Mapping(source = "movement.balance", target = "balance")
    com.challenge.account.domain.db.Movement toMovement(com.challenge.customer.server.models.Movement movement);


    @Mapping(source = "movement.date_movement", target = "date")
    @Mapping(source = "movement.movement_type", target = "movementType")
    @Mapping(source = "movement.value_movement", target = "value")
    @Mapping(source = "movement.balance", target = "balance")
    @Mapping(source = "movement.account_number", target = "accountNumber")
    Movement toMovementResponse(com.challenge.account.domain.db.Movement movement);
}
