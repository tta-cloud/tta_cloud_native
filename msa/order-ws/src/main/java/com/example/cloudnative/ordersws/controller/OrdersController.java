package com.example.cloudnative.ordersws.controller;

import com.example.cloudnative.ordersws.dto.OrderDto;
import com.example.cloudnative.ordersws.entity.OrderEntity;
import com.example.cloudnative.ordersws.messagemq.KafkaProducer;
import com.example.cloudnative.ordersws.model.CreateOrderRequestModel;
import com.example.cloudnative.ordersws.model.CreateOrderResponseModel;
import com.example.cloudnative.ordersws.model.OrderResponseModel;
import com.example.cloudnative.ordersws.service.OrdersService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders-ms")
public class OrdersController {
    @Autowired
    private Environment env;

    @Autowired
    OrdersService ordersService;

    @Autowired
    private KafkaProducer kafkaProducer;

    @GetMapping("/")
    public String health() {
        return "Hi, there. I'm an Orders microservice";
    }

    @PostMapping(value="/users/{userId}/orders")
    public ResponseEntity<CreateOrderResponseModel> createUser(@PathVariable("userId") String userId,
                                                               @RequestBody CreateOrderRequestModel orderDetails) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = modelMapper.map(orderDetails, OrderDto.class);
        orderDto.setUserId(userId);
        OrderDto createDto = ordersService.createOrder(orderDto);
        CreateOrderResponseModel returnValue = modelMapper.map(createDto, CreateOrderResponseModel.class);

        /* Kafka producer */
        kafkaProducer.send("example-kafka-test", orderDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping(value="/users/{userId}/orders")
    public ResponseEntity<List<OrderResponseModel>> getUser(@PathVariable("userId") String userId) {
        Iterable<OrderEntity> orderList = ordersService.getOrdersByUserId(userId);

        List<OrderResponseModel> result = new ArrayList<>();
        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, OrderResponseModel.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
