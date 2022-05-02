package com.idforideas.pizzeria.product;

import java.util.Collection;
import java.util.Optional;

import com.idforideas.pizzeria.category.Category;
import com.idforideas.pizzeria.exception.NotFoundException;

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

    @Override
    public Product create(Product product) {
        log.info("Saving new product {}",  product.getName());
        return productRepo.save(product);
    }

    @Override
    public Product get(Long id) {
        log.info("Finding product by id {}", id);
        return productRepo.findById(id).orElseThrow(() -> new NotFoundException("Id product not exists"));
    }

    @Override
    public Optional<Product> getAsOptional(Long id) {
        log.info("Finding product by id {}", id);
        return productRepo.findById(id);
    }

    @Override
    public Collection<Product> list() {
        log.info("Fetching all products");
        return productRepo.findAll();
    }

    @Override
    public Page<Product> list(Pageable pageable) {
        log.info("Fetching products with pageable");
        return productRepo.findAll(pageable);
    }

    @Override
    public Collection<Product> findByCategory(Category category) {
        log.info("Finding product by category name {}", category.getName());
        return productRepo.findByCategory(category);
    }

    @Override
    public Page<Product> findByCategory(Category category, Pageable pageable) {
        log.info("Finding product by category name {} with pageable", category.getName());
        return productRepo.findByCategory(category, pageable);
    }

     @Override
    public Product update(Product product) {
        log.info("Updating this product {}",  product.getName());
        return productRepo.save(product);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting product by id {}",id);
        if(productRepo.existsById(id)) {
            productRepo.deleteById(id);
        }
    }
}
