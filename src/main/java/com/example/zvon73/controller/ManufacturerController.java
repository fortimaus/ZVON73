package com.example.zvon73.controller;

import java.util.List;
import java.util.UUID;

import com.example.zvon73.DTO.BellTowerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.zvon73.DTO.ManufacturerDto;
import static com.example.zvon73.config.SecurityConfig.SECURITY_CONFIG_NAME;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.service.ManufacturerService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/manufacturer")
@RequiredArgsConstructor
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    @GetMapping("/list")
    public ResponseEntity<List<ManufacturerDto>> getFullList() {
        return ResponseEntity.ok(manufacturerService.findAll());
    }

    @GetMapping("/search-list")
    public ResponseEntity<List<ManufacturerDto>> getSearchListByTitle(@RequestParam("title") String title) {
        return ResponseEntity.ok(manufacturerService.findListByTitle(title));
    }

    @GetMapping
    public ResponseEntity<ManufacturerDto> getManufacturerById(@RequestParam("id") String id) {
        return ResponseEntity.ok(new ManufacturerDto(manufacturerService.findById(UUID.fromString(id))));
    }

}