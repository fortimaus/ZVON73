package com.example.zvon73.controller;

import com.example.zvon73.DTO.BellDto;
import com.example.zvon73.DTO.NewsDto;
import com.example.zvon73.DTO.UserRecordDto;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.controller.domain.UserRecordRequest;
import com.example.zvon73.service.NewsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.zvon73.config.SecurityConfig.SECURITY_CONFIG_NAME;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
public class NewsController {
    private final NewsService newsService;

    @GetMapping("/get-list")
    public ResponseEntity<List<NewsDto>> getListByUserId(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "5") int size){
        return ResponseEntity.ok(newsService.getAllNewsList(PageRequest.of(page, size)));
    }
    @GetMapping
    public ResponseEntity<NewsDto> get(@RequestParam("id") String id){
        return ResponseEntity.ok(new NewsDto(newsService.getById(id)));
    }
    @PostMapping("/create")
    public ResponseEntity<MessageResponse> createNews(@RequestBody NewsDto request){
        return ResponseEntity.ok(newsService.create(request));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<MessageResponse> deleteNews(@RequestBody NewsDto request){
        return ResponseEntity.ok(newsService.delete(request));
    }
}
