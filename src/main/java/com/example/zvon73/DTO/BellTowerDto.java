package com.example.zvon73.DTO;

import com.example.zvon73.entity.Bell;
import com.example.zvon73.entity.BellTower;
import com.example.zvon73.entity.Temple;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BellTowerDto {
    private String id;
    private String title ;

    private String templeName;

    private String templeId;

    private String address;


    public BellTowerDto(BellTower bellTower){
        this.id = bellTower.getId().toString();
        this.title = bellTower.getTitle();
        this.templeName = bellTower.getTemple().getTitle();
        this.templeId = bellTower.getTemple().getId().toString();
        this.address = bellTower.getTemple().getAddress();
    }
}
