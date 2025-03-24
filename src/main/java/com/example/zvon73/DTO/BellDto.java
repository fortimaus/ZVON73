package com.example.zvon73.DTO;

import com.example.zvon73.entity.Bell;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private int diameter;

    private byte[] sound ;

    private String bellTowerName;

    private String bellTowerId;

    public BellDto(Bell bell){
        this.id = bell.getId().toString();
        this.title = bell.getTitle();
        this.manufacturer = bell.getManufacturer();
        this.weight = bell.getWeight();
        this.diameter = bell.getDiameter();
        this.image = bell.getImage();
        this.sound = bell.getSound();
        this.bellTowerName = bell.getBellTower().getTitle();
        this.bellTowerId = bell.getBellTower().getId().toString();
    }

}
