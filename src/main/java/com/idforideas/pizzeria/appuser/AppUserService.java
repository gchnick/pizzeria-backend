package com.idforideas.pizzeria.appuser;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Nick Galán
 */
public interface AppUserService {

    AppUser create(AppUser user);

    AppUser get(Long id);

    AppUser get(String email);

    Optional<AppUser> getAsOptional(Long id);

    Collection<AppUser> list();

    AppUser update(AppUser user);

    void delete(Long id);

    void valid(AppUser user);
}
