package com.example.zvon73.controller;

import com.example.zvon73.DTO.BellDto;
import com.example.zvon73.DTO.BellTowerDto;
import com.example.zvon73.DTO.NewsDto;
import com.example.zvon73.DTO.TempleDto;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.service.BellService;
import com.example.zvon73.service.BellTowerService;
import com.example.zvon73.service.NewsService;
import com.example.zvon73.service.TempleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.zvon73.config.SecurityConfig.SECURITY_CONFIG_NAME;

@RestController
@RequestMapping("/api/ringer")
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
@RequiredArgsConstructor
public class RingerController {
    private final BellService bellService;
    private final BellTowerService bellTowerService;
    private final TempleService templeService;
    private final NewsService newsService;

    @PostMapping("/bell/create")
    public ResponseEntity<BellDto> create(@RequestBody BellDto bellDto){
        return ResponseEntity.ok((bellService.create(bellDto)));
    }
    @PutMapping("/bell/edit")
    public ResponseEntity<BellDto> edit(@RequestBody BellDto bellDto){
        return ResponseEntity.ok(bellService.update(bellDto));
    }
    @DeleteMapping("/bell/delete")
    public ResponseEntity<MessageResponse> delete(@RequestParam("id") String id){
        return ResponseEntity.ok(bellService.delete(UUID.fromString(id)));

    }
    @PutMapping("/bell/preserve")
    public ResponseEntity<MessageResponse> preserve(@RequestParam("id") String id){
        return ResponseEntity.ok(bellService.madeCanned(UUID.fromString(id)));
    }

    @PutMapping("/bell/recover")
    public ResponseEntity<MessageResponse> recover(@RequestParam("id") String id){
        return ResponseEntity.ok(bellService.recover(UUID.fromString(id)));
    }
    @PostMapping("/bell-tower/create")
    public ResponseEntity<BellTowerDto> createBellTower(@RequestBody BellTowerDto bellTowerDto){
        return ResponseEntity.ok(bellTowerService.create(bellTowerDto));

    }
    @PutMapping("/bell-tower/edit")
    public ResponseEntity<BellTowerDto> editBellTower(@RequestBody BellTowerDto bellTowerDto){
        return ResponseEntity.ok(bellTowerService.update(bellTowerDto));
    }
    @DeleteMapping("/bell-tower/delete")
    public ResponseEntity<MessageResponse> deleteBellTower(@RequestParam("id") String id){
        return ResponseEntity.ok(bellTowerService.delete(UUID.fromString(id)));
    }
    @PutMapping("/temple/edit")
    public ResponseEntity<TempleDto> editTemple(@RequestBody TempleDto templeDto){
        return ResponseEntity.ok(templeService.update(templeDto));
    }
    @GetMapping("/temple/operator")
    public ResponseEntity<List<TempleDto>> getTempleByUser(){
        return ResponseEntity.ok(templeService.findByUser());
    }

    @PostMapping("/news/create")
    public ResponseEntity<MessageResponse> createNews(@RequestBody NewsDto request){
        return ResponseEntity.ok(newsService.create(request));
    }
}
