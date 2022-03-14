package com.idforideas.pizzeria.appuser;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nick Galan
 * @version 1.0
 * @since 2/28/2022
 */
@Service @RequiredArgsConstructor @Transactional @Slf4j
public class AppUserServiceImpl implements AppUserService, UserDetailsService {
    private final AppUserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = this.userRepo.findByEmail(email);
        if(user == null) {
            String msg = "Email not found in the database";
            log.error(msg);
            throw new UsernameNotFoundException(msg);
        } else {
            log.info("Email found in the database: {}", email);
        }
        
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getAuthorities());
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        log.info("Saving new app user");
        appUser.setPassword(this.passwordEncoder.encode(appUser.getPassword()));
        return this.userRepo.save(appUser);
    }

    @Override
    public AppUser getUser(String email) {
        log.info("Fetching user by email: {}",email);
        return this.userRepo.findByEmail(email);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Ftching all users");
        return this.userRepo.findAll();
    } 
}
