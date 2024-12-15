package com.example.zvon73.DTO;

import com.example.zvon73.entity.Bell;
import com.example.zvon73.entity.BellTower;
import com.example.zvon73.entity.Enums.OrderStatus;
import com.example.zvon73.entity.Order;
import com.example.zvon73.entity.Temple;
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
public class OrderDto {
    private String id;


    private String temple_start ;

    private String temple_start_title;


    private String temple_end ;

    private String temple_end_title;

    private String bellTower_start ;

    private String bellTower_start_title;

    private String bellTower_end = null;

    private String bellTower_end_title = "";

    private String bell ;

    private String bell_title;

    private String date ;


    private String status ;

    public OrderDto(Order order){
        this.id = order.getId().toString();
        this.temple_start = order.getTemple_start().getId().toString();
        this.temple_start_title = order.getTemple_start().getTitle();
        this.temple_end = order.getTemple_end().getId().toString();
        this.temple_end_title = order.getTemple_end().getTitle();
        this.bellTower_start = order.getBellTower_start().getId().toString();
        this.bellTower_start_title = order.getBellTower_start().getTitle();
        if(order.getBellTower_end() !=null) {
            this.bellTower_end = order.getBellTower_end().getId().toString();
            this.bellTower_end_title = order.getBellTower_end().getTitle();
        }

        this.bell = order.getBell().getId().toString();
        this.bell_title = order.getBell().getTitle();
        if(order.getDate() != null) {
            this.date = order.getDate().toString();
        }
        this.status = order.getStatus().toString();
    }
}
