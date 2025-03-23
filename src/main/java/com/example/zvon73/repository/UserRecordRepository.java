package com.example.zvon73.repository;

import com.example.zvon73.entity.User;
import com.example.zvon73.entity.UserRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

public interface UserRecordRepository extends JpaRepository<UserRecord, UUID> {
    Page<UserRecord> findAll(Pageable pageable);
}
