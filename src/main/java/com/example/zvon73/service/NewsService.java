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

    public NewsDto getById(String id){
        News news = newsRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException("Новость с данным id не найдена"));
        return new NewsDto(news);
    }

    public MessageResponse create(NewsDto request) {
        try {
            var user = userService.getCurrentUser();
            var record = News.builder()
                    .title(request.getTitle())
                    .user(user)
                    .text(request.getText())
                    .image(request.getImage())
                    .date(new Date())
                    .build();
            newsRepository.save(record);
            return new MessageResponse("Новость сохранена", "");
        }catch (Exception e){
            return new MessageResponse("", e.getMessage());
        }
    }

    public MessageResponse delete(NewsDto record){
        try {
            newsRepository.deleteById(UUID.fromString(record.getId()));
            return new MessageResponse("Новость удалена", "");
        }catch (Exception e){
            return new MessageResponse("", e.getMessage());
        }

    }
}
