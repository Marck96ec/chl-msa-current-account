package com.challenge.account.repository;


import com.challenge.account.domain.db.Movement;
import com.challenge.account.domain.db.Report;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.r2dbc.repository.Query;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface MovementRepository extends ReactiveCrudRepository<Movement, Long> {

//    @Query("SELECT date_movement, person_id, m.account_number, account_type, initial_balance, status, value_movement, balance FROM challenge.chl_movement m JOIN challenge.chl_account a ON m.account_number = a.account_number WHERE a.person_id = :identification AND m.date_movement BETWEEN :startDate AND :endDate")
//    Flux<Report> findAllByCustomerIdAndDateBetween(Long identification, LocalDate startDate, LocalDate endDate);


    @Query("SELECT * FROM challenge.chl_movement WHERE account_number = :accountNumber AND date_movement BETWEEN :startDate AND :endDate")
    Flux<Movement> findAllByAccountNumberAndDateBetween(Integer accountNumber, LocalDate startDate, LocalDate endDate);
}
