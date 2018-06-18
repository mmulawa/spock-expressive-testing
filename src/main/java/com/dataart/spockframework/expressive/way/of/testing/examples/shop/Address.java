package com.dataart.spockframework.expressive.way.of.testing.examples.shop;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    private String country;
    private String province;
    private String city;
    private String postalCode;
    private String street;
    private String streetNumber;
    private String homeNumber;
}
