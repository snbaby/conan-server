package com.conan.console.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.conan.console.server.mapper")
public class ConanServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConanServerApplication.class, args);
	}
}
