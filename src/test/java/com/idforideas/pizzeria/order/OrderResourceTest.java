package com.idforideas.pizzeria.order;

import static com.idforideas.pizzeria.order.OrderMother.newOrderOfJuanAsJSON;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.hamcrest.Matchers.containsString;

import com.idforideas.pizzeria.docs.support.MockBase;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

public class OrderResourceTest extends MockBase {

    private final String URL_TEMPLATE = "/api/v1/orders";

    @Test
    void add() throws Exception {
        // Given
        String mockOrder = newOrderOfJuanAsJSON();

        // When
        ResultActions result = mockMvc.perform(
            post(URL_TEMPLATE)
            .with(userToken())
            .contentType(APPLICATION_JSON)
            .content(mockOrder));

        // Then
        final String expectedLocationId = "/1";
        result.andExpect(status().isCreated())
        .andExpect(header().string("Location", containsString(URL_TEMPLATE.concat(expectedLocationId))))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andDo(commonDocumentation().document(
            relaxedResponseFields(
                subsectionWithPath("data.order").description("Información del pedido creado")
        )));
    }
}
