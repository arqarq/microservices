package com.org.rjankowski.ms.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Customer {
    List<Address> addresses;
    private Long id;
    private String firstName;
    private String lastName;
    private Boolean active;

    public Customer(String firstName, String lastName, Boolean active, List<Address> addresses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
        this.addresses = addresses;
    }
}
