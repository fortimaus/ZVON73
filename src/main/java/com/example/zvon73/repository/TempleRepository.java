package com.example.zvon73.repository;

import com.example.zvon73.entity.Temple;
import com.example.zvon73.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TempleRepository extends JpaRepository<Temple, UUID> {

    Temple findByUser(User user);

    Optional<List<Temple>> findAllByUserIsNull();
    @Query("SELECT t FROM Temple t WHERE TRIM(LOWER(t.title)) LIKE TRIM(LOWER(:name))")
    List<Temple> findListByName(String name);

    List<Temple> findAllByIdNot(UUID id);
}
