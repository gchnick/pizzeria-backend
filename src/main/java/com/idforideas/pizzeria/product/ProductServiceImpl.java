package com.idforideas.pizzeria.product;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    @Override
    public Product create(Product product) {
        log.info("Saving new product {}",  product.getName());
        return this.productRepo.save(product);
    }

    @Override
    public Optional<Product> get(Long id) {
        log.info("Finding product by id {}", id);
        return this.productRepo.findById(id);
    }

    @Override
    public Collection<Product> list() {
        log.info("Fetching all products");
        return this.productRepo.findAll();
    }

    @Override
    public Page<Product> list(Pageable pageable) {
        log.info("Fetching products with pageable");
        return this.productRepo.findAll(pageable);
    }

    @Override
    public Page<Product> findByCategoryId(Long categoryId, Pageable pageable) {
        log.info("Finding product by category id {}", categoryId);
        Category category = this.categoryRepo.findById(categoryId).orElseThrow();
        return this.findByCategory(category, pageable);
    }

    @Override
    public Page<Product> findByCategoryName(String categoryName, Pageable pageable) {
        log.info("Finding product by category name {}", categoryName);
        Category category = this.categoryRepo.findByName(categoryName).orElseThrow();
        return this.findByCategory(category, pageable);
    }

    private Page<Product> findByCategory(Category category, Pageable pageable) {
        return this.productRepo.findByCategory(category, pageable);
    }

     @Override
    public Product update(Product product) {
        log.info("Updating this product {}",  product.getName());
        return this.productRepo.save(product);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting product by id {}",id);
        this.productRepo.deleteById(id);
    }
}
