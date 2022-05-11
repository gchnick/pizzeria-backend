package com.idforideas.pizzeria.category;

import java.util.Collection;
import java.util.Optional;

import com.idforideas.pizzeria.exception.BadRequestException;
import com.idforideas.pizzeria.exception.NotFoundException;
import com.idforideas.pizzeria.product.ProductRepo;

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
public class CategoryServiceJpa implements CategoryService {
    private final CategoryRepo categoryRepo;
    private final ProductRepo produdctRepo;

    @Override
    public Category create(Category category) {
        log.info("Saving new category {}",  category.getName());
        valid(category);
        return categoryRepo.save(category);
    }

    @Override
    public Category get(Long id) {
        log.info("Finding category by id {}", id);
        return categoryRepo.findById(id).orElseThrow(() -> new NotFoundException("Id category not exists"));
    }

    @Override
    public Category get(String name) {
        log.info("Finding category by name {}", name);
        return categoryRepo.findByNameIgnoreCase(name).orElseThrow(() -> new NotFoundException("Name category not exists"));
    }

    @Override
    public Optional<Category> getAsOptional(Long id) {
        log.info("Finding category by id {}", id);
        return categoryRepo.findById(id);
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
    public Category update(Category category, Category editedCategory) {
        log.info("Updating this category {}", category.getName());
        valid(editedCategory);
        category.setName(editedCategory.getName());
        return categoryRepo.save(category);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting category by id {}",id);
        Optional<Category> category = categoryRepo.findById(id);
        if(category.isPresent()){
            produdctRepo.deleteAllByCategory(category.get());
            categoryRepo.deleteById(id);
        }
    }

    private void valid(Category category) {
        boolean isPresent = categoryRepo.findByNameIgnoreCase(category.getName()).isPresent();
        if(isPresent) { 
            log.error("{} category is already registered", category.getName());
            throw new BadRequestException("The name of category is already registered");
        }
    }
}
