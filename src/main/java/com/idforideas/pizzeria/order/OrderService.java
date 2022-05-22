package com.idforideas.pizzeria.order;

import java.util.Map;

public interface OrderService {

    Order create(Map<Long, Integer> products);
}
