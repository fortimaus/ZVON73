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
@Table(name = "temples")
public class Temple {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title ;

    @Column(name = "description", nullable = false)
    private String description ;

    @Column(name = "address", nullable = false)
    private String address ;

    @Column(name = "phone", nullable = false)
    private String phone ;

    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    private byte[] image ;

    @OneToOne
    @JoinColumn(name = "operatorId", nullable = true)
    private User user ;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "temple")
    List<BellTower> BellTowers;


}
