package com.org.rjankowski.ms.coupons;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CouponsResource {
    private final CouponsApiClient couponsApiClient;
    private final CouponsRepository couponsRepository;

    CouponsResource(CouponsApiClient couponsApiClient, CouponsRepository couponsRepository) {
        this.couponsApiClient = couponsApiClient;
        this.couponsRepository = couponsRepository;
    }

    @GetMapping
    public List<Coupon> getCoupons() {
        return couponsRepository.findAll();
    }

    @PostMapping(path = "issue")
    public ResponseEntity<Void> issueCoupon(@RequestBody CouponsRequest couponsRequest) {
        ResponseEntity<Customer> customerById = couponsApiClient.getCustomerById(couponsRequest.getCustomerId());
        if (customerById.getBody() == null) {
            throw new RuntimeException();
        }
        couponsRepository.save(new Coupon(null, customerById.getBody().getId(), "random", "ISSUED"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "use")
    public ResponseEntity<Void> issueCoupon(@RequestBody CouponsUseRequest couponsUseRequest) {
        ResponseEntity<Customer> customerById = couponsApiClient.getCustomerById(couponsUseRequest.getCustomerId());
        if (customerById.getBody() == null) {
            throw new RuntimeException();
        }
        Coupon byBarcoded = couponsRepository.findByBarcodeAndCustomerId(couponsUseRequest.getBarcode(), couponsUseRequest.getCustomerId());
        if (byBarcoded == null) {
            throw new RuntimeException();
        }
        byBarcoded.setStatus("USED");
        couponsRepository.save(byBarcoded);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
