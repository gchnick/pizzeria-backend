package com.idforideas.pizzeria.appuser;

import java.util.List;

/**
 * @author Nick Galan
 * @version 1.0
 * @since 2/28/2022
 */
public interface AppUserService {
    AppUser saveUser(AppUser appUser);
    AppUser getUser(String email);
    List<AppUser> getUsers();
}
