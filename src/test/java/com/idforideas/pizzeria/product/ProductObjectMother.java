package com.idforideas.pizzeria.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idforideas.pizzeria.category.Category;

public abstract class ProductObjectMother {

    public static Product getNewProduct011() {
        Category pizzasCategory = new Category("Pizzas");
        pizzasCategory.setId(1L);
        return new Product("Pizzas hawayana", "Espectacular pizzas con pina", 13.85F, null, pizzasCategory);
    }

    public static String getNewProduct011AsJson() throws Exception {
        return new ObjectMapper().writeValueAsString(getNewProduct011());
    }
    
}
