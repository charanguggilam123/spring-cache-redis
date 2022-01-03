package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
//@EnableRedisRepositories
@EnableCaching
//@EnableJpaRepositories
public class JpademoRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpademoRedisApplication.class, args);
	}

}
