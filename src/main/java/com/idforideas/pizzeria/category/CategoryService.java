package com.idforideas.pizzeria.category;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface CategoryService {

    Category create(Category category);

    Category get(Long id);

    Category get(String name);

    Optional<Category> getAsOptional(Long id);

    Collection<Category> list();

    Page<Category> list(Pageable pageable);

    Category update(Category category);

    void delete(Long id);
    
    void valid(Category category);
}
