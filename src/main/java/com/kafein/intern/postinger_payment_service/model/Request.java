package com.kafein.intern.postinger_payment_service.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long walletId;
    private double amount;
    private String type;

    public Request(Long walletId, double amount, String type) {
        super();
        this.walletId = walletId;
        this.amount = amount;
        this.type = type;
    }

    public Request() {
    }
}
