package com.chandrika.orderservice.controller;

import com.chandrika.orderservice.common.Payment;
import com.chandrika.orderservice.common.TransactionRequest;
import com.chandrika.orderservice.common.TransactionResponse;
import com.chandrika.orderservice.entity.Order;
import com.chandrika.orderservice.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService service;

    @PostMapping()
    public TransactionResponse bookOrder(@RequestBody TransactionRequest request) throws JsonProcessingException {
        return service.saveOrder(request);
    }
}
