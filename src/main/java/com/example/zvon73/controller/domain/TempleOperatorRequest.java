package com.example.zvon73.controller.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class TempleOperatorRequest {
    private List<String> temples;
    private String user;
}
