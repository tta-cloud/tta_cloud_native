package com.example.cloudnative.usersws.model;

import lombok.Data;

import java.util.Date;

@Data
public class OrderResponseModel {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private Date createdAt;

    private String orderId;
}
