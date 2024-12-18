package com.challenge.account.domain.db;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Report {

    private LocalDate date_movement;
    private Long person_id;
    private Long account_number;
    private String account_type;
    private Double initial_balance;
    private Boolean status;
    private Double value_movement;
    private Double balance;
}
