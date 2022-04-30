package com.idforideas.pizzeria.user;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class UserObjectMother {

    public static User getNewUser () {
        User user = new User("John Doe", "johndoe@mail.com", "uS3rp@ss", UserRole.ROLE_ADMIN);
        return user;
    }

    public static User getUpdateUser () {
        User user = new User("Doe John", "doejohn@mail.com", "uS3rp@ss", UserRole.ROLE_ADMIN);
        return user;
    }
    
    public static User getUser001 () {
        User user = new User("Docs Test", "test@mail.com", "uS3rp@ss", UserRole.ROLE_ADMIN);
        user.setId(1L);
        return user;
    }

    public static String getNewUserAsJson() throws Exception {
        return new ObjectMapper().writeValueAsString(getNewUser());
    }

    public static String getUser001AsJson() throws Exception {
        return new ObjectMapper().writeValueAsString(getUser001());
    }

    public static String getUpdateUserAsJson() throws Exception {
        return new ObjectMapper().writeValueAsString(getUpdateUser());
    }
}
