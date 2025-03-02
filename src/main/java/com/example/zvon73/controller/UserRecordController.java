package com.example.zvon73.controller;

import com.example.zvon73.DTO.UserRecordDto;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.controller.domain.UserRecordRequest;
import com.example.zvon73.service.UserRecordService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.zvon73.config.SecurityConfig.SECURITY_CONFIG_NAME;

@RestController
@RequestMapping("/user-records")
@RequiredArgsConstructor
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
public class UserRecordController {
    private final UserRecordService userRecordService;

    @GetMapping("/get-list")
    public ResponseEntity<List<UserRecordDto>> getListByUserId(@RequestParam("id") String id){
        return ResponseEntity.ok(userRecordService.getRecordsListByUserId(id));
    }

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> createRecord(@RequestBody UserRecordRequest request){
        return ResponseEntity.ok(userRecordService.create(request));
    }
}
