package com.idforideas.pizzeria.auth;

import static com.idforideas.pizzeria.util.EnvVariable.PWD_TEST;
import static com.idforideas.pizzeria.util.EnvVariable.USER_TEST;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.idforideas.pizzeria.docs.support.MockBase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

public class AuthResourceTest extends MockBase {

    private String mockEmail;
    private String mockPassword;
    private final String URL_TEMPLATE = "/api/v1/auth/token";

    @BeforeEach
    void setUp() {
        this.mockEmail = System.getenv(USER_TEST.name());
        this.mockPassword = System.getenv(PWD_TEST.name());
    }

    @Test
    void login() throws Exception {
        // Given

        // When
        final ResultActions result = mockMvc
            .perform(
                post(URL_TEMPLATE)
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .param("email", mockEmail)
                    .param("password", mockPassword));

        // Then
        result.andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.data.tokens.accessToken").isNotEmpty())
            .andExpect(jsonPath("$.data.tokens.refreshToken").isNotEmpty());
    }

    @Test
    void refresh() throws Exception {
        // Given
        final String mockRefreshPath = "/refresh";
        final String mockPrefixAuth = "Bearer ";
        final String mockRefreshToken = getAccessToken(mockEmail, mockPassword);

        // When
        final ResultActions result = mockMvc
            .perform(
                post(URL_TEMPLATE.concat(mockRefreshPath))
                    .header("Authorization", mockPrefixAuth.concat(mockRefreshToken)));

        // Then
        result.andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.data.tokens.accessToken").isNotEmpty())
            .andExpect(jsonPath("$.data.tokens.refreshToken").value(mockRefreshToken));
    }
}
