package com.org.rjankowski.ms.coupons;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// @FeignClient(value = "CouponsApiClient", url = "http://localhost:8081/customers")
@FeignClient(value = "Customers", path = "customers")
public interface CouponsApiClient {
    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    ResponseEntity<Customer> getCustomerById(@PathVariable Long id);
}
