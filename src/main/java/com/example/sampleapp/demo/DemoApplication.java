package com.example.sampleapp.demo;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity(debug = true)
public class DemoApplication {
	public static void main(String[] args) {

		// SpringApplication.run(DemoApplication.class, args);
		SpringApplication application = new SpringApplication(DemoApplication.class);
		application.setAddCommandLineProperties(true);
		application.setBannerMode(Banner.Mode.OFF);
		application.setLogStartupInfo(true);
		application.run(args);
	}

}
