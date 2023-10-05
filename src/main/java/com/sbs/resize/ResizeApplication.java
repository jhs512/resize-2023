package com.sbs.resize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ResizeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResizeApplication.class, args);
	}

}
