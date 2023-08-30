package com.kafein.intern.postinger_payment_service.model;

import lombok.Data;

import java.beans.ConstructorProperties;
import java.io.Serializable;

@Data
public class PaymentResponse implements Serializable {
    private Long walletId;
    private String message;
    @ConstructorProperties({"walletId","message"})
    public PaymentResponse(Long walletId,String message) {
        super();
        this.walletId = walletId;
        this.message = message;
    }
    public PaymentResponse() {
    }
}
