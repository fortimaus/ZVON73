package com.example.zvon73.DTO;

import com.example.zvon73.entity.Manufacturer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerDto {

    private String id;

    private String title ;

    private String address ;

    private String phone ;

    public ManufacturerDto (Manufacturer manufacturer){
       this.id = manufacturer.getId().toString();
       this.title = manufacturer.getTitle();
       this.address = manufacturer.getAddress();
       this.phone = manufacturer.getPhone();
    }
}