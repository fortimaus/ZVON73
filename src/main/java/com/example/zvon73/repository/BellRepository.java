package com.example.zvon73.repository;


import com.example.zvon73.entity.Bell;
import com.example.zvon73.entity.BellTower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BellRepository extends JpaRepository<Bell, UUID> {
    @Query(value = "SELECT b.* from bells b " +
            "JOIN bell_towers bt  on b.bell_tower_id = bt.id " +
            "where bt.temple_id = :value" , nativeQuery = true)
    List<Bell> findByTempleId(@Param("value") UUID id);
    @Query("SELECT b FROM Bell b WHERE TRIM(LOWER(b.title)) LIKE TRIM(LOWER(:name))")
    List<Bell> findListByName(String name);

    List<Bell> findByCannedFalse();
}
