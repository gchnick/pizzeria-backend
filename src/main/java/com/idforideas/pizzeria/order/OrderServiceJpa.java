package com.idforideas.pizzeria.order;

import java.util.Map;

import com.idforideas.pizzeria.product.Product;
import com.idforideas.pizzeria.product.ProductRepo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrderServiceJpa implements OrderService {

    final OrderRepo orderRepo;
    final ProductRepo productRepo;


    @Override
    public Order create(Map<Long, Integer> products) {
        log.info("Adding product in order");
        Order order = new Order();

        products.forEach((id, q) -> {
            Product product = productRepo.getById(id);
            order.add(product, q);
        });

        return orderRepo.save(order);
    }
}