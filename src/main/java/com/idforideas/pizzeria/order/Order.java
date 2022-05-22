package com.idforideas.pizzeria.order;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.DATE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.idforideas.pizzeria.order.customer.Customer;
import com.idforideas.pizzeria.order.detail.OrderDetail;
import com.idforideas.pizzeria.product.Product;
import com.idforideas.pizzeria.util.BaseEntity;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "orders")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class Order extends BaseEntity {
    
    /**
     * Fecha en la que se realiza el pedido. El sistema agrega de manera automática esta información
     */
    @Column(nullable = false)
    @Temporal(DATE)
    @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE)
    private Date date;

    /**
     * Datos del cliente que realiza el envió
     */
    @NotNull
    @Valid
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    /**
     * Lista de productos y cantidades del pedido
     */
    @OneToMany(fetch = LAZY, cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderDetail> products;

    public Order() {
        products = new ArrayList<>();
    }

    @PrePersist
    void prePersist() {
        date = new Date();
    }
    
    public void add(Product product, Integer quantity) {
        products.add(new OrderDetail(product, quantity));
    }

    public Float grandTotal() {
        return products.stream()
            .map(d -> d.total())
            .reduce(0.0F, (sum, total) -> sum+total);
    }
}
