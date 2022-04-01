package com.idforideas.pizzeria.utils;

import static java.util.Arrays.stream;

import java.util.List;

import org.springframework.data.domain.Sort.Order;

public abstract class SortUtil {

    // FIXME Probar que la logica de negocio funciona
    // Actualmente ve el segundo indice como propiedad
    public static List<Order> getOrders(String[] sort) {
        return stream(sort)
            .map(s -> s.split(","))
            .map(o ->  {
                if(o.length == 1) {
                    return Order.by(o[0]);
                }
                if (o[1].contains("desc") || o[1].contains("asd")) {
                    return o[1].contains("asd") ? Order.asc(o[0]) : Order.desc(o[0]);
                }
                return null;
            }).toList();
    }
}
