package com.example.zvon73;

import com.example.zvon73.DTO.BellDto;
import com.example.zvon73.DTO.BellTowerDto;
import com.example.zvon73.DTO.TempleDto;
import com.example.zvon73.controller.domain.RoleRequest;
import com.example.zvon73.entity.*;
import com.example.zvon73.entity.Enums.BellStatus;
import com.example.zvon73.entity.Enums.OrderStatus;
import com.example.zvon73.entity.Enums.Role;
import com.example.zvon73.repository.TempleRepository;
import com.example.zvon73.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class ZvonTests {

	@Autowired
	private TempleService templeService;

	@Autowired
	private BellTowerService bellTowerService;

	@Autowired
	private BellService bellService;

	@Autowired
	private OrdersService ordersService;

	@Autowired
	private UserService userService;


    @Test
    void CreateNotice() throws IOException {
        byte[] image = Files.readAllBytes(Paths.get("C:\\JavaProjects\\Logo.jpg\\"));


        TempleDto t = TempleDto.builder()
                .title("templeBell1")
                .description("desc")
                .address("ulsty")
                .phone("+800")
                .image(image)
                .build();


        TempleDto tD1 = templeService.create(t);
        System.out.println(new TempleDto(templeService.findById(UUID.fromString(tD1.getId()))));




    }


}
