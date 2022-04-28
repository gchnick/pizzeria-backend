package com.idforideas.pizzeria.appuser;

import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nick Galán
 */
@Service @RequiredArgsConstructor @Transactional @Slf4j
public class AppUserServiceImpl implements AppUserService, UserDetailsService {
    private final AppUserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepo.findByEmail(email).orElseThrow();
        if(user == null) {
            String msg = "Email not found in the database";
            log.error(msg);
            throw new UsernameNotFoundException(msg);
        } else {
            log.info("Email found in the database: {}", email);
        }
        return user;
    }

    @Override
    public AppUser create(AppUser user) {
        log.info("Saving new app user {}",user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        AppUser app = null;
        try {
            app = userRepo.save(user);
            
        }catch(Exception e) {
            
            e.printStackTrace();
        }
        return app;
    }

    @Override
    public Optional<AppUser> get(Long id) {
        log.info("Fetching user by id: {}",id);
        return userRepo.findById(id);
    }

    @Override
    public Optional<AppUser> get(String email) {
        log.info("Fetching user by email: {}",email);
        return userRepo.findByEmail(email);
    }

    @Override
    public Collection<AppUser> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }

    @Override
    public AppUser update(AppUser user) {
        log.info("Updating this app user {}",user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting user by id {}",id);
        if(userRepo.existsById(id)) {
            userRepo.deleteById(id);
        }        
    } 
}
