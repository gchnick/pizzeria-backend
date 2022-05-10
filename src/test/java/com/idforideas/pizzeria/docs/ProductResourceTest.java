package com.idforideas.pizzeria.docs;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
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

import static com.idforideas.pizzeria.product.ProductObjectMother.getNewProduct011AsJson;
import static com.idforideas.pizzeria.product.ProductObjectMother.getUpdateProduct011AsJson;

import com.idforideas.pizzeria.docs.support.MockBase;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;

public class ProductResourceTest extends MockBase {

    private static final String URL_TEMPLATE = "/api/v1/products";
    private static final String URL_TEMPLATE_V2 = "/api/v2/products";

    @Test
    void add() throws Exception {
        // Given
        final String mockedContentJson = getNewProduct011AsJson();

        // When
        final ResultActions result = mockMvc.perform(post(URL_TEMPLATE)
        .with(userToken())
        .contentType(APPLICATION_JSON)
        .content(mockedContentJson));

        // Then
        final String expectedLocationId = "/11";
        result.andExpect(status().isCreated())
        .andExpect(header().string("Location", containsString(URL_TEMPLATE.concat(expectedLocationId))))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andDo(document("{class-name}/{method-name}", 
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            relaxedResponseFields(
                subsectionWithPath("data.product").description("Información del producto creado"))
            ));
    }


    @Test
    void getOne() throws Exception {
        // Given
        final Long mockedId = 2L;
        final String mockedPathVariable = "/{id}";

        // When
        final ResultActions result = mockMvc.perform(get(URL_TEMPLATE.concat(mockedPathVariable),mockedId)
        .contentType(APPLICATION_JSON));
        
        // Then
        result.andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andDo(document("{class-name}/{method-name}", 
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            relaxedResponseFields(
                subsectionWithPath("data.product").description("Información del producto recuperado"))
        ));   
    }

    // TODO add test for create with picture
    @Test
    @Disabled
    void addWithPicture() throws Exception {
        // Given
        final String mockedName = "Pizza hawayana";
        final String mockedDescription = "Espectacular pizza con piña";
        final String mockedPrice = "13.85";
        final String mockedCategoryId = "1";
        final String mockedCategoryName = "Pizzas";
        final MultipartFile picture = null; // FIXME fix error
        final MockMultipartFile file = new MockMultipartFile(
            "file",
            "pictureTest.jpg",
            IMAGE_JPEG_VALUE,
            picture.getBytes());

           
        // When
        final ResultActions result = mockMvc.perform(multipart(URL_TEMPLATE_V2, file)
        .with(userToken())
        .contentType(MULTIPART_FORM_DATA)
        .param("name", mockedName)
        .param("description", mockedDescription)
        .param("price", mockedPrice)
        .param("category.id", mockedCategoryId)
        .param("category.name", mockedCategoryName)
        );

        // Then
        final String expectedLocationId = "/11";
        result.andExpect(status().isCreated())
        .andExpect(header().string("Location", containsString(URL_TEMPLATE.concat(expectedLocationId))))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.data.product.pictureURL").isNotEmpty())
        .andDo(document("{class-name}/{method-name}", 
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            relaxedResponseFields(
                subsectionWithPath("data.product").description("Información del producto creado"))
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
                    subsectionWithPath("data.products").description("Contiene toda la información de paginación"),
                    subsectionWithPath("data.products.content").description("Información de productos de la pagina actual"))
            ));
    }

    @Test
    void getAllByCategory() throws Exception {
        // Given
        final String mockedCategoryName = "pizzas";
        final String mockedPathVariable = "/category/{name}";

        // When
        final ResultActions result = mockMvc.perform(get(URL_TEMPLATE.concat(mockedPathVariable), mockedCategoryName)
        .contentType(APPLICATION_JSON));

        // Then
        final String expectedCategoryName = "Pizzas";
        result.andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.data.products.content[0].category.name").value(expectedCategoryName))
            .andDo(document("{class-name}/{method-name}", 
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                relaxedResponseFields(
                    subsectionWithPath("data.products").description("Contiene toda la información de paginación"),
                    subsectionWithPath("data.products.content").description("Información de productos por la categoría solicitada de la pagina actual"))
            ));
    }

    @Test
    void update() throws Exception {
        // Given
        final Long mockedId = 2L;
        final String mockedPathVariable = "/{id}";
        final String mockedContentJson = getUpdateProduct011AsJson();

       // When
       final ResultActions result = mockMvc.perform(put(URL_TEMPLATE.concat(mockedPathVariable), mockedId)
       .contentType(APPLICATION_JSON)
       .with(userToken())
       .content(mockedContentJson));

        // Then
        result.andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.data.product.id").value(mockedId));
    }

    @Test
    void patchOne() throws Exception {
        // Given
        final Long mockedId = 1L;
        final String mockedPathVariable = "/{id}";
        final String APPLICATION_JSON_PATCH = "application/json-patch+json";
        final String mockedContentJsonPatch = "[{ \"op\": \"replace\", \"path\": \"/name\", \"value\": \"Pizza napolitana\" }, { \"op\": \"replace\", \"path\": \"/price\", \"value\": 14.25 }]";

        // When
        ResultActions result = mockMvc.perform(patch(URL_TEMPLATE.concat(mockedPathVariable), mockedId)
        .contentType(APPLICATION_JSON_PATCH)
        .with(userToken())
        .content(mockedContentJsonPatch));

        // Then
        final String expectedName = "Pizza napolitana";
        final float expectedPrice = 14.25F;
        result.andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.data.product.name").value(expectedName))
        .andExpect(jsonPath("$.data.product.price").value(expectedPrice));
    }

    @Test
    void deleteOne() throws Exception {
         // Given
         final Long mockedId = 3L;
         final String mockedPathVariable = "/{id}";

        // When
        final ResultActions result = mockMvc.perform(delete(URL_TEMPLATE.concat(mockedPathVariable), mockedId)
            .contentType(APPLICATION_JSON)
            .with(userToken()));

        // Then
        result.andExpect(status().isNoContent());
    }
}
