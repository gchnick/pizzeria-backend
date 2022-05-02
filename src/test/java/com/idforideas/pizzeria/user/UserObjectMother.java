package com.idforideas.pizzeria.user;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class UserObjectMother {

    public static User getNewUser001 () {
        User user = new User("Docs Test", "test@mail.com", "uS3rp@ss", UserRole.ROLE_ADMIN);
        return user;
    }

    public static User getNewUser002 () {
        User user = new User("Don Remolo", "donremolo@pizzeria.com", "uS3rp@ss", UserRole.ROLE_ADMIN);
        return user;
    }

    public static User getNewUser003 () {
        User user = new User("Doe John", "doejohn@mail.com", "uS3rp@ss", UserRole.ROLE_ADMIN);
        return user;
    }
    
    public static User getUser001 () {
        User user = new User("Docs Test", "test@mail.com", "uS3rp@ss", UserRole.ROLE_ADMIN);
        user.setId(1L);
        return user;
    }

    public static User getUser002 () {
        User user = new User("Don Remolo", "donremolo@pizzeria.com", "uS3rp@ss", UserRole.ROLE_ADMIN);
        user.setId(2L);
        return user;
    }

    public static User getUser003 () {
        User user = new User("Doe John", "doejohn@mail.com", "uS3rp@ss", UserRole.ROLE_ADMIN);
        user.setId(3L);
        return user;
    }

    public static String getNewUser001AsJson() throws Exception {
        return new ObjectMapper().writeValueAsString(getNewUser001());
    }

    public static String getNewUser002AsJson() throws Exception {
        return new ObjectMapper().writeValueAsString(getNewUser002());
    }

    public static String getNewUser003AsJson() throws Exception {
        return new ObjectMapper().writeValueAsString(getNewUser003());
    }

    public static String getUser001AsJson() throws Exception {
        return new ObjectMapper().writeValueAsString(getUser001());
    }

    public static String getUser002AsJson() throws Exception {
        return new ObjectMapper().writeValueAsString(getUser002());
    }

    public static String getUser003AsJson() throws Exception {
        return new ObjectMapper().writeValueAsString(getUser003());
    }
}
