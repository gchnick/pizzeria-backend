package com.idforideas.pizzeria.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.idforideas.pizzeria.category.Category;
import com.idforideas.pizzeria.category.CategoryService;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
public class CategoryDoc {
    private final String URL = "/api/v1/categories?page=0&size=2&sort=name,desc";
    private final int TOTAL_ELEMENTS = 4;

    @MockBean
    private CategoryService categoryService;
    
    private MockMvc mockMvc;
    private Page<Category> categories;
    private Pageable pageable;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
        
        List<Category> categories = Stream.of(
            new Category("Pizzas"),
            new Category("Empanadas"),
            new Category("Bebidas"),
            new Category("Postres"))
            .collect(Collectors.toList());
        
        this.categories = new PageImpl<>(categories);
        this.pageable = PageRequest.of(0, 2, Sort.by("name").descending());
    }

    @Test
    void getCategoriesWithPageable() throws Exception {
        // Given
        Mockito.when(categoryService.list(pageable)).thenReturn(categories);

        // When
        this.mockMvc.perform(get(URL).contentType(APPLICATION_JSON))
        // Then
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.data.categories").exists())
            .andExpect(jsonPath("$.data.categories.totalElements").value(TOTAL_ELEMENTS))
            .andDo(document("{class-name}/{method-name}",
                preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("page").description("Numero de la pagina a recuperar"),
                    parameterWithName("size").description("Elementos por pagina"),
                    parameterWithName("sort").description("Propiedad que se usara como referencia para ordenar")
            )));

        Mockito.verify(categoryService).list(pageable);
    }
}
