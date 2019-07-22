package com.ngoum.cisc3810.registrar.registrarserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class RegistrarServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistrarServerApplication.class, args);
	}

}