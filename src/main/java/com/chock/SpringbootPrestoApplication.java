package com.chock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringbootPrestoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootPrestoApplication.class, args);
	}
}
