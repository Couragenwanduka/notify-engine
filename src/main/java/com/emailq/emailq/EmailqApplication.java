package com.emailq.emailq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmailqApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailqApplication.class, args);
	}

}
