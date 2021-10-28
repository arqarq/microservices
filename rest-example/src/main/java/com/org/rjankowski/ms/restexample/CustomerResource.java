package com.org.rjankowski.ms.restexample;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "customers")
public class CustomerResource {
    private final CustomerRepository customerRepository;

    CustomerResource(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable long id) {
        try {
            return new ResponseEntity<>(customerRepository.getItemById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<String> addCustomer(@RequestBody(required = true) Customer customer) {
        try {
            customerRepository.createCustomer(customer);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @ApiOperation(value = "Add new customer")
    public ResponseEntity<Void> addCustomerPost(@RequestBody Customer customer) {
        try {
            customerRepository.createCustomer(customer);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> removeCustomer(@PathVariable long id) {
        try {
            customerRepository.removeCustomer(customerRepository.getItemById(id));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @ApiOperation(value = "Get customers with first and/or last name given or all")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "firstName", value = "First name of user", required = false,
                    dataType = "String", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "lastName", value = "Last name of user", required = false,
                    dataType = "String", paramType = "query", defaultValue = ""),
    })
    public ResponseEntity<List<Customer>> getCustomersByFirstAndOrLastName(@PathParam("firstName") String firstName,
                                                                           @PathParam("lastName") String lastName) {
        try {
            if (firstName == null && lastName == null) {
                return new ResponseEntity<>(customerRepository.getList(), HttpStatus.OK);
            }
            return new ResponseEntity<>(customerRepository.getListByFirstNameAndLastName(firstName, lastName), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
