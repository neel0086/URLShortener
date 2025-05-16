package com.service.URLAnalytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class UrlAnalyticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlAnalyticsApplication.class, args);
	}

}
