package com.example.cloudnative.ordersws.repository;

import com.example.cloudnative.ordersws.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<OrderEntity, Long> {
    OrderEntity findByOrderId(String orderId);
    Iterable<OrderEntity> findByUserId(String userId);
}
