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

    private boolean give ;

    private String  date ;


    private String bell ;

    private String user ;

    private String temple ;

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
        this.give = notice.isGive();
        this.date = notice.getDate().toString();
        this.bell = notice.getBell().getId().toString();
        this.user = notice.getUser().getId().toString();
        this.temple = notice.getTemple().getId().toString();
        this.email = notice.getUser().getEmail();
    }
}
