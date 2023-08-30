package com.kafein.intern.postinger_payment_service.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "WITHDRAWAL_TABLE")
public class Withdrawal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long walletId;
    private double amount;
    public Withdrawal(Long walletId, double amount) {
        super();
        this.walletId = walletId;
        this.amount = amount;
    }

    public Withdrawal() {
    }
}
