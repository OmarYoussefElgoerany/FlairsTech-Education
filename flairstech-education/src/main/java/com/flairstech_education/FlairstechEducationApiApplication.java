package com.flairstech_education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FlairstechEducationApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlairstechEducationApiApplication.class, args);
	}

}
