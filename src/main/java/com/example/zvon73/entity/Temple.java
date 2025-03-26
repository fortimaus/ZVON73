package com.example.zvon73.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "temples")
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

    @Column(name = "image", columnDefinition = "bytea")
    private byte[] image ;

    @ManyToMany
    @JoinTable(
            name = "ringers",
            joinColumns = @JoinColumn(name = "temple_id"),
            inverseJoinColumns = @JoinColumn(name = "ringer_id"))
    List<User> ringers;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "temple")
    List<BellTower> BellTowers;

    public void addRinger(User user){
        if(ringers == null)
            ringers = new ArrayList<>();
        for(User ringer : ringers){
           if(ringer.getId().equals(user.getId()))
               return;
        }
        ringers.add(user);
    }
    public void deleteRinger(User user){
        if(ringers == null)
            return;
        for(User ringer : ringers){
            if(ringer.getId().equals(user.getId()))
            {
             ringers.remove(ringer);
             return;
            }
        }
    }

    public boolean checkRinger(UUID id){
        if(ringers == null)
            return false;

        for(User ringer : ringers)
        {
            if(ringer.getId().equals(id))
                return true;
        }
        return false;
    }
}
