package com.idforideas.pizzeria.docs;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import static org.hamcrest.Matchers.containsString;

import static com.idforideas.pizzeria.category.CategoryObjectMother.getNewCategoryAsJson;
import static com.idforideas.pizzeria.category.CategoryObjectMother.getUpdateCategoryAsJson;

import com.idforideas.pizzeria.docs.support.MockBase;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

public class CategoryResourceTest extends MockBase {

    private static final String URL_TEMPLATE = "/api/v1/categories";

    @Test
    void add() throws Exception {
        // Given
        final String mockedContentJson = getNewCategoryAsJson();

        // When
        final ResultActions result = mockMvc.perform(post(URL_TEMPLATE)
        .with(userToken())
        .contentType(APPLICATION_JSON)
        .content(mockedContentJson));

        // Then
        final String expectedLocationId = "/5";
        result.andExpect(status().isCreated())
        .andExpect(header().string("Location", containsString(URL_TEMPLATE.concat(expectedLocationId))))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andDo(document("{class-name}/{method-name}", 
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            relaxedResponseFields(
                subsectionWithPath("data.category").description("Información de categoría creada"),
                subsectionWithPath("data.category.name").description("Nombre de la categoría"))
            ));
    }

    @Test
    void getOne() throws Exception {
        // Given
        final String mockedCategory = "pizzas";
        final String mockedPathVariable = "/category/{name}";

        // When
        final ResultActions result = mockMvc.perform(get(URL_TEMPLATE.concat(mockedPathVariable),mockedCategory)
        .contentType(APPLICATION_JSON));
        
        // Then
        result.andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andDo(document("{class-name}/{method-name}", 
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            relaxedResponseFields(
                subsectionWithPath("data.category").description("Información de la categoría recuperada"),
                subsectionWithPath("data.category.name").description("Nombre de la categoría"))
        ));   
    }
    
    @Test
    void getAll() throws Exception {
        // Given

        // When
        final ResultActions result = mockMvc.perform(get(URL_TEMPLATE)
        .contentType(APPLICATION_JSON));

        // Then
        result.andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andDo(document("{class-name}/{method-name}", 
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                relaxedResponseFields(
                    subsectionWithPath("data.categories").description("Contiene toda la información de paginación"),
                    subsectionWithPath("data.categories.content").description("Información de categorías de la pagina actual"))
            ));
    }

    @Test
    void update() throws Exception {
        // Given
        final Long mockedId = 3L;
        final String mockedPathVariable = "/{id}";
        final String mockedContentJson = getUpdateCategoryAsJson();

       // When
       final ResultActions result = mockMvc.perform(put(URL_TEMPLATE.concat(mockedPathVariable), mockedId)
       .contentType(APPLICATION_JSON)
       .with(userToken())
       .content(mockedContentJson));

        // Then
        result.andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.data.category.id").value(mockedId));
    }

    @Test
    void deleteOne() throws Exception {
         // Given
         final Long mockedId = 2L;
         final String mockedPathVariable = "/{id}";

        // When
        final ResultActions result = mockMvc.perform(delete(URL_TEMPLATE.concat(mockedPathVariable), mockedId)
            .contentType(APPLICATION_JSON)
            .with(userToken()));

        // Then
        result.andExpect(status().isNoContent());
    }
}
