package com.abhi.webflux.mynolapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableMongoAuditing
@EnableReactiveMongoRepositories
@SpringBootApplication
public class MyNolAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyNolAppApplication.class, args);
	}

}

