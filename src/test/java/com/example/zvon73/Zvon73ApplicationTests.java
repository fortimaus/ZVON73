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
        TempleDto t2 = TempleDto.builder()
                .title("templeBell2")
                .description("desc")
                .address("ulsty")
                .phone("+800")
                .image(image)
                .build();
        TempleDto t3 = TempleDto.builder()
                .title("templeBell3")
                .description("desc")
                .address("ulsty")
                .phone("+800")
                .image(image)
                .build();

        Temple tD1 = templeService.create(t);
        Temple tD2 = templeService.create(t2);
        Temple tD3 = templeService.create(t3);

        BellTowerDto tw1 = BellTowerDto.builder()
                .title("tower bell1Bell")
                .templeId(tD1.getId().toString())
                .build();
        BellTowerDto tw2 = BellTowerDto.builder()
                .title("tower bell2Bell")
                .templeId(tD1.getId().toString())
                .build();

        BellTower btD1 = bellTowerService.create(tw1);

        BellTower btD2 = bellTowerService.create(tw2);

        BellDto b1 = BellDto.builder()
                .title("bell1")
                .manufacturer("man")
                .weight(100)
                .image(image)
                .sound(image)
                .bellTowerId(btD1.getId().toString())
                .build();
        BellDto b2 = BellDto.builder()
                .title("bell2")
                .manufacturer("man")
                .weight(100)
                .image(image)
                .sound(image)
                .bellTowerId(btD1.getId().toString())
                .build();
        BellDto b3 = BellDto.builder()
                .title("bell3")
                .manufacturer("man")
                .weight(100)
                .image(image)
                .sound(image)
                .bellTowerId(btD2.getId().toString())
                .build();

        Bell bellD1 = bellService.create(b1);
        Bell bellD2 = bellService.create(b2);
        Bell bellD3 = bellService.create(b3);

        User user1 = User.builder()
                .email("asd1")
                .password("asdghj")
                .role(Role.USER)
                .phone("=9890")
                .build();

        User user2 = User.builder()
                .email("asd2")
                .password("asdghj")
                .role(Role.USER)
                .phone("=98909")
                .build();

        User user3 = User.builder()
                .email("asd3")
                .password("asdghj")
                .role(Role.USER)
                .phone("=98980")
                .build();

        var us1 = userService.save(user1);
        var us2 = userService.save(user2);
        var us3 = userService.save(user3);

        userService.updateRole(new RoleRequest(
                us1.getId().toString(),
                new ArrayList<>(List.of(
                        tD1.getId().toString(),
                        tD2.getId().toString())),
                Role.RINGER.toString()
                )
        );
        userService.updateRole(new RoleRequest(
                        us2.getId().toString(),
                        new ArrayList<>(),
                        Role.RINGER.toString()
                )
        );
        userService.updateRole(new RoleRequest(
                        us2.getId().toString(),
                        new ArrayList<>(List.of(
                                tD1.getId().toString(),
                                tD2.getId().toString(),
                                tD3.getId().toString())),
                        Role.RINGER.toString()
                )
        );

        List<TempleDto> resT1 = templeService.findByUser(us1);
        List<TempleDto> resT2 = templeService.findByUser(us2);
        List<User> users = new ArrayList<>(List.of(us1,us2,us3));

        for(var user : users)
        {
            System.out.println(user.getEmail());
            for(var temple : templeService.findByUser(user))
            {
                System.out.println(temple.getTitle());
            }
        }

    }


//
//
//	//@Test
//	void CreateTemple() throws IOException {
//		byte[] image = Files.readAllBytes(Paths.get("C:\\JavaProjects\\Logo.jpg\\"));
//		Temple t1 = Temple.builder()
//				.title("temple1")
//				.description("desc")
//				.address("ulsty")
//				.phone("+800")
//				.image(image)
//				.build();
//		Temple res1 = templeService.create(t1);
//		Temple t2 = Temple.builder()
//				.title("temple2")
//				.description("desc")
//				.address("ulsty")
//				.phone("+800")
//				.image(image)
//				.build();
//		Temple res2 = templeService.create(t2);
//
//		Assertions.assertNotNull(res1.getId());
//		Assertions.assertNotNull(res2.getId());
//	}
//
//	//@Test
//	void CreateTower() throws IOException {
//		byte[] image = Files.readAllBytes(Paths.get("C:\\JavaProjects\\Logo.jpg\\"));
//		Temple t = Temple.builder()
//				.title("temple")
//				.description("desc")
//				.address("ulsty")
//				.phone("+800")
//				.image(image)
//				.build();
//		templeService.create(t);
//		BellTower tw1 = BellTower.builder()
//				.title("tower bell1")
//				.temple(t)
//				.build();
//		BellTower tw2 = BellTower.builder()
//				.title("tower bell2")
//				.temple(t)
//				.build();
//		BellTower res1 = bellTowerService.create(tw1);
//		BellTower res2 = bellTowerService.create(tw2);
//
//		Assertions.assertNotNull(res1.getId());
//		Assertions.assertNotNull(res2.getId());
//	}
//
//	//@Test
//	void CreateBell() throws IOException {
//		byte[] image = Files.readAllBytes(Paths.get("C:\\JavaProjects\\Logo.jpg\\"));
//		Temple t = Temple.builder()
//				.title("templeBell")
//				.description("desc")
//				.address("ulsty")
//				.phone("+800")
//				.image(image)
//				.build();
//		templeService.create(t);
//		BellTower tw1 = BellTower.builder()
//				.title("tower bell1Bell")
//				.temple(t)
//				.build();
//		BellTower tw2 = BellTower.builder()
//				.title("tower bell2Bell")
//				.temple(t)
//				.build();
//
//		bellTowerService.create(tw1);
//
//		bellTowerService.create(tw2);
//
//		Bell b1 = Bell.builder()
//				.title("bell1")
//				.manufacturer("man")
//				.weight(100)
//				.image(image)
//				.sound(image)
//				.bellTower(tw1)
//				.status(BellStatus.Accepted)
//				.build();
//		Bell b2 = Bell.builder()
//				.title("bell2")
//				.manufacturer("man")
//				.weight(100)
//				.image(image)
//				.sound(image)
//				.bellTower(tw1)
//				.status(BellStatus.Accepted)
//				.build();
//		Bell b3 = Bell.builder()
//				.title("bell3")
//				.manufacturer("man")
//				.weight(100)
//				.image(image)
//				.sound(image)
//				.bellTower(tw2)
//				.status(BellStatus.Accepted)
//				.build();
//
//		Bell res1 = bellService.create(b1);
//		Bell res2 = bellService.create(b2);
//		Bell res3 = bellService.create(b3);
//
//		Assertions.assertNotNull(res1.getId());
//		Assertions.assertNotNull(res2.getId());
//		Assertions.assertNotNull(res3.getId());
//	}
//
//	//@Test
//	void CreateOrder() throws IOException {
//		byte[] image = Files.readAllBytes(Paths.get("C:\\JavaProjects\\Logo.jpg\\"));
//		Temple t = Temple.builder()
//				.title("templeOrder1")
//				.description("desc")
//				.address("ulsty")
//				.phone("+800")
//				.image(image)
//				.build();
//		templeService.create(t);
//		Temple t2 = Temple.builder()
//				.title("templeOrder2")
//				.description("desc")
//				.address("ulsty")
//				.phone("+800")
//				.image(image)
//				.build();
//		templeService.create(t2);
//		BellTower tw1 = BellTower.builder()
//				.title("tower bell1T1")
//				.temple(t)
//				.build();
//		BellTower tw2 = BellTower.builder()
//				.title("tower bell2T2")
//				.temple(t2)
//				.build();
//
//		bellTowerService.create(tw1);
//
//		bellTowerService.create(tw2);
//
//		Bell b1 = Bell.builder()
//				.title("bell1")
//				.manufacturer("man")
//				.weight(100)
//				.image(image)
//				.sound(image)
//				.bellTower(tw1)
//				.status(BellStatus.Accepted)
//				.build();
//		Bell b2 = Bell.builder()
//				.title("bell2")
//				.manufacturer("man")
//				.weight(100)
//				.image(image)
//				.sound(image)
//				.bellTower(tw1)
//				.status(BellStatus.Accepted)
//				.build();
//		Bell b3 = Bell.builder()
//				.title("bell3")
//				.manufacturer("man")
//				.weight(100)
//				.image(image)
//				.sound(image)
//				.bellTower(tw2)
//				.status(BellStatus.Accepted)
//				.build();
//
//		bellService.create(b1);
//		bellService.create(b2);
//		bellService.create(b3);
//
//		Order o = Order.builder()
//				.temple_start(t)
//				.temple_end(t2)
//				.bellTower_start(tw1)
//				.bell(b1)
//				.date(new Date())
//				.status(OrderStatus.Accepted)
//				.build();
//		Order res = ordersService.create(o);
//		Assertions.assertNotNull(res.getId());
//
//	}
//
//	//@Test
//	void UpdateTemple() throws IOException {
//		byte[] image = Files.readAllBytes(Paths.get("C:\\JavaProjects\\Logo.jpg\\"));
//		Temple t1 = Temple.builder()
//				.title("templeUpdate")
//				.description("desc")
//				.address("ulsty")
//				.phone("+800")
//				.image(image)
//				.build();
//		templeService.create(t1);
//
//		t1.setTitle("Updated!!!");
//		Temple res1 = templeService.update(t1);
//		Assertions.assertNotNull(res1.getId());
//	}
//
//	// @Test
//	void updateAll() throws IOException {
//		byte[] image = Files.readAllBytes(Paths.get("C:\\JavaProjects\\Logo.jpg\\"));
//		Temple t = Temple.builder()
//				.title("templeOrder1")
//				.description("desc")
//				.address("ulsty")
//				.phone("+800")
//				.image(image)
//				.build();
//		templeService.create(t);
//		Temple t2 = Temple.builder()
//				.title("templeOrder2")
//				.description("desc")
//				.address("ulsty")
//				.phone("+800")
//				.image(image)
//				.build();
//		templeService.create(t2);
//		BellTower tw1 = BellTower.builder()
//				.title("tower bell1T1")
//				.temple(t)
//				.build();
//		BellTower tw2 = BellTower.builder()
//				.title("tower bell2T2")
//				.temple(t2)
//				.build();
//
//		BellTower twmid = bellTowerService.create(tw1);
//
//		bellTowerService.create(tw2);
//		twmid.setTitle("Updated!");
//		bellTowerService.update(twmid);
//		Bell b1 = Bell.builder()
//				.title("bell1")
//				.manufacturer("man")
//				.weight(100)
//				.image(image)
//				.sound(image)
//				.bellTower(twmid)
//				.status(BellStatus.Accepted)
//				.build();
//		Bell b2 = Bell.builder()
//				.title("bell2")
//				.manufacturer("man")
//				.weight(100)
//				.image(image)
//				.sound(image)
//				.bellTower(tw1)
//				.status(BellStatus.Accepted)
//				.build();
//		Bell b3 = Bell.builder()
//				.title("bell3")
//				.manufacturer("man")
//				.weight(100)
//				.image(image)
//				.sound(image)
//				.bellTower(tw2)
//				.status(BellStatus.Accepted)
//				.build();
//
//		Bell bellMid = bellService.create(b1);
//		bellService.create(b2);
//		bellService.create(b3);
//		bellService.updateStatusInPath(bellMid.getId());
//		Order o = Order.builder()
//				.temple_start(t)
//				.temple_end(t2)
//				.bellTower_start(tw1)
//				.bell(b1)
//				.date(new Date())
//				.status(OrderStatus.Accepted)
//				.build();
//		Order res = ordersService.create(o);
//		res.setStatus(OrderStatus.Waiting_moderator);
//		ordersService.updateStatusOnPath(res.getId());
//		Assertions.assertNotNull(res.getId());
//
//	}
//	//@Test
//	void findAll() throws IOException {
//		byte[] image = Files.readAllBytes(Paths.get("C:\\JavaProjects\\Logo.jpg\\"));
//		Temple t = Temple.builder()
//				.title("templeOrder1")
//				.description("desc")
//				.address("ulsty")
//				.phone("+800")
//				.image(image)
//				.build();
//		templeService.create(t);
//		Temple t2 = Temple.builder()
//				.title("templeOrder2")
//				.description("desc")
//				.address("ulsty")
//				.phone("+800")
//				.image(image)
//				.build();
//		templeService.create(t2);
//		BellTower tw1 = BellTower.builder()
//				.title("tower bell1T1")
//				.temple(t)
//				.build();
//		BellTower tw2 = BellTower.builder()
//				.title("tower bell2T2")
//				.temple(t2)
//				.build();
//
//		BellTower twmid = bellTowerService.create(tw1);
//
//		bellTowerService.create(tw2);
//		twmid.setTitle("Updated!");
//		bellTowerService.update(twmid);
//		Bell b1 = Bell.builder()
//				.title("bell1")
//				.manufacturer("man")
//				.weight(100)
//				.image(image)
//				.sound(image)
//				.bellTower(twmid)
//				.status(BellStatus.Accepted)
//				.build();
//		Bell b2 = Bell.builder()
//				.title("bell2")
//				.manufacturer("man")
//				.weight(100)
//				.image(image)
//				.sound(image)
//				.bellTower(tw1)
//				.status(BellStatus.Accepted)
//				.build();
//		Bell b3 = Bell.builder()
//				.title("bell3")
//				.manufacturer("man")
//				.weight(100)
//				.image(image)
//				.sound(image)
//				.bellTower(tw2)
//				.status(BellStatus.Accepted)
//				.build();
//
//		Bell bellMid = bellService.create(b1);
//		bellService.create(b2);
//		bellService.create(b3);
//		bellService.updateStatusInPath(bellMid.getId());
//		Order o = Order.builder()
//				.temple_start(t)
//				.temple_end(t2)
//				.bellTower_start(tw1)
//				.bell(b1)
//				.date(new Date())
//				.status(OrderStatus.Accepted)
//				.build();
//		Order res = ordersService.create(o);
//		res.setStatus(OrderStatus.Waiting_moderator);
//		ordersService.updateStatusOnPath(res.getId());
//		List<Temple> AllTemple = templeService.findAll();
//		List<BellTower> AllTower = bellTowerService.findAll();
//		List<Bell> AllBell = bellService.findAll();
//		System.out.println(AllTemple.size());
//		System.out.println(AllTemple.get(0).getTitle());
//		System.out.println(AllTemple.get(1).getTitle());
//
//		System.out.println(AllTower.size());
//		System.out.println(AllTower.get(0).getTitle());
//		System.out.println(AllTower.get(1).getTitle());
//
//		System.out.println(AllBell.size());
//		System.out.println(AllBell.get(0).getTitle());
//		System.out.println(AllBell.get(1).getTitle());
//		System.out.println(AllBell.get(2).getTitle());
//
//		Assertions.assertNotNull(res.getId());
//
//
//	}
//	//@Test
//	void findOne() throws IOException {
//		byte[] image = Files.readAllBytes(Paths.get("C:\\JavaProjects\\Logo.jpg\\"));
//		Temple t = Temple.builder()
//				.title("templeOrder1")
//				.description("desc")
//				.address("ulsty")
//				.phone("+800")
//				.image(image)
//				.build();
//		var tOne = templeService.create(t);
//		Temple t2 = Temple.builder()
//				.title("templeOrder2")
//				.description("desc")
//				.address("ulsty")
//				.phone("+800")
//				.image(image)
//				.build();
//		templeService.create(t2);
//		BellTower tw1 = BellTower.builder()
//				.title("tower bell1T1")
//				.temple(t)
//				.build();
//		BellTower tw2 = BellTower.builder()
//				.title("tower bell2T2")
//				.temple(t2)
//				.build();
//
//		BellTower btOne = bellTowerService.create(tw1);
//
//		bellTowerService.create(tw2);
//		btOne.setTitle("Updated!");
//		bellTowerService.update(btOne);
//		Bell b1 = Bell.builder()
//				.title("bell1")
//				.manufacturer("man")
//				.weight(100)
//				.image(image)
//				.sound(image)
//				.bellTower(btOne)
//				.status(BellStatus.Accepted)
//				.build();
//		Bell b2 = Bell.builder()
//				.title("bell2")
//				.manufacturer("man")
//				.weight(100)
//				.image(image)
//				.sound(image)
//				.bellTower(tw1)
//				.status(BellStatus.Accepted)
//				.build();
//		Bell b3 = Bell.builder()
//				.title("bell3")
//				.manufacturer("man")
//				.weight(100)
//				.image(image)
//				.sound(image)
//				.bellTower(tw2)
//				.status(BellStatus.Accepted)
//				.build();
//
//		Bell bellOne = bellService.create(b1);
//		bellService.create(b2);
//		bellService.create(b3);
//		bellService.updateStatusInPath(bellOne.getId());
//		Order o = Order.builder()
//				.temple_start(t)
//				.temple_end(t2)
//				.bellTower_start(tw1)
//				.bell(b1)
//				.date(new Date())
//				.status(OrderStatus.Accepted)
//				.build();
//		Order res = ordersService.create(o);
//		res.setStatus(OrderStatus.Waiting_moderator);
//		ordersService.updateStatusOnPath(res.getId());
//
//		System.out.println(templeService.findById(tOne.getId()).getTitle());
//		System.out.println(bellTowerService.findById(btOne.getId()).getTitle());
//		System.out.println(bellService.findById(bellOne.getId()).getTitle());
//		System.out.println(ordersService.findById(res.getId()).getTemple_start().getTitle());
//		Assertions.assertNotNull(res.getId());
//
//
//	}
//
//	//@Test
//	void GetMyTemple() throws IOException {
//
//		User user = User.builder()
//				.email("asd")
//				.password("asdghj")
//				.role(Role.USER)
//				.phone("=9890")
//				.build();
//		var us = userService.create(user);
//		byte[] image = Files.readAllBytes(Paths.get("C:\\JavaProjects\\Logo.jpg\\"));
//		Temple t1 = Temple.builder()
//				.title("temple1")
//				.description("desc")
//				.address("ulsty")
//				.phone("+800")
//				.user(us)
//				.image(image)
//				.build();
//		Temple res1 = templeService.create(t1);
//		Temple t2 = Temple.builder()
//				.title("temple2")
//				.description("desc")
//				.address("ulsty")
//				.phone("+800")
//				.image(image)
//				.build();
//		templeService.create(t2);
//
//		System.out.println(templeService.findByUser().getTitle());
//	}
//
//	//@Test
//	void GetBellsByTemple() throws IOException {
//
//		byte[] image = Files.readAllBytes(Paths.get("C:\\JavaProjects\\Logo.jpg\\"));
//		Temple t = Temple.builder()
//				.title("templeOrder1")
//				.description("desc")
//				.address("ulsty")
//				.phone("+800")
//				.image(image)
//				.build();
//		var tr = templeService.create(t);
//		Temple t2 = Temple.builder()
//				.title("templeOrder2")
//				.description("desc")
//				.address("ulsty")
//				.phone("+800")
//				.image(image)
//				.build();
//		templeService.create(t2);
//		BellTower tw1 = BellTower.builder()
//				.title("tower bell1T1")
//				.temple(t)
//				.build();
//		BellTower tw2 = BellTower.builder()
//				.title("tower bell2T2")
//				.temple(t)
//				.build();
//
//		BellTower twmid = bellTowerService.create(tw1);
//
//		bellTowerService.create(tw2);
//		twmid.setTitle("Updated!");
//		bellTowerService.update(twmid);
//		Bell b1 = Bell.builder()
//				.title("bell1")
//				.manufacturer("man")
//				.weight(100)
//				.image(image)
//				.sound(image)
//				.bellTower(twmid)
//				.status(BellStatus.Accepted)
//				.build();
//		Bell b2 = Bell.builder()
//				.title("bell2")
//				.manufacturer("man")
//				.weight(100)
//				.image(image)
//				.sound(image)
//				.bellTower(tw2)
//				.status(BellStatus.Accepted)
//				.build();
//		Bell b3 = Bell.builder()
//				.title("bell3")
//				.manufacturer("man")
//				.weight(100)
//				.image(image)
//				.sound(image)
//				.bellTower(tw2)
//				.status(BellStatus.Accepted)
//				.build();
//
//		Bell bellMid = bellService.create(b1);
//		bellService.create(b2);
//		bellService.create(b3);
//		bellService.updateStatusInPath(bellMid.getId());
//
//		System.out.println(bellTowerService.findAllByTemple(tr.getId()).get(0).getTitle());
//		System.out.println(bellTowerService.findAllByTemple(tr.getId()).get(1).getTitle());
//	}
//
//
//
//	@Test
//	void findOrders() throws IOException {
//		User user = User.builder()
//				.email("asd")
//				.password("asdghj")
//				.role(Role.USER)
//				.phone("=9890")
//				.build();
//		var us = userService.create(user);
//		byte[] image = Files.readAllBytes(Paths.get("C:\\JavaProjects\\Logo.jpg\\"));
//		Temple t = Temple.builder()
//				.title("templeOrder1")
//				.description("desc")
//				.address("ulsty")
//				.phone("+800")
//				.image(image)
//				.build();
//		templeService.create(t);
//		Temple t2 = Temple.builder()
//				.title("templeOrder2")
//				.description("desc")
//				.address("ulsty")
//				.phone("+800")
//				.user(us)
//				.image(image)
//				.build();
//		templeService.create(t2);
//		BellTower tw1 = BellTower.builder()
//				.title("tower bell1T1")
//				.temple(t)
//				.build();
//		BellTower tw2 = BellTower.builder()
//				.title("tower bell2T2")
//				.temple(t2)
//				.build();
//
//		BellTower twmid = bellTowerService.create(tw1);
//
//		bellTowerService.create(tw2);
//		twmid.setTitle("Updated!");
//		bellTowerService.update(twmid);
//		Bell b1 = Bell.builder()
//				.title("bell1")
//				.manufacturer("man")
//				.weight(100)
//				.image(image)
//				.sound(image)
//				.bellTower(twmid)
//				.status(BellStatus.Accepted)
//				.build();
//		Bell b2 = Bell.builder()
//				.title("bell2")
//				.manufacturer("man")
//				.weight(100)
//				.image(image)
//				.sound(image)
//				.bellTower(tw1)
//				.status(BellStatus.Accepted)
//				.build();
//		Bell b3 = Bell.builder()
//				.title("bell3")
//				.manufacturer("man")
//				.weight(100)
//				.image(image)
//				.sound(image)
//				.bellTower(tw2)
//				.status(BellStatus.Accepted)
//				.build();
//
//		Bell bellMid = bellService.create(b1);
//		bellService.create(b2);
//		bellService.create(b3);
//		bellService.updateStatusInPath(bellMid.getId());
//		Order o = Order.builder()
//				.temple_start(t)
//				.temple_end(t2)
//				.bellTower_start(tw1)
//				.bell(b1)
//				.date(new Date())
//				.status(OrderStatus.Waiting_operator)
//				.build();
//		Order res = ordersService.create(o);
//		res.setStatus(OrderStatus.Waiting_moderator);
//
//		System.out.println(ordersService.findNewByOperatorId(us.getId()).get(0).getId());
//
//		Assertions.assertNotNull(res.getId());
//
//
//	}
}
