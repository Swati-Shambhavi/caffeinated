package com.caffeinated.caffeinatedpersonaservice;

import jdk.jfr.Enabled;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CaffeinatedPersonaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaffeinatedPersonaServiceApplication.class, args);
	}

}
