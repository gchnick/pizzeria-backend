package com.idforideas.pizzeria.AppUser;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AppUserController {
    private AppUserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>>getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }
    
}
