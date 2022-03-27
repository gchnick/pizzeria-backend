package com.idforideas.pizzeria.product;

import static java.util.Map.of;
import static java.time.LocalDateTime.now;
import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.HeadersBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<Response> create(@RequestBody @Valid Product product) {
        return ResponseEntity.status(CREATED)
                    .body(
                        Response.builder()
                        .timeStamp(now())
                        .data(of("product", this.productService.create(product)))
                        .message("Product created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
                    );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getProduct(@RequestParam Long id) {
        Product product = this.productService.get(id).orElseThrow();
        return ResponseEntity.ok(
            Response.builder()
            .timeStamp(now())
            .data(of("product", product))
            .message("Product retrieved")
            .status(OK)
            .statusCode(OK.value())
            .build()
        );
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "name") String[] sort
    ) {
        List<Order> orders = getOrders(sort);
        return ResponseEntity.ok(
            Response.builder()
                .timeStamp(now())
                .data(of("products", this.productService.list(PageRequest.of(page, size, Sort.by(orders)))))
                .message("Products retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @DeleteMapping(value = "/{id}")
    public HeadersBuilder<?> delete(@RequestParam Long id) {
        this.productService.delete(id);
        return ResponseEntity.noContent();
    }

    private List<Order> getOrders(String[] sort) {
        return stream(sort)
            .map(s -> s.split(","))
            .map(s ->  {
                if(s.length == 1) {
                    return Order.by(s[0]);
                }
                if (s[1].contains("desc") || s[1].contains("asd")) {
                    return s[1].contains("asd") ? Order.asc(s[0]) : Order.desc(s[0]);
                }
                return null;
            }).toList();
    }
}
