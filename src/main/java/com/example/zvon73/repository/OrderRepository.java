package com.example.zvon73.repository;

import com.example.zvon73.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query(value = "Select o from orders o " +
            "where o.temple_endId = (SELECT t from temples t " +
            "where t.operatorId = :id and t.status = 'Waiting_operator')", nativeQuery = true)
    Optional<List<Order>> findNewOrdersByOperatorId(UUID id);

    @Query(value = "Select o from orders o " +
            "where o.temple_endId = (SELECT t from temples t " +
            "where t.operatorId = :id and t.status != 'Waiting_operator')", nativeQuery = true)
    Optional<List<Order>> findOldByOperatorId(UUID id);
}
