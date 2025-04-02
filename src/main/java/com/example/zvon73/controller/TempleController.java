package com.example.zvon73.controller;

import com.example.zvon73.DTO.NoticeDto;
import com.example.zvon73.DTO.TempleDto;
import com.example.zvon73.DTO.UserTemplesDto;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.controller.domain.TempleOperatorRequest;
import com.example.zvon73.service.TempleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.zvon73.config.SecurityConfig.SECURITY_CONFIG_NAME;

@RestController
@RequestMapping("/api/temple")
@RequiredArgsConstructor
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
public class TempleController {
    private final TempleService templeService;
    @GetMapping("/list")
    public ResponseEntity<List<TempleDto>> getFullList(){
        return ResponseEntity.ok(templeService.findAll());
    }
    @GetMapping("/search-list")
    public ResponseEntity<List<TempleDto>> getSearchListByName(@RequestParam("name") String name){
        return ResponseEntity.ok(templeService.findListByName(name));
    }
    @GetMapping
    public ResponseEntity<TempleDto> get(@RequestParam("id") String id){
        return ResponseEntity.ok(new TempleDto(templeService.findById(UUID.fromString(id))));
    }

}
