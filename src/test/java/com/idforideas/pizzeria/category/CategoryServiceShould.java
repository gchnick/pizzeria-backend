package com.idforideas.pizzeria.category;

import static com.idforideas.pizzeria.category.CategoryMother.getCategory001;
import static com.idforideas.pizzeria.category.CategoryMother.getCategory002;
import static com.idforideas.pizzeria.category.CategoryMother.getNewCategory001;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.idforideas.pizzeria.exception.BadRequestException;
import com.idforideas.pizzeria.exception.NotFoundException;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
public class CategoryServiceShould {

    @Mock
    CategoryRepo categoryRepo;

    @InjectMocks
    CategoryServiceJpa categoryService;

    @Test
    void throw_exception_when_category_by_id_not_exist() {
        // Given
        final Long mockedId = 1L;

        // When

        // Then
        assertThrows(NotFoundException.class, ()-> categoryService.get(mockedId));
    }

    @Test
    void throw_exception_when_category_by_name_not_exist() {
        // Given
        final String mockedName = "hamburgesas";

        // When

        // Then
        assertThrows(NotFoundException.class, ()-> categoryService.get(mockedName));
    }

    @Test
    void throw_exception_when_category_name_is_already_registered() {
        // Given
        Category mockedNewCategory001 = getNewCategory001();
        Category mockedCategory = getCategory001();

        // When
        when(categoryRepo.findByNameIgnoreCase(anyString())).thenReturn(Optional.of(mockedCategory));
        
        // Then
        assertThrows(BadRequestException.class, ()-> categoryService.create(mockedNewCategory001));    
    }

    @Test
    void throw_exception_when_category_is_updated_with_name_registered() {
        // Given
        Category mockedEditCategory = getNewCategory001();
        mockedEditCategory.setName("Empanadas");
        Category mockedCategory002 = getCategory002();

        // When
        when(categoryRepo.findByNameIgnoreCase("Empanadas")).thenReturn(Optional.of(mockedCategory002));
        
        // Then
        assertThrows(BadRequestException.class, ()-> categoryService.update(mockedCategory002, mockedEditCategory));    
    } 
}
