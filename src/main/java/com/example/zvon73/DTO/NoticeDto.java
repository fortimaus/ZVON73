package com.example.zvon73.DTO;

import com.example.zvon73.entity.Bell;
import com.example.zvon73.entity.Notice;
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
public class NoticeDto {

    private String id;

    private String title ;

    private String manufacturer ;

    private int weight;

    private int diameter ;

    private byte[] image ;

    private String description ;

    private String type ;

    private String  date ;


    private String user ;

    private String temple ;

    private String templeName ;

    private String email;

    public NoticeDto(Notice notice)
    {
        this.id = notice.getId().toString();
        this.title = notice.getTitle();
        this.manufacturer = notice.getManufacturer();
        this.weight = notice.getWeight();
        this.diameter = notice.getDiameter();
        this.image = notice.getImage();
        this.description = notice.getDescription();
        this.type = notice.getType().toString();
        this.date = notice.getDate();
        this.user = notice.getUser().getId().toString();
        this.temple = notice.getTemple().getId().toString();
        this.templeName = notice.getTemple().getTitle();
        this.email = notice.getUser().getEmail();
    }
}
