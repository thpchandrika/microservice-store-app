package com.chandrika.orderservice.service;

import com.chandrika.orderservice.common.Payment;
import com.chandrika.orderservice.common.TransactionRequest;
import com.chandrika.orderservice.common.TransactionResponse;
import com.chandrika.orderservice.entity.Order;
import com.chandrika.orderservice.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
@RequiredArgsConstructor
@RefreshScope  //loads properties value from config-server
public class OrderService {

    private final OrderRepository repository;
    private final RestTemplate restTemplate;

    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    private String PAYMENT_ENDPOINT_URL;

    private Logger log = LoggerFactory.getLogger(OrderService.class);

    public TransactionResponse saveOrder(TransactionRequest request) throws JsonProcessingException {
        String message = "";
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());

        log.info("OrderService request : {}", new ObjectMapper().writeValueAsString(request));

        Payment paymentResponse = restTemplate.postForObject(PAYMENT_ENDPOINT_URL,payment,
                Payment.class);

        log.info("PaymentService response from order service : {}", new ObjectMapper().writeValueAsString(paymentResponse));
        message = paymentResponse.getPaymentStatus().equals("success") ?
                "payment successful and order placed" : "failure in payment";
        repository.save(order);
        return new TransactionResponse(order, paymentResponse.getAmount(),
                paymentResponse.getTransactionId(), message);
    }
}
