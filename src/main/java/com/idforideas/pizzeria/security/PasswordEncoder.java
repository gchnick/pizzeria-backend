package com.idforideas.pizzeria.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Nick Galan
 * @version 1.0
 * @since 3/1/2022
 */
@Configuration
public class PasswordEncoder {

    @Bean
	public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
}
