package com.codeages.javaskeletonproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JavaSkeletonProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaSkeletonProjectApplication.class, args);
	}

}
