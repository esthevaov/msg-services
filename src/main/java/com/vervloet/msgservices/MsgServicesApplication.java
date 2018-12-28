package com.vervloet.msgservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MsgServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsgServicesApplication.class, args);
	}

}

