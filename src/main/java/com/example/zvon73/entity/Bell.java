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
@Entity(name = "bells")
public class Bell {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title ;

    @ManyToOne
    @JoinColumn(name = "manufacturerId", nullable = false)
    private Manufacturer manufacturer ;

    @Column(name = "weight", nullable = false)
    private int weight;

    @Column(name = "diameter", nullable = false)
    private int diameter;

    @Column(name = "image", columnDefinition="bytea", nullable = false)
    private byte[] image ;

    @Column(name = "sound", columnDefinition="bytea", nullable = false)
    private byte[] sound ;

    @Column(name = "canned", nullable = false)
    private boolean canned = false;

    @ManyToOne
    @JoinColumn(name = "bell_towerId", nullable = false)
    private BellTower bellTower ;


}
