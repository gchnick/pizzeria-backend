package com.idforideas.pizzeria.product;

import java.util.List;

import com.idforideas.pizzeria.category.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
    
    Page<Product> findByCategory(Category category, Pageable pageable);

    void deleteAllByCategory(Category category);
}
