package com.idforideas.pizzeria.product;

import java.util.Collection;
import java.util.Optional;

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
    public Product save(Product product) {
        log.info("Saving product {}",  product.getName());
        return this.productRepo.save(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        log.info("Finding product by id {}", id);
        return this.productRepo.findById(id);
    }

    @Override
    public Collection<Product> findAll() {
        log.info("Fetching all products");
        return this.productRepo.findAll();
    }

    @Override
    public Collection<Product> findByCategoryId(Long categoryId) {
        log.info("Finding product by category id {}", categoryId);
        Optional<Category> category = this.categoryRepo.findById(categoryId);
        return this.findByCategory(category.orElseThrow());
    }

    @Override
    public Collection<Product> findByCategory(Category category) {
        log.info("Finding product by category {}", category.getName());
        return this.productRepo.findByCategory(category);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting product by id {}",id);
        this.productRepo.deleteById(id);
    }
}
