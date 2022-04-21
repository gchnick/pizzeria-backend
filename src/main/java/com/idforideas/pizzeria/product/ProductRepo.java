package com.idforideas.pizzeria.product;

import java.util.Collection;

import com.idforideas.pizzeria.category.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
    Collection<Product> findByCategory(Category category);
    
    Page<Product> findByCategory(Category category, Pageable pageable);
}
