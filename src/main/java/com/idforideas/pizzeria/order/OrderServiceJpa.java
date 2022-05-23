package com.idforideas.pizzeria.order;

import com.idforideas.pizzeria.exception.NotFoundException;
import com.idforideas.pizzeria.order.customer.Customer;
import com.idforideas.pizzeria.order.customer.CustomerRepo;
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
    final CustomerRepo customerRepo;
    final ProductRepo productRepo;

    @Override
    public Order create(OrderDTO newOrder) {
        log.info("Adding product in order");
        Order order = new Order();
        Customer customer = customerRepo.save(newOrder.customer());
        order.setCustomer(customer);

        newOrder.products().forEach((id, q) -> {
            Product product = productRepo.findById(id)
                .orElseThrow(()-> new NotFoundException("Id product not exists"));
            order.add(product, q);
        });

        return orderRepo.save(order);
    }
}