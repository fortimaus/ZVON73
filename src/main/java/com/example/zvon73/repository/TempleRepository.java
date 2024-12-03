package com.example.zvon73.repository;

import com.example.zvon73.entity.Temple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TempleRepository extends JpaRepository<Temple, UUID> {

    @Query(value = "select t from temples t where t.operatorId = :id", nativeQuery = true)
    Optional<Temple> findByUserId(UUID id);

    Optional<List<Temple>> findByUserIsNull();
}
