package com.idforideas.pizzeria.order.detail;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.idforideas.pizzeria.product.Product;
import com.idforideas.pizzeria.util.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_details")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail extends BaseEntity {
    
    /**
     * Producto en la linea del pedido
     */
    @NotNull
    @Valid
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * Cantidad del producto de la linea
     */
    @NotNull
    @Column(nullable = false)
    private Integer quantity;

    public Float total() {
        return product.getPrice() * quantity;
    }
}
