package com.example.zvon73.DTO;

import com.example.zvon73.entity.Temple;
import com.example.zvon73.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TempleDto {

    private UUID id;

    private String title ;

    private String description ;

    private String address ;

    private String phone ;

    private byte[] image ;


    private UUID user = null ;

    public TempleDto(Temple temple){
       this.id = temple.getId();
       this.title = temple.getTitle();
       this.description = temple.getDescription();
       this.address = temple.getAddress();
       this.phone = temple.getPhone();
       this.image = temple.getImage();
       if(temple.getUser() != null)
            this.user = temple.getUser().getId();
    }
}
