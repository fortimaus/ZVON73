package com.example.zvon73.repository;

import com.example.zvon73.entity.Manufacturer;
import com.example.zvon73.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, UUID> {

    @Query("SELECT t FROM manufacturers t WHERE TRIM(LOWER(t.title)) LIKE TRIM(LOWER(:name))")
    List<Manufacturer> findListByTitle(String name);

    List<Manufacturer> findAllByIdNot(UUID id);
}
