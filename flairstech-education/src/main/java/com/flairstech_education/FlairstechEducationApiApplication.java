package com.flairstech_education;

import com.flairstech_education.role.Role;
import com.flairstech_education.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FlairstechEducationApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlairstechEducationApiApplication.class, args);
	}
//	@Bean
//	public CommandLineRunner runner(RoleRepository roleRepository) {
//		return args -> {
//			if (roleRepository.findByName("USER").isEmpty()) {
//				roleRepository.save(Role.builder().name("USER").build());
//			}
//		};
//	}

}
