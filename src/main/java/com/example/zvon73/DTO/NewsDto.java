package com.example.zvon73.DTO;

import com.example.zvon73.entity.News;
import com.example.zvon73.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto {

    private String id;

    private String title;

    private String text;

    private byte[] image;

    private String date ;

    public NewsDto(News news){
        this.id = news.getId().toString();
        this.title = news.getTitle();
        this.text = news.getText();
        this.image = news.getImage();
        this.date = news.getDate().toString();
    }
}
