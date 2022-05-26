package com.codeages.corporate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CorporateApplication {

	public static void main(String[] args) {
		SpringApplication.run(CorporateApplication.class, args);
	}

}
