package com.example.spendora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication

public class SpendoraApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpendoraApplication.class, args);
	}

}
