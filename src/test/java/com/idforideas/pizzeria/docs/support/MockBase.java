package com.idforideas.pizzeria.docs.support;

import static com.idforideas.pizzeria.util.EnvVariable.PWD_TEST;
import static com.idforideas.pizzeria.util.EnvVariable.USER_TEST;
import static java.lang.System.getenv;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.replaceBinaryContent;
import static capital.scalable.restdocs.response.ResponseModifyingPreprocessors.limitJsonArrayLength;


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
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import capital.scalable.restdocs.misc.AuthorizationSnippet;
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class MockBase {

    private static String DEFAULT_AUTHORIZATION = "Recurso público.";
    private static String STRING_AUTHORIZATION = "Se requiere token de acceso de administrador.";
    private static String COMMON_IDENTIFIER = "{class-name}/{method-name}";
    private static String SCHEME = "https";
    private static String HOST = "donremolo-backend.herokuapp.com";
    private static int PORT = 443;
    private static String URL_LOGIN = "/api/v1/auth/token";
    
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
        .alwaysDo(MockMvcRestDocumentation.document(COMMON_IDENTIFIER,
                preprocessRequest(
                    prettyPrint()),
                preprocessResponse(
                    ResponseModifyingPreprocessors.replaceBinaryContent(),
                    ResponseModifyingPreprocessors.limitJsonArrayLength(objectMapper),
                    prettyPrint())))
        .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                .uris()
                .withScheme(SCHEME)
                .withHost(HOST)
                .withPort(PORT)
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

    protected RestDocumentationResultHandler commonDocumentation(Snippet... snippets) {
        return document(COMMON_IDENTIFIER, commonRequestPreprocessor(), commonResponsePreprocessor(), snippets);
    }

    protected OperationRequestPreprocessor commonRequestPreprocessor() {
        return preprocessRequest(replaceBinaryContent(), limitJsonArrayLength(objectMapper), prettyPrint());
    }

    protected OperationResponsePreprocessor commonResponsePreprocessor() {
        return preprocessResponse(replaceBinaryContent(), limitJsonArrayLength(objectMapper), prettyPrint());
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
                    return AuthorizationSnippet.documentAuthorization(request, STRING_AUTHORIZATION);
            }
        };
    }

    protected String getAccessToken(String email, String password) throws Exception {

        String body = mockMvc
            .perform(
                post(URL_LOGIN)
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