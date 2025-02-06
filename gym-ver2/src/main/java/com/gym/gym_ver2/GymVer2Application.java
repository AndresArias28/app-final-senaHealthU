package com.gym.gym_ver2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@EntityScan(basePackages = "com.gym.gym_ver2.domain.model.entity")
@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.gym.gym_ver2.infrastructure.repository")
public class GymVer2Application {

	public static void main(String[] args) {
		SpringApplication.run(GymVer2Application.class, args);
	}

}
