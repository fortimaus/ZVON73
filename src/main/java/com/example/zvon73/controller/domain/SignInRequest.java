package com.example.zvon73.controller.domain;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}
