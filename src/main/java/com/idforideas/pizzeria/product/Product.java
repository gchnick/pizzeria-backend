package com.idforideas.pizzeria.product;

import static javax.persistence.FetchType.EAGER;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.idforideas.pizzeria.category.Category;
import com.idforideas.pizzeria.util.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {

    /**
     * Nombre del producto
     */
    @NotBlank
    @Size(min = 3, max = 100)
    @Column(nullable = false, length = 100)
    private String name;
    
    /**
     * Descripción breve del producto
     */
    @NotBlank
    @Column(nullable = false)
    private String description;
    
    /**
     * Precio del producto
     */
    @NotNull
    @Column(nullable = false)
    private Float price;

    /**
     * Dirección URL de la imagen del producto
     */
    @Column(name = "picture_url")
    private String pictureURL;

    /**
     * Cada producto debe estar clasificado en una categoría
     */
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull
    @Valid
    private Category category;
}
