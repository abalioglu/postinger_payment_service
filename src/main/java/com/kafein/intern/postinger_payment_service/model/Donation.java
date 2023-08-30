package com.kafein.intern.postinger_payment_service.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "DONATION_TABLE")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long walletId;
    private String postId;
    private double amount;
}
