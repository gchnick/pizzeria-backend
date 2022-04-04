package com.idforideas.pizzeria.utils;

import static java.util.Arrays.stream;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.Order.desc;
import static org.springframework.data.domain.Sort.Order.by;

import java.util.List;

import org.springframework.data.domain.Sort.Order;

public abstract class SortUtil {

   public static List<Order> getOrders(String[] sort) {
        final List<Order> orders = stream(sort).filter(s -> !s.contains(",")).map(o -> by(o)).toList();

        stream(sort).filter(s -> s.contains(",")).forEach(str -> {
            if (str.contains(ASC.name()) || str.contains(DESC.name())) {
                String o[] = str.split(",");
                Order order = o[1].equalsIgnoreCase(ASC.name()) ? asc(o[0]) : desc(o[0]);
                orders.add(order);
            }    
        });

        return orders;
    }
}
