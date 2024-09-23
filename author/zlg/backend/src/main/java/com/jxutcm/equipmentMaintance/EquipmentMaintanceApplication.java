package com.jxutcm.equipmentMaintance;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.jxutcm.equipmentMaintance.Dao")
public class EquipmentMaintanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EquipmentMaintanceApplication.class, args);
	}

}
