package com.kafein.intern.postinger_payment_service.controller;

import com.kafein.intern.postinger_payment_service.jwt.JwtUtil;
import com.kafein.intern.postinger_payment_service.model.Wallet;
import com.kafein.intern.postinger_payment_service.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wallet")
public class WalletController {
    private final WalletService walletService;
    private final JwtUtil jwtUtil;

    @PostMapping("/create-wallet")
    ResponseEntity<Wallet> createWallet(HttpServletRequest request) {
        String jwt = jwtUtil.getToken(request);
        Long userId = jwtUtil.extractIdClaim(jwt);
        if(walletService.findByUserId(userId)==null){
             return ResponseEntity.ok(walletService.createWallet(userId));
        }else throw new RuntimeException("A wallet already exists for this user");
    }
    @GetMapping("/get-wallet")
    ResponseEntity<Wallet> getWallet(HttpServletRequest request) {
        String jwt = jwtUtil.getToken(request);
        Long userId = jwtUtil.extractIdClaim(jwt);
        Wallet wallet = walletService.getWalletByUserId(userId);
        if(wallet != null){
            return ResponseEntity.ok(wallet);
        }else throw new RuntimeException("There is no wallet for this user");
    }
    @DeleteMapping("/delete-wallet")
    ResponseEntity<String> deleteWallet(HttpServletRequest request){
        String jwt = jwtUtil.getToken(request);
        Long userId = jwtUtil.extractIdClaim(jwt);
        Long walletId = walletService.getWalletByUserId(userId).getId();
        walletService.deleteByWalletId(walletId);
        return ResponseEntity.ok("You have deleted wallet");
    }
}
