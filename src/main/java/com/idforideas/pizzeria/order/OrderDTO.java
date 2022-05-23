package com.idforideas.pizzeria.order;

import java.util.Map;

import javax.validation.Valid;

import com.idforideas.pizzeria.order.customer.Customer;

public record OrderDTO(Map<Long, Integer> products, @Valid Customer customer) {}
