package com.example.zvon73.controller;

import com.example.zvon73.DTO.UserDto;
import com.example.zvon73.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping("/user/list")
    public ResponseEntity<List<UserDto>> getUserList(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "5") int size){
        return ResponseEntity.ok(userService.getUserListForAdmin(PageRequest.of(page, size)));
    }

}
