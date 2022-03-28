package com.idforideas.pizzeria.category;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;

    @Override
    public Category create(Category category) {
        log.info("Saving new category {}",  category.getName());
        return this.categoryRepo.save(category);
    }

    @Override
    public Optional<Category> get(Long id) {
        log.info("Finding category by id {}", id);
        return this.categoryRepo.findById(id);
    }

    @Override
    public Optional<Category> get(String name) {
        log.info("Finding category by name {}", name);
        return this.categoryRepo.findByName(name);
    }

    @Override
    public Collection<Category> list() {
        log.info("Fetching all categories");
        return this.categoryRepo.findAll();
    }

    @Override
    public Page<Category> list(Pageable pageable) {
        log.info("Fetching categories with pageable");
        return this.categoryRepo.findAll(pageable);
    }

    @Override
    public Category update(Category category) {
        log.info("Updating this category {}",  category.getName());
        return null;
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting category by id {}",id);
        this.categoryRepo.deleteById(id);
    }
    
}
