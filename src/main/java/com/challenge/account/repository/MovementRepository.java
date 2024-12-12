package com.challenge.account.repository;


import com.challenge.account.domain.db.Movement;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends ReactiveCrudRepository<Movement, Long> {
}
