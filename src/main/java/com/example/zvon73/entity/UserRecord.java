package com.example.zvon73.entity;

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
@Entity(name = "user_records")
public class UserRecord {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "bytea")
    private byte[] record;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
}
