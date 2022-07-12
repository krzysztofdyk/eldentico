package com.example.eldentico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class EldenticoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EldenticoApplication.class, args);
	}

}
