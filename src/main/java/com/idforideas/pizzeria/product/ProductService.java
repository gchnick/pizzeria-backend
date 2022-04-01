package com.idforideas.pizzeria.product;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Product create(Product product);
    
    Optional<Product> get(Long id);

    Collection<Product> list();

    Page<Product> list(Pageable pageable);

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Product> findByCategoryName(String categoryName, Pageable pageable);

    Product update(Product product);

    void delete(Long id);    
}
