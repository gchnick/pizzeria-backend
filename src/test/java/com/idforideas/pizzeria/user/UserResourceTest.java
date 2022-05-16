package com.idforideas.pizzeria.user;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.hamcrest.Matchers.containsString;
import static com.idforideas.pizzeria.user.UserMother.getNewUser002AsJson;
import static com.idforideas.pizzeria.user.UserMother.getNewUser003AsJson;

import com.idforideas.pizzeria.docs.support.MockBase;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

public class UserResourceTest extends MockBase {

    private static final String URL_TEMPLATE = "/api/v1/users";

    @Test
    void add() throws Exception {
        // Given
        final String mockedContentJson = getNewUser003AsJson();

        // When
        final ResultActions result = mockMvc.perform(post(URL_TEMPLATE)
        .with(userToken())
        .contentType(APPLICATION_JSON)
        .content(mockedContentJson));

        // Then
        result.andExpect(status().isCreated())
        .andExpect(header().string("Location", containsString(URL_TEMPLATE + "/3")))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.data.user.password").doesNotExist())
        .andDo(commonDocumentation().document(
            relaxedResponseFields(
                subsectionWithPath("data.user").description("Información de usuario creado"))
        ));    
    }

    @Test
    void getOne() throws Exception {
        //Given
        final Long mockedId = 1L;
        final String mockedPathVariable = "/{id}"; 
        
        // When
        final ResultActions result = mockMvc.perform(get(URL_TEMPLATE.concat(mockedPathVariable), mockedId)
        .contentType(APPLICATION_JSON)
        .with(userToken()));

        // Then
        result.andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.data.user.password").doesNotExist())
        .andDo(commonDocumentation().document(
            relaxedResponseFields(
                subsectionWithPath("data.user").description("Información del usuario recuperado")
        )));
        
    }

    @Test
    void getAll () throws Exception {
        //Given
        
       // When
       final ResultActions result = mockMvc.perform(get(URL_TEMPLATE)
       .contentType(APPLICATION_JSON)
       .with(userToken()));

       // Then
       result.andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.data.users[0].password").doesNotExist())
        .andDo(commonDocumentation().document(
            relaxedResponseFields(
                subsectionWithPath("data.users").description("Información de los usuario registrados")
        )));
    }

    @Test
    void update() throws Exception {
        // Given
        final Long mockedId = 2L;
        final String mockedPathVariable = "/{id}";
        final String mockedContentJson = getNewUser002AsJson();

        // When
        final ResultActions result = mockMvc.perform(put(URL_TEMPLATE.concat(mockedPathVariable), mockedId)
        .contentType(APPLICATION_JSON)
        .with(userToken())
        .content(mockedContentJson));
        
         // Then
         result.andExpect(status().isOk())
         .andExpect(content().contentType(APPLICATION_JSON))
         .andExpect(jsonPath("$.data.user.id").value(mockedId))
         .andExpect(jsonPath("$.data.user.password").doesNotExist());
    }

    @Test
    void deleteOne () throws Exception {
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
