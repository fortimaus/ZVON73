package com.example.zvon73.repository;


import com.example.zvon73.entity.BellTower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BellTowerRepository extends JpaRepository<BellTower, UUID> {

    @Query(value = "select bt from bell_towers bt where bt.templeId = :id", nativeQuery = true)
    Optional<List<BellTower>> findByTempleId(UUID id);
}
