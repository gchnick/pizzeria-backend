package com.idforideas.pizzeria.docs;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors;

public class ApiRestDocumentation {
    
    @Autowired
    private WebApplicationContext context;

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @BeforeEach
    public void setUp() throws Exception {
        // org.junit.ru
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .alwaysDo(JacksonResultHandlers.prepareJackson(objectMapper))
                .alwaysDo(MockMvcRestDocumentation.document("{class-name}/{method-name}",
                        Preprocessors.preprocessRequest(),
                        Preprocessors.preprocessResponse(
                                ResponseModifyingPreprocessors.replaceBinaryContent(),
                                ResponseModifyingPreprocessors.limitJsonArrayLength(objectMapper),
                                Preprocessors.prettyPrint())))
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                        .uris()
                        .withScheme("https")
                        .withHost("donremolo-backend.herokuapp.com")
                        .withPort(8080)
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
                                AutoDocumentation.section()))
                .build();
    }
}