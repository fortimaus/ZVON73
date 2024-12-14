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

    private UUID id;


    private String title ;

    private UUID temple ;

    private String temple_title;

    private String address;

    private List<BellDto> bells = new ArrayList<>();

    public BellTowerDto(BellTower bellTower){
        this.id = bellTower.getId();
        this.title = bellTower.getTitle();
        this.temple = bellTower.getTemple().getId();
        this.temple_title = bellTower.getTemple().getTitle();
        this.address = bellTower.getTemple().getAddress();
        if(bellTower.getBells() != null && !bellTower.getBells().isEmpty())
            this.bells = bellTower.getBells().stream().map(BellDto::new).toList();
    }
}
