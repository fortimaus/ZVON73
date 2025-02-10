package com.example.zvon73.entity;

import com.example.zvon73.entity.Enums.OrderStatus;
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
@Entity
@Table(name = "orders")
public class Order {


    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "temple_startId", nullable = false)
    private Temple temple_start ;

    @ManyToOne
    @JoinColumn(name = "temple_endId", nullable = false)
    private Temple temple_end ;

    @ManyToOne
    @JoinColumn(name = "bell_tower_startId", nullable = false)
    private BellTower bellTower_start ;

    @ManyToOne
    @JoinColumn(name = "bell_tower_endId", nullable = true)
    private BellTower bellTower_end ;

    @ManyToOne
    @JoinColumn(name = "bellId", nullable = false)
    private Bell bell ;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date ;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private OrderStatus status ;


}
