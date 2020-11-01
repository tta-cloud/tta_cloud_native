package com.example.cloudnative.usersws.controller;

import com.example.cloudnative.usersws.dto.UserDto;
import com.example.cloudnative.usersws.entity.UserEntity;
import com.example.cloudnative.usersws.model.CreateUserRequestModel;
import com.example.cloudnative.usersws.model.CreateUserResponseModel;
import com.example.cloudnative.usersws.model.UserResponseModel;
import com.example.cloudnative.usersws.service.UsersService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users-ms")
public class UsersController {
    @Autowired
    private Environment env;

    @Autowired
    UsersService usersService;

    @GetMapping("/")
    public String health() {
        return "Hi, there. I'm an Users microservice";
    }

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponseModel> createUser(@RequestBody CreateUserRequestModel userDetails) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserDto createDto = usersService.createUser(userDto);

        CreateUserResponseModel returnValue = modelMapper.map(createDto, CreateUserResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping(value="/users")
    public ResponseEntity<List<UserResponseModel>> getUsers() {
        Iterable<UserEntity> userList = usersService.getUserByAll();

        List<UserResponseModel> result = new ArrayList<>();
        userList.forEach(v -> {
            result.add(new ModelMapper().map(v, UserResponseModel.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(value="/users/{userId}")
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId) {
        UserDto userDto = usersService.getUserByUserId(userId);
        UserResponseModel returnValue = new ModelMapper().map(userDto, UserResponseModel.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
}
