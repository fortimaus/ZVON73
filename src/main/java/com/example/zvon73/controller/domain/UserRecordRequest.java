package com.example.zvon73.controller.domain;

import lombok.Data;

import java.util.List;

@Data
public class UserRecordRequest {
    private String name;
    private byte[] record;
}
