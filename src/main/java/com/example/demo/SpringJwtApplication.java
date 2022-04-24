package com.example.demo;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.App_User;
import com.example.demo.model.Role;
import com.example.demo.service.UserService;


@SpringBootApplication
public class SpringJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJwtApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null , "ADMIN"));
			userService.saveRole(new Role(null , "SUPER_ADMIN"));
			userService.saveRole(new Role(null , "USER"));
			
			userService.saveUser(new App_User(null , "yousef yaser" , "yousef" , "1234" , new ArrayList<>()));
			userService.saveUser(new App_User(null , "khaled waleed" , "khaled" , "1234" , new ArrayList<>()));
			userService.saveUser(new App_User(null , "mohammed khaleel" , "mohammed" , "1234" , new ArrayList<>()));
			
			userService.addRoleToUser("yousef", "ADMIN");
			userService.addRoleToUser("yousef", "SUPER_ADMIN");
			userService.addRoleToUser("khaled", "USER");
			userService.addRoleToUser("mohammed", "USER");
		};
	}
}
