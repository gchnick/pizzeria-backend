package com.idforideas.pizzeria.order.detail;

import static com.idforideas.pizzeria.product.ProductMother.getProduct001;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

import com.idforideas.pizzeria.product.Product;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("order")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class OrderDetailShould {

    @Test
    void generate_total() {
        //Arrange
        Product product = getProduct001();
        OrderDetail detail = new OrderDetail(product, 2);

        // Act
        float expected = 31.3F;
        float actual = detail.total();

        // Assert
        assertEquals(expected, actual);
    }
}
