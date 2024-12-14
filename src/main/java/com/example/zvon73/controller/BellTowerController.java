package com.example.zvon73.controller;

import com.example.zvon73.DTO.BellTowerDto;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.service.BellTowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bell-tower")
@RequiredArgsConstructor
public class BellTowerController {
    private final BellTowerService bellTowerService;

    @GetMapping
    public ResponseEntity<BellTowerDto> get(@RequestParam("id") String id){
        return ResponseEntity.ok(new BellTowerDto(bellTowerService.findById(UUID.fromString(id))));
    }
    @GetMapping("/list")
    public ResponseEntity<List<BellTowerDto>> getFullList(){
        return ResponseEntity.ok(bellTowerService.findAll());
    }
    @GetMapping("/list/temple")
    public ResponseEntity<List<BellTowerDto>> getFullListByTemple(@RequestParam("id") String id){
        return ResponseEntity.ok(bellTowerService.findAllByTemple(UUID.fromString(id)));
    }
    @GetMapping("/search/list")
    public ResponseEntity<List<BellTowerDto>> getFullListByName(@RequestParam("name") String name){
        return ResponseEntity.ok(bellTowerService.findAllByName(name));
    }
    @PostMapping("/create")
    public ResponseEntity<BellTowerDto> create(@RequestBody BellTowerDto bellTowerDto){
        return ResponseEntity.ok(new BellTowerDto(bellTowerService.create(bellTowerDto)));
    }
    @PutMapping("/edit")
    public ResponseEntity<BellTowerDto> edit(@RequestBody BellTowerDto bellTowerDto){
        return ResponseEntity.ok(new BellTowerDto(bellTowerService.update(bellTowerDto)));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<MessageResponse> delete(@RequestParam("id") String id){
        return ResponseEntity.ok(bellTowerService.delete(UUID.fromString(id)));
    }
}
