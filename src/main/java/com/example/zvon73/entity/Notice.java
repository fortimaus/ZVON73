package com.example.zvon73.entity;

import com.example.zvon73.entity.Enums.BellStatus;
import com.example.zvon73.entity.Enums.TypeNotice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "notices")
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

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeNotice type ;

    @Column(name = "date")
    private LocalDateTime date ;


    @ManyToOne
    @JoinColumn(name = "userId", nullable = true)
    private User user ;

    @ManyToOne
    @JoinColumn(name = "templeId", nullable = false)
    private Temple temple ;

    final static DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String getDate(){
        return date.format(CUSTOM_FORMATTER);
    }
}
