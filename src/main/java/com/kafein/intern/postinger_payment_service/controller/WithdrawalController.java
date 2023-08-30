package com.kafein.intern.postinger_payment_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafein.intern.postinger_payment_service.jwt.JwtUtil;
import com.kafein.intern.postinger_payment_service.model.Request;
import com.kafein.intern.postinger_payment_service.model.Withdrawal;
import com.kafein.intern.postinger_payment_service.service.WalletService;
import com.kafein.intern.postinger_payment_service.service.WithdrawalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/withdrawal")
public class WithdrawalController {
    private final WithdrawalService withdrawalService;
    private final JwtUtil jwtUtil;
    private final WalletService walletService;


    @PostMapping("/request-withdrawal")
    ResponseEntity<?> requestWithdrawal(@RequestParam(name="amount") double amount,HttpServletRequest request) throws JsonProcessingException {
        String jwt = jwtUtil.getToken(request);
        Long userId = jwtUtil.extractIdClaim(jwt);
        //Long userId = 1L;
        Long walletId = walletService.getWalletByUserId(userId).getId();
        Withdrawal withdrawal = withdrawalService.makeWithdrawal(walletId,amount);
        Request request_ =  new Request(walletId,amount,"withdrawal request");
        withdrawalService.saveRequest(request_);
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }
    @GetMapping("/get-withdrawals")
    ResponseEntity<List<Withdrawal>> getUserWithdrawals(HttpServletRequest request){
        String jwt = jwtUtil.getToken(request);
        Long userId = jwtUtil.extractIdClaim(jwt);
        Long walletId = walletService.getWalletByUserId(userId).getId();
        return new ResponseEntity<>(withdrawalService.getUserWithdrawals(walletId), HttpStatus.OK);
    }
}
