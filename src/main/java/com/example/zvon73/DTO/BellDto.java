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

    private String manufacturerId ;

    private String manufacturerTitle ;

    private int diameter;

    private int weight;

    private byte[] image ;

    private byte[] sound ;

    private boolean canned;

    private String bellTowerName;

    private String bellTowerId;

    public BellDto(Bell bell){
        this.id = bell.getId().toString();
        this.title = bell.getTitle();
        this.manufacturerId = bell.getManufacturer().getId().toString();
        this.manufacturerTitle = bell.getManufacturer().getTitle();
        this.weight = bell.getWeight();
        this.image = bell.getImage();
        this.canned = bell.isCanned();
        this.diameter = bell.getDiameter();
        this.sound = bell.getSound();
        this.bellTowerName = bell.getBellTower().getTitle();
        this.bellTowerId = bell.getBellTower().getId().toString();
    }

}
