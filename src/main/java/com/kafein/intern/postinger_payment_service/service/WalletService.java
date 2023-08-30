package com.kafein.intern.postinger_payment_service.service;

import com.kafein.intern.postinger_payment_service.model.Wallet;
import com.kafein.intern.postinger_payment_service.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class WalletService {
    private final WalletRepository walletRepository;


    public Wallet getWalletByUserId(Long userId) {
        return walletRepository.findByUserId(userId);
    }
    public Long getUserIdByWalletId(Long walletId){
        Wallet wallet = walletRepository.findById(walletId).orElse(null);
        assert wallet != null;
        return wallet.getUserId();
    }
    public void deleteByWalletId(Long walletId){
        walletRepository.deleteById(walletId);
    }
    public Wallet findByUserId(Long userId){
        return walletRepository.findByUserId(userId);
    }
    public Wallet createWallet(Long userId) {
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setBalance(0.0);
        return walletRepository.save(wallet);
    }
    public void addFundsToWallet(Long userId, double amount) {
        Wallet wallet = getWalletByUserId(userId);
        wallet.setBalance(wallet.getBalance() + amount);
        walletRepository.save(wallet);
    }
    public void deductFundsFromWallet(Long userId, double amount) {
        Wallet wallet = getWalletByUserId(userId);
        double newBalance = wallet.getBalance() - amount;
        if (newBalance < 0) {
            throw new RuntimeException("Insufficient funds in wallet");
        }
        wallet.setBalance(newBalance);
        walletRepository.save(wallet);
    }
}
