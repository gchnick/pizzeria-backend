package com.idforideas.pizzeria.appuser;

import static java.util.Map.of;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import javax.validation.Valid;

import com.idforideas.pizzeria.util.Response;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * @author Nick Galan
 * @version 1.0
 * @since 2/28/2022
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class AppUserResource {
    private final AppUserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Response> saveUser(@RequestBody @Valid AppUser user) {
        return ResponseEntity.status(CREATED).body(
            Response.builder()
                .timeStamp(now())
                .data(of("user", userService.create(user)))
                .message("User created")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build()
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Response> getUsers() {
        return ResponseEntity.ok(
            Response.builder()
                .timeStamp(now())
                .data(of("users", userService.getUsers()))
                .message("Users retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateUser(@RequestBody @Valid AppUser newUser, @PathVariable("id") Long id) {
            return userService.get(id).map(user -> {
                user.setFullName(newUser.getFullName());
                user.setEmail(newUser.getEmail());
                user.setPassword(newUser.getPassword());
                user.setRole(newUser.getRole());
                return ResponseEntity.ok(
                    Response.builder()
                        .timeStamp(now())
                        .data(of("user", userService.update(user)))
                        .message("User updated")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
                );
            }).orElseGet(() -> {
                return ResponseEntity.status(CREATED).body(
                    Response.builder()
                        .timeStamp(now())
                        .data(of("user", userService.create(newUser)))
                        .message("User created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
                );
            });
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
