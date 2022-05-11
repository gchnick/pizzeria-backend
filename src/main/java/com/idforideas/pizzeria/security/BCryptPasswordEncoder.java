package com.idforideas.pizzeria.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nick Galán
 */
@Configuration
public class BCryptPasswordEncoder {

    @Bean
	public org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder passwordEncoder() {
		return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
	}
    
}
