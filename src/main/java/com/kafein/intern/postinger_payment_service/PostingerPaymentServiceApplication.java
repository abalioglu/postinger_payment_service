package com.kafein.intern.postinger_payment_service;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@SpringBootApplication
@EnableFeignClients
@EnableRabbit
public class PostingerPaymentServiceApplication  {

	public static void main(String[] args) {
		SpringApplication.run(PostingerPaymentServiceApplication.class, args);
	}
	@Bean
	Queue Queue() {
		return new Queue("paymentResponseWithdrawal");
	}

	@Bean
	Queue Queue_() {
		return new Queue("paymentResponseDeposit");
	}
	/*
	@Bean
	public MappingJackson2MessageConverter jackson2Converter() {
		return new MappingJackson2MessageConverter();
	}

	@Bean
	public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(jackson2Converter());
		return factory;
	}

	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar listenerRegistrar) {
		listenerRegistrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
	}
*/
}
