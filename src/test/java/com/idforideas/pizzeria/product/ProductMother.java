package com.idforideas.pizzeria.product;

import static com.idforideas.pizzeria.category.CategoryMother.getCategory001;
import static com.idforideas.pizzeria.category.CategoryMother.getCategory003;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idforideas.pizzeria.category.Category;

public abstract class ProductMother {

    public static Product getProduct001() {
        Category pizzasCategory = getCategory001();
        Product product = new Product("Napolitana", "Sabrosa pizza con salsa napolitana", 15.65F, null, pizzasCategory);
        product.setId(1L);
        return product;
    }

    public static Product getProduct007() {
        Category bebidasCategory = getCategory003();
        Product product = new Product("Pepsi", "Pepsi selfie", 1.2F, null, bebidasCategory);
        product.setId(7L);
        return product;
    }

    public static Product getNewProductPizzaMuzzarella() {
        Category pizzasCategory = getCategory001();
        return new Product("Muzzarella", "Irresistible pizza con tres capas de queso muzzarella", 17.85F, null, pizzasCategory);
    }
    
    public static Product getNewProduct011() {
        Category pizzasCategory = getCategory001();
        return new Product("Pizza hawayana", "Espectacular pizza con piña", 13.85F, null, pizzasCategory);
    }

    public static Product getUpdateProduct011() {
        Product updatedProduct = getNewProduct011();
        updatedProduct.setName("Hawayana Big");
        updatedProduct.setDescription("Espectacular pizza grande con piña");
        return updatedProduct;
    }

    public static String getNewProductPizzaMuzzarellaAsJson() throws Exception {
        return new ObjectMapper().writeValueAsString(getNewProductPizzaMuzzarella());
    }

    public static String getNewProduct011AsJson() throws Exception {
        return new ObjectMapper().writeValueAsString(getNewProduct011());
    }

    public static String getUpdateProduct011AsJson() throws Exception {
        return new ObjectMapper().writeValueAsString(getUpdateProduct011());
    }
}
