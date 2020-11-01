package com.example.cloudnative.usersws.service;

import com.example.cloudnative.usersws.dto.UserDto;
import com.example.cloudnative.usersws.entity.UserEntity;

import java.util.List;

public interface UsersService {
    UserDto createUser(UserDto userDetails);
    UserDto getUserDetailsByEmail(String email);
    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();
}
