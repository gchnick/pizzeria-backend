package com.idforideas.pizzeria.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

import com.idforideas.pizzeria.category.Category;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class ProductShould {
    
    @Test
    void set_and_get_category() {
        // Arrange
        Product product = new Product();
        String expected = "Pizzas";
        Category category = new Category(expected);

        // Act
        product.setCategory(category);

        String actual = product.getCategory().getName();

        // Assert
        assertEquals(expected, actual);
    }
}
