package com.idforideas.pizzeria.product;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

import com.idforideas.pizzeria.exception.NotFoundException;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
public class ProductServiceShould {

    @Mock
    ProductRepo productRepo;

    @InjectMocks
    ProductServiceJpa productService;

    @Test
    void throw_exception_when_product_by_id_not_exist() {
        // Arrange
        Long id = 1L;

        // Assert
        assertThrows(NotFoundException.class, ()-> {
            // Act
            productService.get(id);
        });

    }
    
}
