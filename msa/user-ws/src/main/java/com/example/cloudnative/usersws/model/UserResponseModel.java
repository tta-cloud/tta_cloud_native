package com.example.cloudnative.usersws.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseModel {
    private String userId;
    private String name;
    private String email;

    private List<OrderResponseModel> orders;
}
