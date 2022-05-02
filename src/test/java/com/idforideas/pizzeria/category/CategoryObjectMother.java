package com.idforideas.pizzeria.category;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class CategoryObjectMother {
    
    public static Category getNewCategory () {
        return new Category("Helados");
    }

    public static Category getNewCategory001 () {
        return new Category("Pizzas");
    }

    public static Category getCategory001() {
        Category category = new Category("Pizzas");
        category.setId(1L);
        return category;
    }

    public static Category getCategory002() {
        Category category = new Category("Empanadas");
        category.setId(2L);
        return category;
    }

    public static Category getUpdateCategory() {
        Category category = new Category("Bebidas alcohólicas");
        return category;
    }

    public static String getNewCategoryAsJson() throws Exception {
        return new ObjectMapper().writeValueAsString(getNewCategory());
    }
    
    public static String getCategory001AsJson() throws Exception {
        return new ObjectMapper().writeValueAsString(getCategory001());
    }

    public static String getUpdateCategoryAsJson() throws Exception {
        return new ObjectMapper().writeValueAsString(getUpdateCategory());
    }
}
