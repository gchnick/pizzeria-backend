package com.idforideas.pizzeria.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idforideas.pizzeria.category.Category;

public abstract class ProductObjectMother {

    public static Product getNewProduct011() {
        Category pizzasCategory = new Category("Pizzas");
        pizzasCategory.setId(1L);
        return new Product("Pizza hawayana", "Espectacular pizza con piña", 13.85F, null, pizzasCategory);
    }

    public static Product getUpdateProduct011() {
        Product updatedProduct = getNewProduct011();
        updatedProduct.setName("Hawayana Big");
        updatedProduct.setDescription("Espectacular pizza grande con piña");
        return updatedProduct;
    }

    public static String getNewProduct011AsJson() throws Exception {
        return new ObjectMapper().writeValueAsString(getNewProduct011());
    }

    public static String getUpdateProduct011AsJson() throws Exception {
        return new ObjectMapper().writeValueAsString(getUpdateProduct011());
    }
}
