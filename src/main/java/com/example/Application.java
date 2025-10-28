package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Component
	public static class ConfigLogger implements CommandLineRunner {

		@Value("${spring.datasource.url:URL_NOT_FOUND}")
		private String dbUrl;

		@Value("${spring.profiles.active:NO_PROFILE_SET}")
		private String activeProfile;

		@Override
		public void run(String... args) throws Exception {
			System.out.println("--- CONFIG CHECK START ---");
			System.out.println("Active Profile: " + activeProfile);
			System.out.println("DB URL: " + dbUrl);
			System.out.println("--- CONFIG CHECK END ---");
		}
	}

}
