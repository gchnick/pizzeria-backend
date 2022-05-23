package com.idforideas.pizzeria.order;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;

import java.net.URI;

import javax.validation.Valid;

import com.idforideas.pizzeria.util.Response;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderResource {

    private final OrderService orderService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Response> save(@RequestBody @Valid OrderDTO order) {
        Order createdOrder = orderService.create(order);
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdOrder.getId())
            .toUri();
        return ResponseEntity.created(uri)
            .body(
                Response.builder()
                .timeStamp(now())
                .data(of("order", createdOrder,
                    "grandTotal", createdOrder.grandTotal()))
                .message("Order created")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build()
        );
    }   
}
