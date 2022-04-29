package com.idforideas.pizzeria.appuser;

import java.util.Collection;
import java.util.Optional;

import com.idforideas.pizzeria.exception.BadRequestException;
import com.idforideas.pizzeria.exception.NotFoundException;

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
        String msg = "Email not found in the database";
        AppUser user = userRepo.findByEmailIgnoreCase(email).orElseThrow(() -> new UsernameNotFoundException(msg));
       
        log.info("Email found in the database: {}", email);
        return user;
    }

    @Override
    public AppUser create(AppUser user) {
        log.info("Saving new app user {}",user.getName());
        valid(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        return userRepo.save(user);
    }

    @Override
    public AppUser get(Long id) {
        log.info("Fetching user by id: {}",id);
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException("Id user not exists"));
    }

    @Override
    public AppUser get(String email) {
        log.info("Fetching user by email: {}",email);
        return userRepo.findByEmailIgnoreCase(email).orElseThrow(() -> new NotFoundException("User email not exists"));
    }

    @Override
    public Optional<AppUser> getAsOptional(Long id) {
        log.info("Fetching user by id: {}",id);
        return userRepo.findById(id);
    }

    @Override
    public Collection<AppUser> list() {
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

	@Override
	public void valid(AppUser user) {
		boolean isPresent = userRepo.findByEmailIgnoreCase(user.getEmail()).isPresent();
        if(isPresent) { 
            log.error("{} user email is already registered", user.getEmail());
            throw new BadRequestException("The user email is already registered");
        }
		
	} 
}
