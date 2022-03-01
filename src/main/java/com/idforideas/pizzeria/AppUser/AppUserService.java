package com.idforideas.pizzeria.AppUser;

import java.util.List;

public interface AppUserService {
    AppUser saveUser(AppUser appUser);
    AppUser getUser(String email);
    List<AppUser> getUsers();
}
