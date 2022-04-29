package com.idforideas.pizzeria.appuser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nick Galán
 */
public interface AppUserRepo extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmailIgnoreCase(String email);
}
