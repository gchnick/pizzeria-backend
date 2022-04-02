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
        return stream(sort)
            .map(s -> s.split(","))
            .map(a ->  {
                if(stream(a).filter(str -> str.equalsIgnoreCase(ASC.name()) || str.equalsIgnoreCase(DESC.name())).findFirst().isPresent()) {
                    return a[1].equalsIgnoreCase(ASC.name()) ? asc(a[0]) : desc(a[0]);
                }
                else {
                    return by(a[0]);
                }
            }).toList();
    }
}
