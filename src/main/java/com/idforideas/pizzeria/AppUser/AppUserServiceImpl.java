package com.idforideas.pizzeria.AppUser;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepo userRepo;

    @Override
    public AppUser saveUser(AppUser appUser) {
        log.info("Saving new app user");
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
