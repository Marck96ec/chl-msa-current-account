package com.challenge.account.repository;

import com.challenge.account.domain.db.Account;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account, Long> {

    @Query("SELECT * FROM chl_account WHERE person_id = :personId")
    Flux<Account> findByPersonId(Long personId);
}
