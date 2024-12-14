package com.example.zvon73.repository;

import com.example.zvon73.entity.Temple;
import com.example.zvon73.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TempleRepository extends JpaRepository<Temple, UUID> {

    Optional<Temple> findByUser(User user);

    Optional<List<Temple>> findAllByUserIsNull();

    List<Temple> findAllByIdNot(UUID id);
}
