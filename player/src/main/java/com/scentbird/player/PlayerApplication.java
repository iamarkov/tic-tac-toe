package com.scentbird.player;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.scentbird.common",
		"com.scentbird.player"
})
public class PlayerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlayerApplication.class, args);
	}

}
