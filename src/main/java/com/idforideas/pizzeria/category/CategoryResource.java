package com.idforideas.pizzeria.category;

import static java.util.Map.of;
import static java.time.LocalDateTime.now;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static com.idforideas.pizzeria.utils.SortUtil.getOrders;

import java.util.List;

import javax.validation.Valid;

import com.idforideas.pizzeria.utils.Response;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products/categories")
@RequiredArgsConstructor
public class CategoryResource {
    private final CategoryService categoryService;

    @PostMapping("/save")
    public ResponseEntity<Response> saveCategory(@RequestBody @Valid Category category) {
        return ResponseEntity.status(CREATED)
                    .body(
                        Response.builder()
                        .timeStamp(now())
                        .data(of("category", this.categoryService.create(category)))
                        .message("Category created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
                    );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getCategory(@RequestParam Long id) {
        Category category = this.categoryService.get(id).orElseThrow();
        return ResponseEntity.ok(
            Response.builder()
            .timeStamp(now())
            .data(of("category", category))
            .message("Category retrieved")
            .status(OK)
            .statusCode(OK.value())
            .build()
        );
    }

    @GetMapping("/{name}")
    public ResponseEntity<Response> getCategory(@RequestParam String name) {
        Category category = this.categoryService.get(name).orElseThrow();
        return ResponseEntity.ok(
            Response.builder()
            .timeStamp(now())
            .data(of("category", category))
            .message("Category retrieved")
            .status(OK)
            .statusCode(OK.value())
            .build()
        );
    }

    @GetMapping("")
    public ResponseEntity<Response> getCategories(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "name") String[] sort
    ) {
        List<Order> orders = getOrders(sort);
        return ResponseEntity.ok(
            Response.builder()
                .timeStamp(now())
                .data(of("categories", this.categoryService.list(PageRequest.of(page, size, by(orders)))))
                .message("Categories retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestParam Long id) {
        this.categoryService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
