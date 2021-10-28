package com.org.rjankowski.ms.registration;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RegistrationRequest {
    List<Address> addresses;
    private String firstName;
    private String lastName;
}
