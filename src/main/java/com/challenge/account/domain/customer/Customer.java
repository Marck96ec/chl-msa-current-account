package com.challenge.account.domain.customer;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {

    private Long customer_id;
    private Long person_id;
    private String password;
    private String status;
}
