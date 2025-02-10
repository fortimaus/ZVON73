package com.example.zvon73.controller.domain;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String email;
    private String password;
    private String token;
}
