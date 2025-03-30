package com.example.zvon73.controller;

import com.example.zvon73.DTO.BellDto;
import com.example.zvon73.DTO.BellTowerDto;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.entity.Bell;
import com.example.zvon73.service.BellService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.zvon73.config.SecurityConfig.SECURITY_CONFIG_NAME;

@RestController
@RequestMapping("/api/bell")
@RequiredArgsConstructor
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
public class BellController {
    private final BellService bellService;
    @GetMapping
    public ResponseEntity<BellDto> get(@RequestParam("id") String id){
        return ResponseEntity.ok(new BellDto(bellService.findById(UUID.fromString(id))));
    }
    @GetMapping("/list")
    public ResponseEntity<List<BellDto>> getFullList(){
        return ResponseEntity.ok(bellService.findAll());
    }
    @GetMapping("/list/temple")
    public ResponseEntity<List<BellDto>> getFullListByTemple(@RequestParam("id") String id){
        return ResponseEntity.ok(bellService.findByTemple(UUID.fromString(id)));

    }
    @GetMapping("/search/list")
    public ResponseEntity<List<BellDto>> getFullListByName(@RequestParam("name") String name){
        return ResponseEntity.ok(bellService.findByName(name));
    }
    @PostMapping("/create")
    public ResponseEntity<BellDto> create(@RequestBody BellDto bellDto){
        return ResponseEntity.ok((bellService.create(bellDto)));
    }
    @PutMapping("/edit")
    public ResponseEntity<BellDto> edit(@RequestBody BellDto bellDto){
        return ResponseEntity.ok(bellService.update(bellDto));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<MessageResponse> delete(@RequestParam("id") String id){
            return ResponseEntity.ok(bellService.delete(UUID.fromString(id)));

    }
    @PutMapping("/preserve")
    public ResponseEntity<MessageResponse> preserve(@RequestParam("id") String id){
        return ResponseEntity.ok(bellService.madeCanned(UUID.fromString(id)));
    }

    @PutMapping("/recover")
    public ResponseEntity<MessageResponse> recover(@RequestParam("id") String id){
        return ResponseEntity.ok(bellService.recover(UUID.fromString(id)));
    }
}
