package com.kafein.intern.postinger_payment_service.repository;

import com.kafein.intern.postinger_payment_service.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByWalletId(Long walletId);
}
