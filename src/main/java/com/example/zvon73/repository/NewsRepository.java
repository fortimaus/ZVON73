package com.example.zvon73.repository;

import com.example.zvon73.entity.News;
import com.example.zvon73.entity.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NewsRepository extends JpaRepository<News, UUID> {
}
