package com.example.zvon73.controller;


import com.example.zvon73.DTO.UserDto;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.controller.domain.RoleRequest;
import com.example.zvon73.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.zvon73.config.SecurityConfig.SECURITY_CONFIG_NAME;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
public class UserController {
    private final UserService userService;

    @PutMapping("/update")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(new UserDto(userService.update(userDto)));
    }

    @GetMapping
    public ResponseEntity<UserDto> get(){
        return ResponseEntity.ok(new UserDto(userService.getCurrentUser()));
    }
}
