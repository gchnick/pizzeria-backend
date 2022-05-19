package com.idforideas.pizzeria.user;

import java.util.Collection;
import java.util.Optional;

import com.idforideas.pizzeria.exception.BadRequestException;
import com.idforideas.pizzeria.exception.NotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nick Galán
 */
@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceJpa implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User create(User user) {
        log.info("Saving new app user {}",user.getFullName());
        valid(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        return userRepo.save(user);
    }

    @Override
    public User get(Long id) {
        log.info("Fetching user by id: {}",id);
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException("Id user not exists"));
    }

    @Override
    public User get(String email) {
        log.info("Fetching user by email: {}",email);
        return userRepo.findByEmailIgnoreCase(email).orElseThrow(() -> new NotFoundException("User email not exists"));
    }

    @Override
    public Optional<User> getAsOptional(Long id) {
        log.info("Fetching user by id: {}",id);
        return userRepo.findById(id);
    }

    @Override
    public Collection<User> list() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }

    @Override
    public User update(User user, User editedUser) {
        log.info("Updating this app user {}",user.getFullName());
        valid(editedUser);
        user.setFullName(editedUser.getFullName());
        user.setEmail(editedUser.getEmail());
        user.setPassword(passwordEncoder.encode(editedUser.getPassword()));
        user.setRole(editedUser.getRole());
        return userRepo.save(user);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting user by id {}",id);
        if(userRepo.existsById(id)) {
            userRepo.deleteById(id);
        }        
    }

	private void valid(User user) {
		boolean isPresent = userRepo.findByEmailIgnoreCase(user.getEmail()).isPresent();
        if(isPresent) { 
            log.error("{} user email is already registered", user.getEmail());
            throw new BadRequestException("The user email is already registered");
        }	
	} 
}