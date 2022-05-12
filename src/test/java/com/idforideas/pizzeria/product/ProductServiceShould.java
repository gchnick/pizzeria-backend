package com.idforideas.pizzeria.product;

import static org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

import org.junit.jupiter.api.Disabled;
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
    ProductServiceJpa productSerice;

    // TODO Implementar las pruebas de ProductServiceJpa
    @Test
    @Disabled
    void throw_exception_when_product_by_id_not_exist() {

    }
    
}
