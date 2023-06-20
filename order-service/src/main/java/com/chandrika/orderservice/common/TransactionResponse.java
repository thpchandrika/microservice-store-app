package com.chandrika.orderservice.common;

import com.chandrika.orderservice.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionResponse {
    private Order order;
    private double amount;
    private String transactionId;
    private String message;
}
