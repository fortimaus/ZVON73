package com.example.zvon73.service;

import com.example.zvon73.DTO.OrderDto;
import com.example.zvon73.entity.*;
import com.example.zvon73.entity.Enums.BellStatus;
import com.example.zvon73.entity.Enums.OrderStatus;
import com.example.zvon73.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final BellService bellService;
    private  final TempleService templeService;
    private final BellTowerService bellTowerService;

    @Transactional
    public Order create(OrderDto order)
    {
        User curUser = userService.getCurrentUser();
        Temple templeStart = templeService.findById(order.getTemple_start());
        if(curUser.getId() == templeStart.getUser().getId()) {
            Temple templeEnd = templeService.findById(order.getTemple_end());
            BellTower bellTowerStart = bellTowerService.findById(order.getBellTower_start());
            Bell bell = bellService.findById(order.getBell());
            Order newOrder = Order.builder()
                    .temple_start(templeStart)
                    .temple_end(templeEnd)
                    .bellTower_start(bellTowerStart)
                    .bellTower_end(null)
                    .bell(bell)
                    .status(OrderStatus.Waiting_operator)
                    .build();
            return orderRepository.save(newOrder);
        }
        else
            throw new RuntimeException("Не имеешь права, хулиган");
    }

    @Transactional(readOnly = true)
    public Order findById(UUID id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Заявки с данным id не найден"));
    }

    @Transactional(readOnly = true)
    public List<Order> findOldByOperatorId(UUID id){
        return orderRepository.findOldByOperatorId(id);
    }

    @Transactional(readOnly = true)
    public List<Order> findNewByOperatorId(UUID id){
        return orderRepository.findNewOrdersByOperatorId(id);
    }

    @Transactional(readOnly = true)
    public List<Order> findForModeration(){
        return orderRepository.findForModeration();
    }

    @Transactional(readOnly = true)
    public List<Order> findInPathForModerator(){
        return orderRepository.findInPath();
    }

    @Transactional
    public Order updateStatusOnModeration(UUID id){
        User curUser = userService.getCurrentUser();
        Order currentOrder = findById(id);
        if (curUser.getId() == currentOrder.getTemple_end().getUser().getId()) {
            currentOrder.setStatus(OrderStatus.Waiting_moderator);
            return orderRepository.save(currentOrder);

        }
        else
            throw new RuntimeException("Не имеешь права, хулиган");
    }

    public Order updateBellTowerEnd(UUID idOrder ,UUID idTower){
        Order currentOrder = findById(idOrder);
        BellTower bellTower = bellTowerService.findById(idTower);
        currentOrder.setBellTower_end(bellTower);
        return orderRepository.save(currentOrder);
    }

    @Transactional
    public Order updateStatusOnPath(UUID id){
        Order currentOrder = findById(id);
        currentOrder.setStatus(OrderStatus.In_path);
        bellService.updateStatusInPath(currentOrder.getBell().getId());
        return orderRepository.save(currentOrder);
    }

    @Transactional
    public Order updateStatusOnFinished(UUID id){
        Order currentOrder = findById(id);
        currentOrder.setStatus(OrderStatus.Finished);
        currentOrder.setDate(new Date());
        bellService.updateStatusAccepted(currentOrder.getBell().getId());
        return orderRepository.save(currentOrder);
    }

    @Transactional
    public Order updateStatusOnCancelled(UUID id){
        Order currentOrder = findById(id);
        currentOrder.setStatus(OrderStatus.Cancelled);
        bellService.updateStatusAccepted(currentOrder.getBell().getId());
        return orderRepository.save(currentOrder);
    }
}
