package com.idforideas.pizzeria.user;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Nick Galán
 */
public interface UserService {

    User create(User user);

    User get(Long id);

    User get(String email);

    Optional<User> getAsOptional(Long id);

    Collection<User> list();

    User update(User user, User editedUser);

    void delete(Long id);
}
