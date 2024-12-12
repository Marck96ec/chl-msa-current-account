package com.challenge.account.domain.customer;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {

    private Long person_id;

    private String name;
    private String gender;
    private int age;
    private String identification;
    private String address;
    private String phone;
}
