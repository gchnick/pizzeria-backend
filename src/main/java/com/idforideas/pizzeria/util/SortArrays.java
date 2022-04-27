package com.idforideas.pizzeria.util;

import static java.util.Arrays.stream;
import static org.springframework.data.domain.Sort.Order.by;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;

import com.idforideas.pizzeria.exception.BadRequestException;

import org.springframework.data.domain.Sort.Order;

public class SortArrays extends SortUtil {

    public List<Order> getOrders(String[] sort) {
        final List<Order> orders = new ArrayList<>();
        final boolean withDirection = stream(sort).anyMatch(SortUtil::isDirection);

        if(!withDirection) { stream(sort).forEach(s -> orders.add(by(s))); }
        else {
            if(isValid(sort)) {
                IntStream.range(0, sort.length).forEach( i -> {
                    if(isDirection(sort[i])) {
                        Order order = getOrder(sort[i-1], sort[i]);
                        orders.add(order);
                    }
                });
            } else {
                throw new BadRequestException("Bad use of direction in sort");
            }
        }
        return orders;
    }


    private static boolean isValid(String[] sort) {
        if(hasDirectionAtZeroIndex(sort[0])) return false;
        if(hasDirectionsTogether(sort)) return false;
        return true;
    }

    private static boolean hasDirectionAtZeroIndex(String arg) {
        return isDirection(arg) ? true : false;
    }

    private static boolean hasDirectionsTogether(String[] args) {
        int index = 0;
        int last = 0;
        int now = -1;
        boolean isTogether = false;

        for(String arg : args) {
            if(isDirection(arg)) {
                last = now;
                now = index;
            }
            if(last == now-1) {
                isTogether = true;
            }
            index++;
        }

        return isTogether;
    } 
}