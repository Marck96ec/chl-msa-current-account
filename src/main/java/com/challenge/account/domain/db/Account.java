package com.challenge.account.domain.db;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("chl_account")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account {

    @Id
    private Long account_number;
    private String account_type;
    private Long person_id;
    private Double initial_balance;
    private Boolean status;
}
