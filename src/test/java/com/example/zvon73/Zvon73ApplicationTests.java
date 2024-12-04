package com.example.zvon73;

import com.example.zvon73.entity.BellTower;
import com.example.zvon73.entity.Temple;
import com.example.zvon73.repository.TempleRepository;
import com.example.zvon73.service.BellService;
import com.example.zvon73.service.BellTowerService;
import com.example.zvon73.service.OrdersService;
import com.example.zvon73.service.TempleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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


	@Test
	void CreateTemple() {
		Temple t1 = Temple.builder()
				.title("temple1")
				.description("desc1")
				.address("ad1")
				.phone("+7901")
				.image(new byte[]{})
				.build();
		var res = templeService.create(t1);
		System.out.println(templeService.findAll());
	}

}
