package com.idforideas.pizzeria;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.idforideas.pizzeria.category.Category;
import com.idforideas.pizzeria.product.Product;
import com.idforideas.pizzeria.product.ProductRepo;
import com.idforideas.pizzeria.product.ProductServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceShuold {

    @Mock
    ProductRepo productRepo;

    @InjectMocks
    ProductServiceImpl productSerice;

    static final Category PIZZAS = new Category("Pizzas");

    static final Product NAPOLITANA = new Product("Napolitana", "Deliciosa pizzas con salsa napolitana", 10.20F, null, PIZZAS);
    static final Product TRES_QUESOS = new Product("Tres quesos", "Queso azul, queso blanco y quezo muzarrella", 9F, null, PIZZAS);
    static final Product JAMON = new Product("Pizzas de jamon", "Con jamon de espalda", 10.50F, null, PIZZAS);

    @Test
    void return_list_products_by_cateogy() {
        
        List<Product> expected = Arrays.asList(NAPOLITANA, TRES_QUESOS, JAMON);

        when(productRepo.findByCategory(PIZZAS)).thenReturn(expected);

        assertSame(expected, productSerice.findByCategory(PIZZAS));
    }
    
}
