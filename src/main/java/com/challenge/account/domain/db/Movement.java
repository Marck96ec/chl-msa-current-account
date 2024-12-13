package com.challenge.account.domain.db;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table("chl_movement")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Movement {

    @Id
    private Long id;
    private LocalDate date_movement;
    private Long account_number;
    private String movement_type;
    private Double value_movement;
    private Double balance;
}
