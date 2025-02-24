package com.agromercantil.gestaofrota;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GestaoFrotaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoFrotaApplication.class, args);
	}

}