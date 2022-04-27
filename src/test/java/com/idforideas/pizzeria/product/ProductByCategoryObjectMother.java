package com.idforideas.pizzeria.product;

import com.idforideas.pizzeria.category.Category;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;


public abstract class ProductByCategoryObjectMother {

    static final Category PIZZAS = new Category("Pizzas");

    static final Product NAPOLITANA = new Product("Napolitana", "Excelente pizza con salsa napolitana", 12.6F, null, PIZZAS);

    static final Product PEPERONI = new Product("Peperoni", "Pizza con panceta", 11.23F, null, PIZZAS);

    static final Product JAMON = new Product("Pizza de jamon", "Con jamon de espalda", 10.50F, null, PIZZAS);


    public static List<Product> getProducts() {
        List<Product> products = Arrays.asList(NAPOLITANA, PEPERONI, JAMON);
        ListIterator<Product> iterator = products.listIterator();
        while(iterator.hasNext()) {
            Long id = Long.valueOf( Integer.valueOf(iterator.nextIndex()).longValue() );
            iterator.next().setId(id);
        }
        return products;
    }
    
}
