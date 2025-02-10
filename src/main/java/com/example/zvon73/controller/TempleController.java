package com.example.zvon73.controller;

import com.example.zvon73.DTO.TempleDto;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.controller.domain.TempleOperatorRequest;
import com.example.zvon73.service.TempleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.zvon73.config.SecurityConfig.SECURITY_CONFIG_NAME;

@RestController
@RequestMapping("/temple")
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
    @GetMapping("/operator")
    public ResponseEntity<TempleDto> getTempleByUser(){
        return ResponseEntity.ok(new TempleDto(templeService.findByUser()));
    }
    @GetMapping
    public ResponseEntity<TempleDto> get(@RequestParam("id") String id){
        return ResponseEntity.ok(new TempleDto(templeService.findById(UUID.fromString(id))));
    }
    @PostMapping("/create")
    public ResponseEntity<TempleDto> createTemple(@RequestBody TempleDto templeDto){
        return ResponseEntity.ok(new TempleDto(templeService.create(templeDto)));
    }
    @PutMapping("/change-operator")
    public ResponseEntity<MessageResponse> changeOperator(@RequestBody TempleOperatorRequest request){
        return ResponseEntity.ok(templeService.updateOperator(request));
    }

    @PutMapping("/edit")
    public ResponseEntity<TempleDto> editTemple(@RequestBody TempleDto templeDto){
        return ResponseEntity.ok(new TempleDto(templeService.update(templeDto)));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<MessageResponse> deleteTemple(@RequestParam("id") String id){
        return ResponseEntity.ok(templeService.delete(UUID.fromString(id)));
    }
}
