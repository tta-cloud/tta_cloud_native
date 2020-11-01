package com.example.cloudnative.usersws.client;

import com.example.cloudnative.usersws.model.OrderResponseModel;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(url="http://orders-app-service:50002", name="orders-app-service", fallbackFactory = OrdersFallbackFactory.class)
public interface OrdersServiceClient {

    @GetMapping("/orders-ms/users/{id}/orders")
    List<OrderResponseModel> getOrders(@PathVariable String id);

}

@Component
class OrdersFallbackFactory implements FallbackFactory<OrdersServiceClient> {
    @Override
    public OrdersServiceClient create(Throwable cause) {
        return new OrdersServiceClientFallback(cause);
    }
}

@Slf4j
class OrdersServiceClientFallback implements OrdersServiceClient {
    private final Throwable cause;

    public OrdersServiceClientFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public List<OrderResponseModel> getOrders(String id) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            log.error("404 error took place when getOrders was called with userId: " +
                    id + ". Error message: " + cause.getLocalizedMessage());
        } else {
            log.error("Other error took place: " + cause.getLocalizedMessage());
        }

        return new ArrayList<>();
    }
}