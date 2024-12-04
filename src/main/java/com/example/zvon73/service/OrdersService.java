package com.example.zvon73.service;

import com.example.zvon73.entity.Bell;
import com.example.zvon73.entity.Enums.BellStatus;
import com.example.zvon73.entity.Enums.OrderStatus;
import com.example.zvon73.entity.Order;
import com.example.zvon73.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrderRepository orderRepository;

    @Transactional
    public Order create(Order order)
    {
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Order findById(UUID id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Заявки с данным id не найден"));
    }

    @Transactional(readOnly = true)
    public List<Order> findOldByOperatorId(UUID id){
        return orderRepository.findOldByOperatorId(id)
                .orElseThrow(() -> new NotFoundException("Заявок у данного пользователя нет"));
    }

    @Transactional(readOnly = true)
    public List<Order> findNewByOperatorId(UUID id){
        return orderRepository.findNewOrdersByOperatorId(id)
                .orElseThrow(() -> new NotFoundException("Заявок у данного пользователя нет"));
    }

    @Transactional(readOnly = true)
    public List<Order> findForModeration(){
        return orderRepository.findForModeration()
                .orElseThrow(() -> new NotFoundException("Заявок для модерации нет"));
    }

    @Transactional(readOnly = true)
    public List<Order> findInPathForModerator(){
        return orderRepository.findInPath()
                .orElseThrow(() -> new NotFoundException("Заявок в пути нет"));
    }

    @Transactional
    public Order updateStatusOnModeration(UUID id){
        Order currentOrder = findById(id);
        currentOrder.setStatus(OrderStatus.Waiting_moderator);
        return orderRepository.save(currentOrder);
    }

    @Transactional
    public Order updateStatusOnWaitingOperator(UUID id){
        Order currentOrder = findById(id);
        currentOrder.setStatus(OrderStatus.Waiting_operator);
        return orderRepository.save(currentOrder);
    }

    @Transactional
    public Order updateStatusOnAccepted(UUID id){
        Order currentOrder = findById(id);
        currentOrder.setStatus(OrderStatus.Accepted);
        return orderRepository.save(currentOrder);
    }

    @Transactional
    public Order updateStatusOnPath(UUID id){
        Order currentOrder = findById(id);
        currentOrder.setStatus(OrderStatus.In_path);
        return orderRepository.save(currentOrder);
    }

    @Transactional
    public Order updateStatusOnFinished(UUID id){
        Order currentOrder = findById(id);
        currentOrder.setStatus(OrderStatus.Finished);
        return orderRepository.save(currentOrder);
    }

    @Transactional
    public Order updateStatusOnCancelled(UUID id){
        Order currentOrder = findById(id);
        currentOrder.setStatus(OrderStatus.Cancelled);
        return orderRepository.save(currentOrder);
    }
}
