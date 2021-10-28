package com.org.rjankowski.ms.customers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController("/")
public class CustomerResource {
    private final CustomerRepository customerRepository;

    CustomerResource(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers(@RequestParam(value = "firstName", required = false) String firstName,
                                                       @RequestParam(value = "lastName", required = false) String lastName) {
        if (firstName != null && lastName != null) {
            return new ResponseEntity<>(customerRepository.findAllByFirstNameAndLastName(firstName, lastName), HttpStatus.OK);
        }
        if (firstName != null) {
            return new ResponseEntity<>(customerRepository.findAllByFirstName(firstName), HttpStatus.OK);
        }
        if (lastName != null) {
            return new ResponseEntity<>(customerRepository.findAllByLastName(lastName), HttpStatus.OK);
        }
        List<Customer> all = customerRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("customers/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        Optional<Customer> itemById = customerRepository.findById(id);
        return itemById.map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/customers")
    public ResponseEntity<Void> createCustomer(@RequestBody Customer customer) {
        customerRepository.saveAndFlush(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("customers/{id}/addresses/{idAdr}")
    public ResponseEntity<List<Address>> getCustomerAddress(@PathVariable Long id, @PathVariable Long idAdr) {
        Optional<Customer> itemById = customerRepository.findById(id);
        if (itemById.isPresent()) {
            return new ResponseEntity<>(itemById.get().getAddresses().stream().filter(address -> address.getId().equals(idAdr)).collect(Collectors.toList()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("customers/{id}/addresses")
    public ResponseEntity<List<Address>> getCustomerAddresses(@PathVariable Long id) {
        Optional<Customer> itemById = customerRepository.findById(id);
        if (itemById.isPresent()) {
            return new ResponseEntity<>(itemById.get().getAddresses(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
