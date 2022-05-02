package com.idforideas.pizzeria.category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    Optional<Category> findByNameIgnoreCase(String name);
}
