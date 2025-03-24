package com.example.zvon73.controller;

import com.example.zvon73.DTO.BellDto;
import com.example.zvon73.DTO.ManufacturerDto;
import com.example.zvon73.DTO.NoticeDto;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.service.BellService;
import com.example.zvon73.service.NoticeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.zvon73.config.SecurityConfig.SECURITY_CONFIG_NAME;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping
    public ResponseEntity<NoticeDto> get(@RequestParam("id") String id){
        return ResponseEntity.ok(new NoticeDto(noticeService.findById(UUID.fromString(id))));

    }
    @GetMapping("/list_take")
    public ResponseEntity<List<NoticeDto>> getTakeList(){
        return ResponseEntity.ok(noticeService.findTakeNotice());
    }
    @GetMapping("/list_give")
    public ResponseEntity<List<NoticeDto>> getGiveList(){
        return ResponseEntity.ok(noticeService.findGiveNotice());
    }
    @GetMapping("/my_list")
    public ResponseEntity<List<NoticeDto>> getMyList(){
        return ResponseEntity.ok(noticeService.findMyNotice());
    }
    @PostMapping("/create")
    public ResponseEntity<NoticeDto> create(@RequestBody NoticeDto noticeDto){
        return ResponseEntity.ok(noticeService.create(noticeDto));
    }
    @PutMapping("/edit")
    public ResponseEntity<NoticeDto> edit(@RequestBody NoticeDto noticeDto){
        return ResponseEntity.ok(noticeService.update(noticeDto));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<MessageResponse> delete(@RequestParam("id") String id){
        return ResponseEntity.ok(noticeService.delete(UUID.fromString(id)));
    }
}
