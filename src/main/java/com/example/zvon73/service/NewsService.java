package com.example.zvon73.service;

import com.example.zvon73.DTO.NewsDto;
import com.example.zvon73.controller.domain.MessageResponse;
import com.example.zvon73.entity.News;
import com.example.zvon73.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final UserService userService;
    private final NewsRepository newsRepository;

    public List<NewsDto> getAllNewsList(PageRequest request){
        Page<News> newsPage = newsRepository.findAll(request);
        return newsPage.getContent().stream().map(NewsDto::new).collect(Collectors.toList());
    }

    public News getById(String id){
        return newsRepository.findById(UUID.fromString(id))
                .orElse(null);}

    public MessageResponse create(NewsDto request) {
        try {
            var user = userService.getCurrentUser();
            var record = News.builder()
                    .title(request.getTitle())
                    .user(user)
                    .text(request.getText())
                    .image(request.getImage())
                    .date(LocalDateTime.now())
                    .build();
            newsRepository.save(record);
            return new MessageResponse("Новость сохранена", "");
        }catch (Exception e){
            return new MessageResponse("", e.getMessage());
        }
    }

    public MessageResponse delete(String id){
        News news = getById(id);
        if(news == null)
            return new MessageResponse("", "Новость не найден");
        newsRepository.delete(news);
        return new MessageResponse("Новость удалена", "");
    }
}
