package com.example.zvon73.DTO;

import com.example.zvon73.entity.Bell;
import com.example.zvon73.entity.BellTower;
import com.example.zvon73.entity.Enums.BellStatus;
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
public class BellDto {

    private String id;

    private String title ;

    private String manufacturer ;

    private int weight;

    private byte[] image ;

    private byte[] sound ;

    private String status ;

    private UUID bellTower ;

    public BellDto(Bell bell){
        this.id = bell.getId().toString();
        this.title = bell.getTitle();
        this.manufacturer = bell.getManufacturer();
        this.weight = bell.getWeight();
        this.image = bell.getImage();
        this.sound = bell.getSound();
        this.status = bell.getStatus().toString();
        this.bellTower = bell.getBellTower().getId();
    }

}
