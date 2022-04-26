package com.idforideas.pizzeria.docs.support;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static com.idforideas.pizzeria.security.CustomEnvironmentVariables.USER_TEST;
import static com.idforideas.pizzeria.security.CustomEnvironmentVariables.PWD_TEST;
import static java.lang.System.getenv;


import javax.servlet.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
// import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors;
import capital.scalable.restdocs.misc.AuthorizationSnippet;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class MockBase {

    private static String DEFAULT_AUTHORIZATION = "Resource is public.";
    
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Filter springSecurityFilterChain;

    // @Autowired
    // private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    protected MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) throws Exception {
    this.mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .addFilters(springSecurityFilterChain)
        .alwaysDo(JacksonResultHandlers.prepareJackson(objectMapper))
        .alwaysDo(MockMvcRestDocumentation.document("{class-name}/{method-name}",
                Preprocessors.preprocessRequest(
                    prettyPrint()),
                Preprocessors.preprocessResponse(
                    ResponseModifyingPreprocessors.replaceBinaryContent(),
                    ResponseModifyingPreprocessors.limitJsonArrayLength(objectMapper),
                    prettyPrint())))
        .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                .uris()
                .withScheme("https")
                .withHost("donremolo-backend.herokuapp.com")
                .and().snippets()
                .withDefaults(CliDocumentation.curlRequest(),
                        HttpDocumentation.httpRequest(),
                        HttpDocumentation.httpResponse(),
                        AutoDocumentation.requestFields(),
                        AutoDocumentation.responseFields(),
                        AutoDocumentation.pathParameters(),
                        AutoDocumentation.requestParameters(),
                        AutoDocumentation.description(),
                        AutoDocumentation.methodAndPath(),
                        AutoDocumentation.section(),
                        AutoDocumentation.authorization(DEFAULT_AUTHORIZATION)/*,
                        AutoDocumentation.modelAttribute(requestMappingHandlerAdapter.getArgumentResolvers())*/))
        .build();
    }

    protected RequestPostProcessor userToken() {
        return new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {

                    String accessToken;
            
                    try {
                            accessToken = getAccessToken(getenv(USER_TEST), getenv(PWD_TEST));
                    } catch (Exception e) {
                            throw new RuntimeException(e);
                    }

                    request.addHeader("Authorization", "Bearer " + accessToken);
                    return AuthorizationSnippet.documentAuthorization(request, "User access token required.");
            }
        };
    }

    private String getAccessToken(String email, String password) throws Exception {

        String body = mockMvc
            .perform(
                post("/api/v1/oauth/token")
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .param("email", email)
                    .param("password", password)
                    // .param("grant_type", "password")
                    // .param("scope", "read write")
                    // .param("client_id", "app")
                    /* .param("client_secret", "very_secret")*/)
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            // .andExpect(jsonPath("$.access_token", `is`(notNullValue())))
            // .andExpect(jsonPath("$.token_type", `is`(equalTo("bearer"))))
            // .andExpect(jsonPath("$.refresh_token", `is`(notNullValue())))
            // .andExpect(jsonPath("$.expires_in", `is`(greaterThan(4000))))
            // .andExpect(jsonPath("$.scope", `is`(equalTo("read write"))))
            .andReturn().getResponse().toString();

        return body.substring(17, 53);
    }

}