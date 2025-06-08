package com.example.zvon73.controller.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RoleRequest {
    private String user;
    private List<String> temples;
    private String role;
}
