package com.dataart.spockframework.expressive.way.of.testing.examples.shop;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Customer {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Address address;
}
