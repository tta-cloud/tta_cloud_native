package com.example.cloudnative.usersws.service;

import com.example.cloudnative.usersws.client.OrdersServiceClient;
import com.example.cloudnative.usersws.dto.UserDto;
import com.example.cloudnative.usersws.entity.UserEntity;
import com.example.cloudnative.usersws.model.OrderResponseModel;
import com.example.cloudnative.usersws.repository.UsersRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService {
    UsersRepository repository;

    Environment env;

    OrdersServiceClient ordersServiceClient;

    @Autowired
    public UsersServiceImpl(UsersRepository repository,
                            OrdersServiceClient ordersServiceClient,
                            Environment env) {
        this.repository = repository;
        this.ordersServiceClient = ordersServiceClient;
        this.env = env;
    }

    @Override
    public UserDto createUser(UserDto userDetails) {

        userDetails.setUserId(UUID.randomUUID().toString());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
        repository.save(userEntity);

        UserDto returnValue = modelMapper.map(userEntity, UserDto.class);
        return returnValue;
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = repository.findByEmail(email);

        if (userEntity == null)
            log.error(String.format(""));

        return new ModelMapper().map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = repository.findByUserId(userId);

//        if (userEntity == null)
//            throw new UsernameNotFoundException("User not found");

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        List<OrderResponseModel> ordersList = ordersServiceClient.getOrders(userId);
        userDto.setOrders(ordersList);

        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return repository.findAll();
    }
}
