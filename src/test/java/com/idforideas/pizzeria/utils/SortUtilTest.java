package com.idforideas.pizzeria.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Stream;

import com.idforideas.pizzeria.exceptions.BadRequestException;

import org.springframework.data.domain.Sort.Order;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


public class SortUtilTest {
   
    @ParameterizedTest
    @MethodSource("sortArguments")
    void whenGetOrdersThenNotNull(String[] sort) {
        assertNotNull(SortUtil.getOrders(sort));
    }

    @ParameterizedTest
    @MethodSource("sortArguments")
    void whenGetOrdersThenNotContains_ASC_Or_DESC_Properties(String[] sort) {
        List<Order> orders = SortUtil.getOrders(sort);
        assertFalse(orders.contains(Order.by("asc")));
        assertFalse(orders.contains(Order.desc("asc")));
        assertFalse(orders.contains(Order.by("desc")));
        assertFalse(orders.contains(Order.desc("desc")));
    }

    @ParameterizedTest
    @MethodSource("sortInvalidArguments")
    void whenInvalidParametersThenThrowBadRequestException(String[] sort) {
        assertThrows(BadRequestException.class, () -> SortUtil.getOrders(sort));
    }

    
    static Stream<Arguments> sortArguments() {
        return Stream.of(
            Arguments.of((Object) new String[]{"price", "desc"}),
            Arguments.of((Object) new String[]{"price", "asc", "name", "category","desc"}),
            Arguments.of((Object) new String[]{"lastname", "desc", "phone", "asc"}),
            Arguments.of((Object) new String[]{"name"}),
            Arguments.of((Object) new String[]{"phone", "desc", "address"})
            );
    }

    static Stream<Arguments> sortInvalidArguments() {
        return Stream.of(
            Arguments.of((Object) new String[]{"asc", "name"}),
            Arguments.of((Object) new String[]{"price", "asc", "desc","name", "category","desc"}),
            Arguments.of((Object) new String[]{"lastname", "desc", "desc", "asc"}),
            Arguments.of((Object) new String[]{"desc", "phone", "desc", "address"})
        );
    }
}
