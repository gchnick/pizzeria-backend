package com.idforideas.pizzeria.order.customer;

public abstract class CustomerMother {

    public static Customer getCustomerJuan() {
        Customer customer = new Customer();
        customer.setName("Juan Pérez");
        customer.setPhone("00-000-0000");
        customer.setDeliveryAddress("Calle Street casa 1-77 Las Lomas");
        return customer;
    }
}