package com.example.cloudnative.ordersws.dto;

import com.example.cloudnative.ordersws.model.OrderResponseModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderDto implements Serializable {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;
}
