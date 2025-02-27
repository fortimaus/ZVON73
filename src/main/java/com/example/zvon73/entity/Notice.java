package com.example.zvon73.entity;

import com.example.zvon73.entity.Enums.BellStatus;
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
@Table(name = "notices")
public class Notice {

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

    @Column(name = "diameter", nullable = false)
    private int diameter ;

    @Column(name = "image", columnDefinition="bytea", nullable = false)
    private byte[] image ;

    @Column(name = "description", nullable = false)
    private String description ;

    @Column(name = "give", nullable = false)
    private boolean give ;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date ;

    @ManyToOne
    @JoinColumn(name = "bellId", nullable = true)
    private Bell bell ;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user ;
}
