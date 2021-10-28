package com.org.rjankowski.ms.coupons;

import lombok.Getter;

@Getter
public class CouponsUseRequest {
    private Long customerId;
    private String barcode;
}
