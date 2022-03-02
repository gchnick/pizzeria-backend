package com.idforideas.pizzeria;

import com.idforideas.pizzeria.AppUser.AppUser;
import com.idforideas.pizzeria.AppUser.AppUserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PizzeriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzeriaApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run (AppUserService userService) {
		return args -> {
			userService.saveUser(new AppUser(null, "John Doe", "johndoe@mail.com", "userpass", "ROLE_ADMIN"));
			userService.saveUser(new AppUser(null, "Don Remolo", "pizzeriadonremolo@gmail.com", "userpass", "ROLE_ADMIN"));
		};

	}

}
