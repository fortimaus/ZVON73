package com.example.zvon73.controller.domain;

import lombok.Data;

@Data
public class VerifyRequest {
    private String email;
    private String token;
}
