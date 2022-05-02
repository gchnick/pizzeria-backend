package com.idforideas.pizzeria.product;

import java.util.Collection;
import java.util.Optional;

import com.idforideas.pizzeria.category.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Nick Galán
 */
public interface ProductService {

    Product create(Product product);

    Product get(Long id);
    
    Optional<Product> getAsOptional(Long id);

    Collection<Product> findByCategory(Category category);
    
    Page<Product> findByCategory(Category category, Pageable pageable);

    Collection<Product> list();

    Page<Product> list(Pageable pageable);

    Product update(Product product);

    void delete(Long id);
}
