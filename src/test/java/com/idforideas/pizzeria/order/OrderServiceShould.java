package com.idforideas.pizzeria.order;

import static com.idforideas.pizzeria.order.OrderMother.getOrder001;
import static com.idforideas.pizzeria.product.ProductMother.getProduct001;
import static com.idforideas.pizzeria.product.ProductMother.getProduct007;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import com.idforideas.pizzeria.product.ProductRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@Tag("order")
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
public class OrderServiceShould {

    @Mock
    OrderRepo orderRepo;

    @Mock
    ProductRepo productRepo;

    @InjectMocks
    OrderServiceJpa orderService;

    Map<Long , Integer> products;

    @BeforeEach
    void setUp() {
        products = new HashMap<>();
    }

    /*
     * En el checkout se crea un texto automático que se envía al whatsapp del local.
     * P. E. https://wa.me/5124234234234234?text=Hola,%20estoy%20interesado%20el%20produc
to,%20quisiera%20una%20porción
     */

    @Test
     void add_products() {

        // Arrange
        products.put(1L, 2);
        products.put(7L, 2);
        when(productRepo.getById(1L)).thenReturn(getProduct001());
        when(productRepo.getById(7L)).thenReturn(getProduct007());
        when(orderRepo.save(any(Order.class))).thenReturn(getOrder001());

        // Act
        Order order = orderService.create(products);
        int productsSizeExpected = 2;
        int productsSizeActual = order.getProducts().size();

        int firstProductQuantityExpected = 2;
        int firstProductQuantityActual = order.getProducts().get(0).getQuantity();

        int secondProductQuantityExpected = 2;
        int secondProductQuantityActual = order.getProducts().get(0).getQuantity();

        // Assert
        assertNotNull(order);
        assertEquals(productsSizeExpected, productsSizeActual);
        assertEquals(firstProductQuantityExpected, firstProductQuantityActual);
        assertEquals(secondProductQuantityExpected, secondProductQuantityActual);
    }
}
