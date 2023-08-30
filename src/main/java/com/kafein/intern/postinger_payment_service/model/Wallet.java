package com.kafein.intern.postinger_payment_service.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "WALLET_TABLE")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private double balance;
}