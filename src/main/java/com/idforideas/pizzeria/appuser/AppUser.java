package com.idforideas.pizzeria.appuser;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.EnumType.STRING;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.idforideas.pizzeria.validation.Password;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Column;
/**
 * @author Nick Galan
 * @version 1.0
 * @since 2/28/2022
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser implements UserDetails {
    @Id @GeneratedValue(strategy = AUTO)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(nullable = false, length = 100)
    private String fullName;

    @Email
    @NotBlank
    @Size(min = 3, max = 100)
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Password
    @Size(max = 16)
    @Column(nullable = false)
    private String password;

    @NotBlank
    @Size(min = 3, max = 16)
    @Enumerated(STRING)
    @Column(nullable = false, length = 16)
    private AppUserRole role;

    public String getName() {
        return fullName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singleton(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}