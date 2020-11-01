package com.example.cloudnative.usersws.dto;

import com.example.cloudnative.usersws.model.OrderResponseModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDto implements Serializable {

    private String name;
    private String email;
    private String pwd;

    private String userId;

    private List<OrderResponseModel> orders;
}
