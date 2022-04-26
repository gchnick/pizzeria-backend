package com.idforideas.pizzeria.docs.category;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.idforideas.pizzeria.docs.support.MockBase;

import org.junit.jupiter.api.Test;

public class CategoryResourceTest extends MockBase {

    @Test
    void getCategory() throws Exception {
        mockMvc.perform(get("/api/v1/categories/category/pizzas").contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON));
    }
    
    @Test
    void getAllCategories() throws Exception {
        mockMvc.perform(get("/api/v1/categories").contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON));
    }
}
