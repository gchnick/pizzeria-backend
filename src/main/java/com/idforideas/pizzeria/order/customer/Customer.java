package com.idforideas.pizzeria.order.customer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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
    @Size(min = 3, max = 100)
    @Column(nullable = false, length = 100)
    private String name;
    
    /**
     * Numero telefónico para contactar al cliente 
     */
    @NotEmpty
    @Size(min = 9, max = 15)
    @Column(nullable = false, length = 15)
    private String phone;

    /**
     * Dirección de destino para la entrega del pedido
     */
    @NotEmpty
    @Size(min = 10, max = 200)
    @Column(name = "delivery_address", nullable = false, length = 200)
    private String deliveryAddress;
}
