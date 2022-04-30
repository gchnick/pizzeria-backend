package com.idforideas.pizzeria.user;

import static javax.persistence.EnumType.STRING;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.idforideas.pizzeria.util.BaseEntity;
import com.idforideas.pizzeria.validation.Password;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Column;
/**
 * Extiende de {@link BaseEntity} e implementa {@link UserDetails}
 * @author Nick Galán
 */
@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {

    /**
     * Nombre completo del usuario
     */
    @NotBlank
    @Size(min = 3, max = 100)
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    /**
     * Correo electrónico del usuario. Este se usara como <code>username</code> para poder solicitar un token de acceso
     */
    @Email
    @NotBlank
    @Size(min = 3, max = 100)
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Password(message = "La contraseña debe tener al menos: un dígito, una letra en mayúscula y minúscula, un signo especial [@#@#$%^&+=]. Tener 8 caracteres de tamaño y no tener ningún espacios.")
    @Column(nullable = false)
    private String password;

    /**
     * El valor siempre debe ser <code>ROLE_ADMIN</code>
     */
    @NotNull
    @Enumerated(STRING)
    @Column(nullable = false, length = 16)
    private UserRole role;

    @JsonIgnore
    public String getName() {
        return fullName;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singleton(authority);
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}