package com.idforideas.pizzeria.customer;

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

    @NotEmpty
    @Column(nullable = false)
    private String name;
    
    @NotEmpty
    @Column(nullable = false)
    private String phone;

    @NotEmpty
    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;
}
