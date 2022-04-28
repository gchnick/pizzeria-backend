package com.idforideas.pizzeria.appuser;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Nick Galán
 */
public interface AppUserService {

    AppUser create(AppUser user);

    Optional<AppUser> get(Long id);

    Optional<AppUser> get(String email);

    Collection<AppUser> getUsers();

    AppUser update(AppUser user);

    void delete(Long id);
}
