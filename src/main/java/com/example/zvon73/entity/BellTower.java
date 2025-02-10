package com.example.zvon73.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bell_towers")
public class BellTower {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;


    @Column(name = "title", nullable = false)
    private String title ;

    @ManyToOne
    @JoinColumn(name = "templeId", nullable = false)
    private Temple temple ;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bellTower")
    private List<Bell> bells ;
}
