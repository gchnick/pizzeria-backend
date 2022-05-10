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

import static com.idforideas.pizzeria.product.ProductObjectMother.getNewProduct011AsJson;

import com.idforideas.pizzeria.docs.support.MockBase;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

public class ProductResourceTest extends MockBase {

    private static final String URL_TEMPLATE = "/api/v1/products";

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
}
