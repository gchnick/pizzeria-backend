package com.idforideas.pizzeria.order;

import static com.idforideas.pizzeria.order.customer.CustomerMother.getCustomerJuan;
import static com.idforideas.pizzeria.product.ProductMother.getProduct001;
import static com.idforideas.pizzeria.product.ProductMother.getProduct007;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idforideas.pizzeria.order.customer.Customer;
import com.idforideas.pizzeria.product.Product;

public abstract class OrderMother {

    public static Order getOrder001() {
        Order order = new Order();
        Product product001 = getProduct001();
        Product product007 = getProduct007();
        Customer customer = getCustomerJuan();
        order.add(product001, 2);
        order.add(product007, 2);
        order.setCustomer(customer);
        order.setId(1L);
        return order;
    }

    public static OrderDTO newOrderOfJuan() {
        Customer customer = getCustomerJuan();
        Map<Long, Integer> products = new HashMap<>();
        products.put(1L, 2);
        products.put(7L, 2);
        return new OrderDTO(products, customer);
    }

    public static String newOrderOfJuanAsJSON() throws Exception {
        return new ObjectMapper().writeValueAsString(newOrderOfJuan());
    }
}
