package com.idforideas.pizzeria.security.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.idforideas.pizzeria.auth.UserDetailsService;
import com.idforideas.pizzeria.security.EmailPasswordAuthenticationFilter;
import com.idforideas.pizzeria.security.OncePerRequestAuthorizationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

/**
 * @author Nick Galán
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityEmailPassword extends WebSecurityConfigurerAdapter {
    
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final String FILTER_PROCESSES_URL = "/api/v1/auth/token";

   @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        EmailPasswordAuthenticationFilter authenticationFilter = new EmailPasswordAuthenticationFilter(authenticationManagerBean());
        authenticationFilter.setFilterProcessesUrl(FILTER_PROCESSES_URL);
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.addFilter(authenticationFilter);
        http.addFilterBefore(new OncePerRequestAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
