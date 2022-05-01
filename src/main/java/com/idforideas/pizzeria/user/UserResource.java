package com.idforideas.pizzeria.user;

import static java.util.Map.of;
import static java.util.stream.Collectors.toList;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.OK;

import java.net.URI;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;

/**
 * @author Nick Galán
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;

    /**
     * Añadir un nuevo <b>usuario</b> administrador del sistema
     * @param user Información del usuario
     * @return {@link Response}
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Response> save(@RequestBody @Valid User user) {
        User createdUser = userService.create(user);

        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/" + createdUser.getId())
            .buildAndExpand()
            .toUri();                
        return ResponseEntity.created(uri).body(
            Response.builder()
                .timeStamp(now())
                .data(of("user", withoutPassword(createdUser)))
                .message("User created")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build()
        );
    }

    /**
    * Devuelve un usuario por ID
     * @param id ID del usuario a recuperar
     * @return {@link Response}
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Response> get(@PathVariable Long id) throws Exception {
        User user = userService.get(id);
        return ResponseEntity.ok(
            Response.builder()
                .timeStamp(now())
                .data(of("user", withoutPassword(user)))
                .message("User retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    /**
     * Devuelve una lista sin paginación de usuarios
     * @return {@link Response}
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Response> list() {
        return ResponseEntity.ok(
            Response.builder()
                .timeStamp(now())
                .data(of("users", userService.list().stream().map(u -> withoutPassword(u)).collect(toList()) ))
                .message("Users retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    /**
     * Actualiza todos los campos de usuario a la que pertenece el ID con la nueva información. En caso de no existir un usuario con el ID suministrado se procederá a crear un nuevo usuario
     * @param id ID del usuario a actualizar
     * @param newUser Nueva información del usuario para aplicar en actualización
     * @return {@link Response}
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody @Valid User newUser) {

        return userService.getAsOptional(id).map(user -> {
            user.setFullName(newUser.getFullName());
            user.setEmail(newUser.getEmail());
            user.setPassword(newUser.getPassword());
            user.setRole(newUser.getRole());
            User userUpdated = userService.update(user);
            return ResponseEntity.ok(
                Response.builder()
                    .timeStamp(now())
                    .data(of("user", withoutPassword(userUpdated)))
                    .message("User updated")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        }).orElseGet(() -> {
            User createdUser = userService.create(newUser);
            URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + createdUser.getId())
                .buildAndExpand()
                .toUri();
            return ResponseEntity.created(uri).body(
                Response.builder()
                    .timeStamp(now())
                    .data(of("user", withoutPassword(createdUser)))
                    .message("User created")
                    .status(CREATED)
                    .statusCode(CREATED.value())
                    .build()
            );
        });
    }

    /**
     * Elimina el usuario al que corresponde el ID suministrado 
     * @param id ID del usuario a eliminar
     * @return {@link Response}
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    private Object withoutPassword(User u) {
        return new UserWithoutPassword(u.getId(), u.getFullName(), u.getEmail(), u.getRole());
    }

    private record UserWithoutPassword(Long id, String fullName, String email, UserRole role) {}
}
