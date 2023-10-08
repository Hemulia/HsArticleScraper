package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;


@SpringBootApplication
@EnableScheduling
public class HsJavaScraperApplication {
	public static void main(String[] args) {
		SpringApplication.run(HsJavaScraperApplication.class, args);
	}

}

