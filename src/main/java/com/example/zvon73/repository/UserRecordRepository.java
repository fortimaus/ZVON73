package com.example.zvon73.repository;

import com.example.zvon73.entity.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserRecordRepository extends JpaRepository<UserRecord, UUID> {
    @Query("SELECT ur from user_records ur where ur.user.id = ?1")
    List<UserRecord> getListByUserId(UUID userId);
}
