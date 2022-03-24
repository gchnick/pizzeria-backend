package com.idforideas.pizzeria.product;

import java.util.Collection;
import java.util.Optional;

public interface ProductService {

    Product save(Product product);
    
    Optional<Product> findById(Long id);

    Collection<Product> findAll();

    Collection<Product> findByCategoryId(Long categoryId);

    Collection<Product> findByCategory(Category category);

    void delete(Long id);    
}
