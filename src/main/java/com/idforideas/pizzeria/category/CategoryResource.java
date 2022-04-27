package com.idforideas.pizzeria.category;

import static java.util.Map.of;
import static java.time.LocalDateTime.now;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.idforideas.pizzeria.util.Response;
import com.idforideas.pizzeria.util.SortUtil;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryResource {
    private final CategoryService categoryService;
    private final SortUtil sort;

    /**
     * Añadir nueva <b>categoría</b>
     * @param category Información de la categoría
     * @return {@link Response}
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Response> saveCategory(@RequestBody @Valid Category category) {
        Category createdCategory = categoryService.create(category);
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("{/id}")
            .buildAndExpand(createdCategory.getId())
            .toUri();
        return ResponseEntity.created(uri)
                    .body(
                        Response.builder()
                        .timeStamp(now())
                        .data(of("category", createdCategory))
                        .message("Category created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
                    );
    }

    /**
     * Devuelve una categoría por ID
     * @param id ID de la categoría a recuperar
     * @return {@link Response}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Response> getCategory(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
            Response.builder()
            .timeStamp(now())
            .data(of("category", categoryService.get(id)))
            .message("Category retrieved")
            .status(OK)
            .statusCode(OK.value())
            .build()
        );
    }

    /**
     * Devuelve una categoría por nombre
     * @param name Nombre de la categoría a recuperar. No se hace distinción entre mayúsculas o minúsculas
     * @return {@link Response}
     */
    @GetMapping("/category/{name}")
    public ResponseEntity<Response> getCategory(@PathVariable("name") String name) {
        return ResponseEntity.ok(
            Response.builder()
            .timeStamp(now())
            .data(of("category", categoryService.get(name)))
            .message("Category retrieved")
            .status(OK)
            .statusCode(OK.value())
            .build()
        );
    }

    /**
     * Devuelve una lista paginada de categorías
     * @return {@link Response}
     */
    @GetMapping
    public ResponseEntity<Response> getCategories(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "name") String[] sort
    ) {
        List<Order> orders = this.sort.getOrders(sort);
        return ResponseEntity.ok(
            Response.builder()
                .timeStamp(now())
                .data(of("categories", categoryService.list(PageRequest.of(page, size, by(orders)))))
                .message("Categories retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    /**
     * 
     * @param id
     * @param newCategory
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateCategory(@PathVariable Long id, @RequestBody @Valid Category newCategory) {
        return categoryService.getAsOptional(id).map(category -> {
            categoryService.valid(newCategory);
            category.setName(newCategory.getName());
            return ResponseEntity.ok(
                Response.builder()
                .timeStamp(now())
                .data(of("category", categoryService.update(category)))
                .message("Category updated")
                .status(OK)
                .statusCode(OK.value())
                .build()
            );
        }).orElseGet(() -> {
            Category createdCategory = categoryService.create(newCategory);
            URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{/id}")
                .buildAndExpand(createdCategory.getId())
                .toUri();
            return ResponseEntity.created(uri).body(
                Response.builder()
                    .timeStamp(now())
                    .data(of("category", createdCategory))
                    .message("Category created")
                    .status(CREATED)
                    .statusCode(CREATED.value())
                    .build()
            );
        });
    }

    /**
     * 
     * @param id
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
