package com.idforideas.pizzeria.utils;

import static java.util.Arrays.stream;

import java.util.List;

import org.springframework.data.domain.Sort.Order;

public abstract class SortUtil {

    public static List<Order> getOrders(String[] sort) {
        return stream(sort)
            .map(s -> s.split(","))
            .map(s ->  {
                if(s.length == 1) {
                    return Order.by(s[0]);
                }
                if (s[1].contains("desc") || s[1].contains("asd")) {
                    return s[1].contains("asd") ? Order.asc(s[0]) : Order.desc(s[0]);
                }
                return null;
            }).toList();
    }
}
