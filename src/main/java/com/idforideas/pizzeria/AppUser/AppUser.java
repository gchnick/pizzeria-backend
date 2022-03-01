package com.idforideas.pizzeria.AppUser;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static javax.persistence.GenerationType.AUTO;

import java.util.Arrays;
import java.util.Collection;
/**
 * @author Nick Galan
 * @version 1.0
 * @since 2/28/2022
 */
@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class AppUser {
    @Id @GeneratedValue(strategy = AUTO)
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private final Collection<String> role = Arrays.asList("ROLE_ADMIN");
}