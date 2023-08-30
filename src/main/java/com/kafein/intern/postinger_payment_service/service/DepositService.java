package com.kafein.intern.postinger_payment_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafein.intern.postinger_payment_service.dto.RequestDTO;
import com.kafein.intern.postinger_payment_service.model.Deposit;
import com.kafein.intern.postinger_payment_service.model.PaymentResponse;
import com.kafein.intern.postinger_payment_service.model.Request;
import com.kafein.intern.postinger_payment_service.repository.DepositRepository;
import com.kafein.intern.postinger_payment_service.repository.RequestRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class DepositService {
    private final DepositRepository depositRepository;
    private final RequestRepository requestRepository;
    private final RabbitTemplate rabbitTemplate;
    private final WalletService walletService;

    public List<Deposit> getUserDeposits(Long walletId) {
        return depositRepository.findByWalletId(walletId);
    }
    public void saveDeposit(Deposit deposit){
        depositRepository.save(deposit);
    }
    public void saveRequest(Request request){
        requestRepository.save(request);
    }
    public Deposit makeDeposit(Long walletId, double amount) throws JsonProcessingException {
        Deposit deposit = new Deposit(walletId,amount);
        RequestDTO requestDTO = new RequestDTO(walletId,amount);
        ObjectMapper objectMapper = new ObjectMapper();
        String queuePayloadString = objectMapper.writeValueAsString(requestDTO);
        System.out.println(queuePayloadString +" deposit request");
        rabbitTemplate.convertAndSend("depositRequests", queuePayloadString);
        return deposit;
    }

    @RabbitListener(queues = "paymentResponseDeposit")
    public void processDeposit(String response) throws JsonProcessingException {
        //Long userId = 1L;
        ObjectMapper objectMapper = new ObjectMapper();
        PaymentResponse payload = objectMapper.readValue(response, PaymentResponse.class);
        Request request_;
        if(Objects.equals(payload.getMessage(), "Deposit successful")){
            Long walletId = payload.getWalletId();
            Long userId = walletService.getUserIdByWalletId(walletId);
            request_ = requestRepository.findTopByWalletIdOrderByIdDesc(walletService.getWalletByUserId(userId).getId());
            Deposit deposit = new Deposit(request_.getWalletId(), request_.getAmount());
            walletService.deductFundsFromWallet(userId, deposit.getAmount());
            saveDeposit(deposit);
        }
    }


}