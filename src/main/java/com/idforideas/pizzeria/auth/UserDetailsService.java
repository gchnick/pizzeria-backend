package com.idforideas.pizzeria.auth;

import javax.transaction.Transactional;

import com.idforideas.pizzeria.user.User;
import com.idforideas.pizzeria.user.UserRepo;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String msg = "Email not found in the database";
        User user = userRepo.findByEmailIgnoreCase(email).orElseThrow(() -> new UsernameNotFoundException(msg));
        log.info("Email found in the database: {}", email);
       
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true, true, true, user.getAuthorities());
    }
}
