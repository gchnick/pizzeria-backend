package com.idforideas.pizzeria.order.customer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.idforideas.pizzeria.util.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {

    /**
     * Nombre del cliente
     */
    @NotEmpty
    @Column(nullable = false)
    private String name;
    
    /**
     * Numero telefónico para contactar al cliente 
     */
    @NotEmpty
    @Column(nullable = false)
    private String phone;

    /**
     * Dirección de destino para la entrega del pedido
     */
    @NotEmpty
    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;
}
