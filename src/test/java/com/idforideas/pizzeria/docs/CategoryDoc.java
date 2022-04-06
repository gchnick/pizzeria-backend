package com.idforideas.pizzeria.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
            new Category(1L, "Pizzas"),
            new Category(2L, "Empanadas"),
            new Category(3L, "Bebidas"),
            new Category(4L, "Postres"))
            .collect(Collectors.toList());
        
        this.categories = new PageImpl<>(categories);
        this.pageable = PageRequest.of(0, 2, Sort.by("name"));
    }

    @Test
    void getCategoriesWithPageable() throws Exception {
        // Given
        Mockito.when(categoryService.list(pageable)).thenReturn(categories);

        // When
        this.mockMvc.perform(get("/api/v1/categories").contentType(APPLICATION_JSON))
        // Then
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.data.categories").exists())
            .andExpect(jsonPath("$.data.categories.totalElements").value(4))
            .andDo(document("{class-name}/{method-name}"));

        Mockito.verify(categoryService).list(pageable);
    }
}
