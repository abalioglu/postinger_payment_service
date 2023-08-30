package com.kafein.intern.postinger_payment_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafein.intern.postinger_payment_service.jwt.JwtUtil;
import com.kafein.intern.postinger_payment_service.model.Deposit;
import com.kafein.intern.postinger_payment_service.model.Request;
import com.kafein.intern.postinger_payment_service.service.DepositService;
import com.kafein.intern.postinger_payment_service.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/deposit")
public class DepositController {
    private final DepositService depositService;
    private final JwtUtil jwtUtil;
    private final WalletService walletService;

    @PostMapping("/request-deposit")
    ResponseEntity<?> requestWithdrawal(@RequestParam(name="amount") double amount, HttpServletRequest request) throws JsonProcessingException {
        String jwt = jwtUtil.getToken(request);
        Long userId = jwtUtil.extractIdClaim(jwt);
        //Long userId = 1L;
        Long walletId = walletService.getWalletByUserId(userId).getId();
        Deposit deposit = depositService.makeDeposit(walletId,amount);
        Request request_ =  new Request(walletId,amount,"deposit request");
        depositService.saveRequest(request_);
        return new ResponseEntity<>(deposit, HttpStatus.OK);
    }
    @GetMapping("/get-deposits")
    ResponseEntity<List<Deposit>> getUserDeposits(HttpServletRequest request){
        String jwt = jwtUtil.getToken(request);
        Long userId = jwtUtil.extractIdClaim(jwt);
        Long walletId = walletService.getWalletByUserId(userId).getId();
        return new ResponseEntity<>(depositService.getUserDeposits(walletId), HttpStatus.OK);
    }
}
