package com.example.zvon73.DTO;

import com.example.zvon73.entity.Temple;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTemplesDto {
    private String id;
    public UserTemplesDto(Temple temple){
        this.id = temple.getId().toString();


    }
}
