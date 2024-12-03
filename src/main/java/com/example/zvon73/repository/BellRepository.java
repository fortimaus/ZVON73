package com.example.zvon73.repository;


import com.example.zvon73.entity.Bell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BellRepository extends JpaRepository<Bell, UUID> {
    @Query(value = "SELECT b from bells b where b.bell_towerId in " +
            "(SELECT bt from bell_towers bt where bt.templeId = :id)", nativeQuery = true)
    Optional<List<Bell>> findByTempleId(UUID id);
}
