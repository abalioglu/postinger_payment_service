package com.kafein.intern.postinger_payment_service.controller;

import com.kafein.intern.postinger_payment_service.jwt.JwtUtil;
import com.kafein.intern.postinger_payment_service.model.Donation;
import com.kafein.intern.postinger_payment_service.service.DonationService;
import com.kafein.intern.postinger_payment_service.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/donation")
public class DonationController {
    private final DonationService donationService;
    private final JwtUtil jwtUtil;
    private final WalletService walletService;

    @PostMapping("/make-donation")
    ResponseEntity<Donation> makeDonation(@RequestParam(name = "postId")String postId,@RequestParam(name = "amount") double amount, HttpServletRequest request){
        String jwt = jwtUtil.getToken(request);
        Long donorUserId = jwtUtil.extractIdClaim(jwt);
        Long donorWalletId = walletService.getWalletByUserId(donorUserId).getId();
        return ResponseEntity.ok(donationService.makeDonation(donorWalletId,postId,amount));
    }

    @GetMapping("/get-donations")
    ResponseEntity<List<Donation>> getUserDonations(HttpServletRequest request){
        String jwt = jwtUtil.getToken(request);
        Long userId = jwtUtil.extractIdClaim(jwt);
        Long walletId = walletService.getWalletByUserId(userId).getId();
        return new ResponseEntity<>(donationService.getUserDonations(walletId), HttpStatus.OK);
    }
}
