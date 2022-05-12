package com.idforideas.pizzeria.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Stream;

import com.idforideas.pizzeria.exception.BadRequestException;

import org.springframework.data.domain.Sort.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


@DisplayName("Sort arrays should")
public class SortArraysShould {

    private SortUtil sort;

    @BeforeEach
    void setUp() {
        // Arrange
        this.sort = new SortArrays();
    }
   
    @ParameterizedTest
    @MethodSource("sortArguments")
    @DisplayName("is not null when get orders")
    void notNull(String[] sort) {

        // Act
        List<Order> actual = this.sort.getOrders(sort);

        // Assert
        assertNotNull(actual);
    }

    @ParameterizedTest
    @MethodSource("sortArguments")
    @DisplayName("is not contains asc or desc with properties order")
    void notContains_ASC_or_DESC(String[] sort) {

        // Act
        List<Order> actual = this.sort.getOrders(sort);

        // Assert
        assertFalse(actual.contains(Order.by("asc")));
        assertFalse(actual.contains(Order.desc("asc")));
        assertFalse(actual.contains(Order.by("desc")));
        assertFalse(actual.contains(Order.desc("desc")));
    }

    @ParameterizedTest
    @MethodSource("sortInvalidArguments")
    @DisplayName("throw exception when has invalid parameters")
    void invalidParameters(String[] sort) {
        
        // Assert
        assertThrows(BadRequestException.class, () -> {
            // Act
            this.sort.getOrders(sort);
        });
    }

    // ------------------------ METHODS SOURCE ------------------------ //
    // --------------------------- ARRANGE --------------------------- //

    private static Stream<Arguments> sortArguments() {
        return Stream.of(
            Arguments.of((Object) new String[]{"price", "desc"}),
            Arguments.of((Object) new String[]{"price", "asc", "name", "category","desc"}),
            Arguments.of((Object) new String[]{"lastname", "desc", "phone", "asc"}),
            Arguments.of((Object) new String[]{"name"}),
            Arguments.of((Object) new String[]{"phone", "desc", "address"})
            );
    }

    private static Stream<Arguments> sortInvalidArguments() {
        return Stream.of(
            Arguments.of((Object) new String[]{"asc", "name"}),
            Arguments.of((Object) new String[]{"price", "asc", "desc","name", "category","desc"}),
            Arguments.of((Object) new String[]{"lastname", "desc", "desc", "asc"}),
            Arguments.of((Object) new String[]{"desc", "phone", "desc", "address"})
        );
    }
}
