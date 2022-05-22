package com.idforideas.pizzeria.order;

import com.idforideas.pizzeria.product.Product;
import com.idforideas.pizzeria.product.ProductMother;

public abstract class OrderMother {

    public static Order getOrder001() {
        Order order = new Order();
        Product product001 = ProductMother.getProduct001();
        Product product007 = ProductMother.getProduct007();
        order.add(product001, 2);
        order.add(product007, 2);
        order.setId(1L);
        return order;
    }
}
