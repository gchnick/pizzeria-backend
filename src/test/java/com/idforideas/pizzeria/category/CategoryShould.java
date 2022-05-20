package com.idforideas.pizzeria.category;

import static com.idforideas.pizzeria.category.CategoryMother.getCategory001;
import static com.idforideas.pizzeria.category.CategoryMother.getCategory002;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class CategoryShould {

    @Test
    void test_equals() {
        // Arrange
        Category pizzas1 = getCategory001();
        Category pizzas2 = getCategory001();
        Category empanadas = getCategory002();

        // Act
        boolean equals = pizzas1.equals(pizzas2);
        boolean different = pizzas1.equals(empanadas);

        // Assert
        assertTrue(equals);
        assertFalse(different);
    }
    
}
