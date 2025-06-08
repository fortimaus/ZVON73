package com.example.zvon73.service;

import com.example.zvon73.DTO.OrderDto;
import com.example.zvon73.controller.domain.OrderRequest;
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
import java.util.stream.Collectors;

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
        Temple templeStart = templeService.findById(UUID.fromString(order.getTemple_start()));
        if(templeStart.getRingers().contains(curUser)) {
            Temple templeEnd = templeService.findById(UUID.fromString(order.getTemple_end()));
            BellTower bellTowerStart = bellTowerService.findById(UUID.fromString(order.getBellTower_start()));
            Bell bell = bellService.findById(UUID.fromString(order.getBell()));
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
    public List<OrderDto> findOldByOperatorId(UUID id){
        return orderRepository.findOldByOperatorId(id).stream().map(OrderDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderDto> findNewByOperatorId(UUID id){
        return orderRepository.findNewOrdersByOperatorId(id).stream().map(OrderDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderDto> findForModeration(){
        return orderRepository.findForModeration().stream().map(OrderDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderDto> findInPathForModerator(){
        return orderRepository.findInPath().stream().map(OrderDto::new).collect(Collectors.toList());
    }

    @Transactional
    public Order updateStatusOnModeration(OrderRequest request){
        User curUser = userService.getCurrentUser();
        Order currentOrder = findById(UUID.fromString(request.getOrder()));
        if (currentOrder.getTemple_end().getRingers().contains(curUser)) {
            currentOrder.setStatus(OrderStatus.Waiting_moderator);
            return orderRepository.save(currentOrder);

        }
        else
            throw new RuntimeException("Не имеешь права, хулиган");
    }

    public Order updateBellTowerEnd(OrderRequest request){
        Order currentOrder = findById(UUID.fromString(request.getOrder()));
        BellTower bellTower = bellTowerService.findById(UUID.fromString(request.getBellTower()));
        currentOrder.setBellTower_end(bellTower);
        return orderRepository.save(currentOrder);
    }

    @Transactional
    public Order updateStatusOnPath(OrderRequest request){
        Order currentOrder = findById(UUID.fromString(request.getOrder()));
        currentOrder.setStatus(OrderStatus.In_path);
        return orderRepository.save(currentOrder);
    }

    @Transactional
    public Order updateStatusOnFinished(OrderRequest request){
        Order currentOrder = findById(UUID.fromString(request.getOrder()));
        currentOrder.setStatus(OrderStatus.Finished);
        currentOrder.setDate(new Date());
        return orderRepository.save(currentOrder);
    }

    @Transactional
    public Order updateStatusOnCancelled(OrderRequest request){
        Order currentOrder = findById(UUID.fromString(request.getOrder()));
        currentOrder.setStatus(OrderStatus.Cancelled);
        return orderRepository.save(currentOrder);
    }
}
