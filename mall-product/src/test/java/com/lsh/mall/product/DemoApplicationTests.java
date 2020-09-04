package com.lsh.mall.product;

import com.lsh.mall.product.entity.BrandEntity;
import com.lsh.mall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	BrandService brandService;

	@Test
	void contextLoads() {
	}

	@Test
	void saveTest(){
		BrandEntity brandEntity = new BrandEntity();
		brandEntity.setName("华为");
		brandEntity.setDescript("华为还行");
		brandService.save(brandEntity);
		System.out.println("保存成功");
	}
}
