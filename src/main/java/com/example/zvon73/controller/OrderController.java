package com.example.zvon73.controller;

import com.example.zvon73.DTO.OrderDto;
import com.example.zvon73.controller.domain.OrderRequest;
import com.example.zvon73.service.OrdersService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.zvon73.config.SecurityConfig.SECURITY_CONFIG_NAME;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
public class OrderController {
    private final OrdersService ordersService;

    @GetMapping
    public ResponseEntity<OrderDto> get(@RequestParam("id") String id){
        return ResponseEntity.ok(new OrderDto(ordersService.findById(UUID.fromString(id))));
    }
    @GetMapping("/operator/old")
    public ResponseEntity<List<OrderDto>> getListByOldOperator(@RequestParam("id") String id){
        return ResponseEntity.ok(ordersService.findOldByOperatorId(UUID.fromString(id)));
    }
    @GetMapping("/operator/new")
    public ResponseEntity<List<OrderDto>> getListByNewOperator(@RequestParam("id") String id){
        return ResponseEntity.ok(ordersService.findNewByOperatorId(UUID.fromString(id)));
    }
    @GetMapping("/list/in-path")
    public ResponseEntity<List<OrderDto>> getListInPath(){
        return ResponseEntity.ok(ordersService.findInPathForModerator());
    }
    @GetMapping("/moderation/list")
    public ResponseEntity<List<OrderDto>> getListForModeration(){
        return ResponseEntity.ok(ordersService.findForModeration());
    }
    @PutMapping("/moderation/update-status")
    public ResponseEntity<OrderDto> updateStatusOnModeration(@RequestBody OrderRequest orderRequest){
        return  ResponseEntity.ok(new OrderDto(ordersService.updateStatusOnModeration(orderRequest)));
    }
    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto){
        return ResponseEntity.ok(new OrderDto(ordersService.create(orderDto)));
    }
    @PutMapping("/update-bell-tower")
    public ResponseEntity<OrderDto> updateBellTowerEnd(@RequestBody OrderRequest orderRequest){
        return  ResponseEntity.ok(new OrderDto(ordersService.updateBellTowerEnd(orderRequest)));
    }
    @PutMapping("/update-status/path")
    public ResponseEntity<OrderDto> updateStatusOnPath(@RequestBody OrderRequest orderRequest){
        return  ResponseEntity.ok(new OrderDto(ordersService.updateStatusOnPath(orderRequest)));
    }
    @PutMapping("/update-status/finished")
    public ResponseEntity<OrderDto> updateStatusOnFinished(@RequestBody OrderRequest orderRequest){
        return  ResponseEntity.ok(new OrderDto(ordersService.updateStatusOnFinished(orderRequest)));
    }
    @PutMapping("/update-status/cancelled")
    public ResponseEntity<OrderDto> updateStatusOnCancelled(@RequestBody OrderRequest orderRequest){
        return  ResponseEntity.ok(new OrderDto(ordersService.updateStatusOnCancelled(orderRequest)));
    }
}
