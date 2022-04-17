package com.idforideas.pizzeria.category;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface CategoryService {

    Category create(Category category);

    Optional<Category> get(Long id);

    Optional<Category> get(String name);

    Collection<Category> list();

    Page<Category> list(Pageable pageable);

    Category update(Category category);

    void delete(Long id);
    
    void valid(Category category);
}
