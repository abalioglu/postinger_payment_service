package com.kafein.intern.postinger_payment_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafein.intern.postinger_payment_service.dto.RequestDTO;
import com.kafein.intern.postinger_payment_service.jwt.JwtUtil;
import com.kafein.intern.postinger_payment_service.model.PaymentResponse;
import com.kafein.intern.postinger_payment_service.model.Request;
import com.kafein.intern.postinger_payment_service.model.Withdrawal;
import com.kafein.intern.postinger_payment_service.repository.RequestRepository;
import com.kafein.intern.postinger_payment_service.repository.WithdrawalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

@RequiredArgsConstructor
@Service
public class WithdrawalService {
    private final WithdrawalRepository withdrawalRepository;
    private final RequestRepository requestRepository;
    private final RabbitTemplate rabbitTemplate;
    private final WalletService walletService;
    public List<Withdrawal> getUserWithdrawals(Long walletId) {
        return withdrawalRepository.findByWalletId(walletId);
    }

    public Withdrawal makeWithdrawal(Long walletId, double amount) throws JsonProcessingException {
        Withdrawal withdrawal = new Withdrawal(walletId,amount);
        RequestDTO request = new RequestDTO(withdrawal.getWalletId(), withdrawal.getAmount());
        ObjectMapper objectMapper = new ObjectMapper();
        String queuePayloadString = objectMapper.writeValueAsString(request);
        rabbitTemplate.convertAndSend("withdrawalRequests", queuePayloadString);
        return withdrawal;
    }

    public void saveWithdrawal(Withdrawal withdrawal){
        withdrawalRepository.save(withdrawal);
    }
    public void saveRequest(Request request){
        requestRepository.save(request);
    }



    @RabbitListener(queues = "paymentResponseWithdrawal")
    public void processWithdrawal(String response) throws JsonProcessingException {
        System.out.println(response);
        //Long userId = 3L;
        ObjectMapper objectMapper = new ObjectMapper();
        PaymentResponse payload = objectMapper.readValue(response, PaymentResponse.class);
        Request req;
        if(Objects.equals(payload.getMessage(), "Withdrawal successful")){
           // request_ = requestRepository.findTopByOrderByIdDesc();
            Long walletId = payload.getWalletId();
            Long userId = walletService.getUserIdByWalletId(walletId);
            req = requestRepository.findTopByWalletIdOrderByIdDesc(walletId);
            System.out.println(req);
            Withdrawal withdrawal = new Withdrawal();
            withdrawal.setWalletId(req.getWalletId());
            withdrawal.setAmount(req.getAmount());
            System.out.println(withdrawal);
            walletService.addFundsToWallet(userId, withdrawal.getAmount());
            saveWithdrawal(withdrawal);
        }
    }

}