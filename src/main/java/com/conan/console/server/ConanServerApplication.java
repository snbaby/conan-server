package com.conan.console.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
public class ConanServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConanServerApplication.class, args);
	}
}
