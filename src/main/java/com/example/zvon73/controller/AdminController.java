package com.example.zvon73.controller;

import com.example.zvon73.DTO.ManufacturerDto;
import com.example.zvon73.DTO.TempleDto;
import com.example.zvon73.DTO.UserDto;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.controller.domain.RoleRequest;
import com.example.zvon73.service.ManufacturerService;
import com.example.zvon73.service.NewsService;
import com.example.zvon73.service.TempleService;
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
@RequestMapping("/api/admin")
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final TempleService templeService;
    private final NewsService newsService;
    private final ManufacturerService manufacturerService;

    @GetMapping("/user/list")
    public ResponseEntity<List<UserDto>> getUserList(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "5") int size){
        return ResponseEntity.ok(userService.getUserListForAdmin(PageRequest.of(page, size)));
    }
    @PutMapping("/user/update-role")
    public ResponseEntity<MessageResponse> updateRole(@RequestBody RoleRequest request){
        return ResponseEntity.ok(userService.updateRole(request));
    }
    @DeleteMapping("/news/delete")
    public ResponseEntity<MessageResponse> deleteNews(@RequestParam("id") String id){
        return ResponseEntity.ok(newsService.delete(id));
    }
    @DeleteMapping("/temple/delete")
    public ResponseEntity<MessageResponse> deleteTemple(@RequestParam("id") String id){
        return ResponseEntity.ok(templeService.delete(UUID.fromString(id)));
    }
    @PostMapping("/temple/create")
    public ResponseEntity<TempleDto> createTemple(@RequestBody TempleDto templeDto){
        return ResponseEntity.ok(templeService.create(templeDto));
    }
    @PostMapping("/manufacturer/create")
    public ResponseEntity<ManufacturerDto> createManufacturer(@RequestBody ManufacturerDto manufacturerDto) {
        return ResponseEntity.ok(manufacturerService.create(manufacturerDto));

    }
    @PutMapping("/manufacturer/edit")
    public ResponseEntity<ManufacturerDto> editManufacturer(@RequestBody ManufacturerDto manufacturerDto) {
        return ResponseEntity.ok(manufacturerService.update(manufacturerDto));
    }
    @DeleteMapping("/manufacturer/delete")
    public ResponseEntity<MessageResponse> deleteManufacturer(@RequestParam("id") String id) {
        return ResponseEntity.ok(manufacturerService.delete(UUID.fromString(id)));
    }

}
