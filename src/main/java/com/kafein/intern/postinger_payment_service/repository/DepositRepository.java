package com.kafein.intern.postinger_payment_service.repository;

import com.kafein.intern.postinger_payment_service.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {
    List<Deposit> findByWalletId(Long walletId);
}
