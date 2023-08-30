package com.kafein.intern.postinger_payment_service.service;

import com.kafein.intern.postinger_payment_service.model.Donation;
import com.kafein.intern.postinger_payment_service.repository.DonationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DonationService {
    private final DonationRepository donationRepository;
    private final WalletService walletService;
    private final PostServiceClient postServiceClient;

    public Long getUserIdByPostId(String postId){
        return postServiceClient.getUserIdByPostId(postId);
    }

    public List<Donation> getUserDonations(Long walletId) {
        return donationRepository.findByWalletId(walletId);
    }
    public Donation makeDonation(Long donorWalletId, String postId, double amount) {
        Long donorUserId = walletService.getUserIdByWalletId(donorWalletId);
        Long recipientUserId = getUserIdByPostId(postId);

        walletService.deductFundsFromWallet(donorUserId, amount);
        walletService.addFundsToWallet(recipientUserId, amount);

        Donation donation = new Donation();
        donation.setWalletId(donorWalletId);
        donation.setPostId(postId);
        donation.setAmount(amount);
        return donationRepository.save(donation);
    }
}