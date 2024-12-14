package com.example.zvon73.controller;


import com.example.zvon73.DTO.UserDto;
import com.example.zvon73.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/update")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(new UserDto(userService.update(userDto)));
    }
    @GetMapping
    public ResponseEntity<UserDto> get(@RequestParam("id") String id){
        return ResponseEntity.ok(new UserDto(userService.findById(UUID.fromString(id))));
    }
}
