package com.org.rjankowski.ms.registration;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RegistrationResource {
    private final RestTemplate restTemplate;
    private final Environment environment;

    RegistrationResource(RestTemplate restTemplate, Environment environment) {
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createCustomer(@RequestBody RegistrationRequest registrationRequest) {
        ResponseEntity<Customer[]> forEntity = restTemplate.getForEntity("http://Customers/customers", Customer[].class);
        for (Customer customer : forEntity.getBody()) {
            if (customer.getFirstName().equals(registrationRequest.getFirstName()) &&
                    customer.getLastName().equals(registrationRequest.getLastName())) {
                throw new RuntimeException();
            }
        }
        restTemplate.postForEntity("http://Customers/customers", new Customer(registrationRequest.getFirstName(),
                registrationRequest.getLastName(), false, registrationRequest.getAddresses()), ResponseEntity.class);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/active")
    public ResponseEntity<Void> createCustomer(@RequestBody ActivationRequest activationRequest) {
        ResponseEntity<Customer[]> forEntity = restTemplate.getForEntity("http://Customers/customers", Customer[].class);
        for (Customer customer : forEntity.getBody()) {
            if (customer.getFirstName().equals(activationRequest.getFirstName()) &&
                    customer.getLastName().equals(activationRequest.getLastName())) {
                customer.setActive(true);
                restTemplate.postForEntity("http://Customers/customers", customer, ResponseEntity.class);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/close")
    public ResponseEntity<Void> createCustomer(@RequestBody ClosingRequest closingRequest) {
        ResponseEntity<Customer[]> forEntity = restTemplate.getForEntity("http://Customers/customers", Customer[].class);
        for (Customer customer : forEntity.getBody()) {
            if (customer.getFirstName().equals(closingRequest.getFirstName()) &&
                    customer.getLastName().equals(closingRequest.getLastName())) {
                restTemplate.delete("http://Customers/customers" + customer.getId());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "version")
    public String getVersion() {
        return environment.getProperty("custom.api.version") + " / " + environment.getProperty("config.client.version");
    }
}
