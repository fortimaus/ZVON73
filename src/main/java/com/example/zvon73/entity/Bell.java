package com.example.zvon73.entity;

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
@Entity
@Table(name = "bells")
public class Bell {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title ;

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer ;

    @Column(name = "weight", nullable = false)
    private int weight;

    @Column(name = "image", columnDefinition="bytea", nullable = false)
    private byte[] image ;

    @Column(name = "sound", columnDefinition="bytea", nullable = false)
    private byte[] sound ;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private BellStatus status ;

    @ManyToOne
    @JoinColumn(name = "bell_towerId", nullable = false)
    private BellTower bellTower ;


}
