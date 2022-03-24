package com.idforideas.pizzeria.product;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
    Collection<Product> findByCategory(Category category);
}
