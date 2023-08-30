package com.kafein.intern.postinger_payment_service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class RequestDTO implements Serializable {
    private Long walletId;
    private double amount;

    public RequestDTO(Long walletId, double amount) {
        super();
        this.walletId = walletId;
        this.amount = amount;
    }

}
