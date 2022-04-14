package com.idforideas.pizzeria.util;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.domain.Sort.Order.desc;
import static org.springframework.data.domain.Sort.Order.by;

import java.util.List;

import org.springframework.data.domain.Sort.Order;

public abstract class SortUtil {
    
    public abstract List<Order> getOrders(String[] sort);

    protected static Order getOrder(String property, String direction) {
        return direction.equalsIgnoreCase(ASC.name()) ? by(property) : desc(property);
    }

    protected static boolean isDirection(String arg) {
        return arg.equalsIgnoreCase(ASC.name()) || arg.equalsIgnoreCase(DESC.name());
    }
}
