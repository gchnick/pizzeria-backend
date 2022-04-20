package com.idforideas.pizzeria.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.idforideas.pizzeria.category.Category;
import com.idforideas.pizzeria.category.CategoryService;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class ApiRestDocumentation {
        
    MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation)
                .operationPreprocessors()
                    .withRequestDefaults(prettyPrint())
                    .withResponseDefaults(prettyPrint())).build();
    }

    @Tag("Category")
    @Nested
    class categoryResource {
        final String URL_TEMPLATE = "/api/v1/categories";
        final int TOTAL_ELEMENTS = 4;
        Page<Category> categories;
        Pageable pageable;

        @MockBean
        CategoryService categoryService;

        @BeforeEach
        void setUp() {
            List<Category> categories = Stream.of(
                new Category("Pizzas"),
                new Category("Empanadas"),
                new Category("Bebidas"),
                new Category("Postres"))
                .collect(Collectors.toList());
    
            ListIterator<Category> iterator = categories.listIterator();
            while(iterator.hasNext()) {
                Long id = Long.valueOf( Integer.valueOf(iterator.nextIndex()).longValue() );
                iterator.next().setId(id);
            }
            
            this.pageable = PageRequest.of(0, 2, Sort.by("name").descending());
            this.categories = new PageImpl<>(categories, pageable, categories.size());
        }

        @Tag("GET")
        @Test
        void getList() throws Exception {
            final String VARIABLES = "?page=0&size=2&sort=name,desc";

            // Given
            when(categoryService.list(pageable)).thenReturn(categories);

            // When
            mockMvc.perform(get(URL_TEMPLATE + VARIABLES).contentType(APPLICATION_JSON))
            // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.data.categories").exists())
                .andExpect(jsonPath("$.data.categories.numberOfElements").value(2))
                .andExpect(jsonPath("$.data.categories.totalElements").value(TOTAL_ELEMENTS))
                .andDo(document("{class-name}/{method-name}",requestParameters(
                        parameterWithName("page")
                        .description("Numero de pagina a solicitar. Por defecto su valor es 0 (primera pagina)"),
                        parameterWithName("size")
                        .description("Cantidad de categorias por pagina. Por defecto su valor es 5"),
                        parameterWithName("sort")
                        .description("Nombre del campo que se usara para ordenar la lista. Por defecto se ordena la lista por nombre de categoria de manera ascendente")),
                        relaxedResponseFields(
                            fieldWithPath("data.categories.totalPages")
                            .description("Cantidad total de paginas disponibles"),
                            fieldWithPath("data.categories.totalElements")
                            .description("Total de categorias registradas"),
                            fieldWithPath("data.categories.pageable.pageNumber")
                            .description("Pagina de categorias actual"),
                            fieldWithPath("data.categories.numberOfElements")
                            .description("Cantidad de categorias en la pagina actual"),
                            fieldWithPath("data.categories.content")
                            .description("Lista de categorias de la pagina actual")
                        )
                ));
        }
    }
}
