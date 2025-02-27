package com.example.zvon73.repository;

import com.example.zvon73.entity.Notice;
import com.example.zvon73.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface NoticeRepository extends JpaRepository<Notice, UUID> {

    @Query(value = "Select n.* from notices n " +
            "where n.userId = :value ", nativeQuery = true)
    List<Notice> findMyNotices(@Param("value") UUID id);

    @Query(value = "Select n.* from notices n " +
            "where n.give = :value ", nativeQuery = true)
    List<Notice> findTypeNotices(@Param("value") boolean value);
}
