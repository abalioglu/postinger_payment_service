package com.kafein.intern.postinger_payment_service.repository;

import com.kafein.intern.postinger_payment_service.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    /*
    @Query("SELECT e FROM Request e ORDER BY e.id DESC")
    Request findLast();
    */
    Request findTopByOrderByIdDesc();
    Request findTopByWalletIdOrderByIdDesc(Long walletId);
}
