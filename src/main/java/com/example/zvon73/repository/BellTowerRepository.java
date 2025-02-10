package com.example.zvon73.repository;


import com.example.zvon73.entity.BellTower;
import com.example.zvon73.entity.Temple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BellTowerRepository extends JpaRepository<BellTower, UUID> {
    @Query("SELECT bt FROM BellTower bt WHERE TRIM(LOWER(bt.title)) LIKE TRIM(LOWER(:name))")
    List<BellTower> findListByName(String name);
    List<BellTower> findByTemple(Temple temple);
}
