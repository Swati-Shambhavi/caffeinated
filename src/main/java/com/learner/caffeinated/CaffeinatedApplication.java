package com.learner.caffeinated;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CaffeinatedApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaffeinatedApplication.class, args);
	}

}
