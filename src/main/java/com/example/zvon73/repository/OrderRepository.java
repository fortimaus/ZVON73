package com.example.zvon73.repository;

import com.example.zvon73.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query(value = "Select o.* from orders o " +
            "where o.temple_end_id in (SELECT t.id from temples t " +
            "where t.operator_id = :value) and o.status = 'Waiting_operator'", nativeQuery = true)
    Optional<List<Order>> findNewOrdersByOperatorId(@Param("value") UUID id);

    @Query(value = "Select o.* from orders o " +
            "where o.temple_start_id in (SELECT t.id from temples t " +
            "where t.operator_id = :value) and o.status != 'Waiting_operator'", nativeQuery = true)
    Optional<List<Order>> findOldByOperatorId(@Param("value") UUID id);

    @Query(value = "Select o.* from orders o " +
            "where o.status = 'Waiting_moderator' " , nativeQuery = true)
    Optional<List<Order>> findForModeration();

    @Query(value = "Select o.* from orders o " +
            "where o.status = 'In_path' " , nativeQuery = true)
    Optional<List<Order>> findInPath();
}
