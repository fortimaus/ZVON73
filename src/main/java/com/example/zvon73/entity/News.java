package com.example.zvon73.entity;

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
@Entity(name = "news")
public class News {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false, columnDefinition = "bytea")
    private byte[] image;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date ;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

}
