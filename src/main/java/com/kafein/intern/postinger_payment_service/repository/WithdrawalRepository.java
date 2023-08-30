package com.kafein.intern.postinger_payment_service.repository;

import com.kafein.intern.postinger_payment_service.model.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
    List<Withdrawal> findByWalletId(Long walletId);
}