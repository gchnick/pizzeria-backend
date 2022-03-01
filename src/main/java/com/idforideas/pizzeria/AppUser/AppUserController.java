package com.idforideas.pizzeria.AppUser;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * @author Nick Galan
 * @version 1.0
 * @since 2/28/2022
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>>getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

}
