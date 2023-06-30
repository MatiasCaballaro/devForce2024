package com.devforce.liceman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class LicemanApplication {

	public static void main(String[] args) {
		SpringApplication.run(LicemanApplication.class, args);
	}

}
