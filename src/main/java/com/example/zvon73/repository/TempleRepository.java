package com.example.zvon73.repository;

import com.example.zvon73.entity.Temple;
import com.example.zvon73.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TempleRepository extends JpaRepository<Temple, UUID> {

    @Query(value = "Select t.* from temples t " +
            "where t.id in (SELECT r.temple_id from ringers r " +
            "where r.ringer_id = :value)", nativeQuery = true)
    List<Temple> findTemplesByRingersId(@Param("value") UUID user);

    @Query("SELECT t FROM temples t WHERE TRIM(LOWER(t.title)) LIKE TRIM(LOWER(:name))")
    List<Temple> findListByName(String name);

}
