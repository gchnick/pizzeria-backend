package com.idforideas.pizzeria.category;

import java.util.Collection;
import java.util.Optional;

import com.idforideas.pizzeria.exception.BadRequestException;

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
        valid(category);
        return categoryRepo.save(category);
    }

    @Override
    public Optional<Category> get(Long id) {
        log.info("Finding category by id {}", id);
        return categoryRepo.findById(id);
    }

    @Override
    public Optional<Category> get(String name) {
        log.info("Finding category by name {}", name);
        return categoryRepo.findByNameIgnoreCase(name);
    }

    @Override
    public Collection<Category> list() {
        log.info("Fetching all categories");
        return categoryRepo.findAll();
    }

    @Override
    public Page<Category> list(Pageable pageable) {
        log.info("Fetching categories with pageable");
        return categoryRepo.findAll(pageable);
    }

    @Override
    public Category update(Category category) {
        log.info("Updating this category {}",  category.getName());
        return categoryRepo.save(category);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting category by id {}",id);
        if(categoryRepo.existsById(id)){
            this.categoryRepo.deleteById(id);
        }
    }

    @Override
    public void valid(Category category) {
        boolean isPresent = categoryRepo.findByNameIgnoreCase(category.getName()).isPresent();
        if(isPresent) { 
            log.error("{} category is already registered", category.getName());
            throw new BadRequestException("The name of category is already registered");
        }
    }
    
}
