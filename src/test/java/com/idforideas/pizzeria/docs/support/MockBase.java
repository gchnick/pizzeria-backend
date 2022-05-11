package com.idforideas.pizzeria.docs.support;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static com.idforideas.pizzeria.util.EnvVariable.PWD_TEST;
import static com.idforideas.pizzeria.util.EnvVariable.USER_TEST;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors;
import capital.scalable.restdocs.misc.AuthorizationSnippet;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class MockBase {

    private static String DEFAULT_AUTHORIZATION = "Recurso público.";
    
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    protected MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) throws Exception {
    this.mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .addFilters(springSecurityFilterChain)
        .alwaysDo(JacksonResultHandlers.prepareJackson(objectMapper))
        .alwaysDo(MockMvcRestDocumentation.document("{class-name}/{method-name}",
                preprocessRequest(
                    prettyPrint()),
                preprocessResponse(
                    ResponseModifyingPreprocessors.replaceBinaryContent(),
                    ResponseModifyingPreprocessors.limitJsonArrayLength(objectMapper),
                    prettyPrint())))
        .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                .uris()
                .withScheme("https")
                .withHost("donremolo-backend.herokuapp.com")
                .withPort(443)
                .and().snippets()
                .withDefaults(CliDocumentation.curlRequest(),
                        HttpDocumentation.httpRequest(),
                        HttpDocumentation.httpResponse(),
                        AutoDocumentation.requestFields(),
                        AutoDocumentation.pathParameters(),
                        AutoDocumentation.requestParameters(),
                        AutoDocumentation.description(),
                        AutoDocumentation.methodAndPath(),
                        AutoDocumentation.authorization(DEFAULT_AUTHORIZATION),
                        AutoDocumentation.modelAttribute(requestMappingHandlerAdapter.getArgumentResolvers())))
        .build();
    }

    protected RequestPostProcessor userToken() {
        return new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {

                    String accessToken;
            
                    try {
                            accessToken = getAccessToken(getenv(USER_TEST.name()), getenv(PWD_TEST.name()));
                    } catch (Exception e) {
                            throw new RuntimeException(e);
                    }

                    request.addHeader("Authorization", "Bearer ".concat(accessToken));
                    return AuthorizationSnippet.documentAuthorization(request, "Se requiere token de acceso de usuario.");
            }
        };
    }

    protected String getAccessToken(String email, String password) throws Exception {

        String body = mockMvc
            .perform(
                post("/api/v1/auth/token")
                    .contentType(APPLICATION_FORM_URLENCODED)
                    .param("email", email)
                    .param("password", password))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.data.tokens.accessToken").isNotEmpty())
            .andExpect(jsonPath("$.data.tokens.refreshToken").isNotEmpty())
            .andReturn().getResponse().getContentAsString();

            int beginning = body.indexOf("accessToken");
            beginning += "accessToken\":\"".length();
            int end = body.indexOf("\"", beginning);

        return body.substring(beginning, end);
    }

}