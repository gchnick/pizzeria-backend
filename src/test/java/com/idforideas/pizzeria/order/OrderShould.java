package com.idforideas.pizzeria.order;

import static com.idforideas.pizzeria.order.OrderMother.getOrder001;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("order")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class OrderShould {

    @Test
    void generate_grand_total() {
        // Arrange
        Order order = getOrder001();

        // Act
        float expected = 33.7F;
        float actual = order.grandTotal();

        // Assert
        assertEquals(expected, actual);
    }
    
}
