package com.idforideas.pizzeria.product;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.idforideas.pizzeria.exception.ConflictException;
import com.idforideas.pizzeria.util.Response;
import com.idforideas.pizzeria.util.SortUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductResource {
    private final ProductService productService;
    private final SortUtil sort;

    @Value("${config.uploads.path}")
    private String path;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Response> saveProduct(@RequestBody @Valid Product product) {
            return ResponseEntity.status(CREATED)
                        .body(
                            Response.builder()
                            .timeStamp(now())
                            .data(of("product", productService.create(product)))
                            .message("Product created")
                            .status(CREATED)
                            .statusCode(CREATED.value())
                            .build()
                        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "/with-picture", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response> saveProductWithPicture(@Valid Product product,
        @RequestPart(required = true) MultipartFile file) throws IllegalStateException, IOException {
            product.setPictureURL( parsePathPicture(file.getOriginalFilename()) );
            file.transferTo(new File(path.concat(product.getPictureURL())));
            return ResponseEntity.status(CREATED)
                .body(
                    Response.builder()
                    .timeStamp(now())
                    .data(of("product", productService.create(product)))
                    .message("Product created")
                    .status(CREATED)
                    .statusCode(CREATED.value())
                    .build()
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(
            Response.builder()
            .timeStamp(now())
            .data(of("product", productService.get(id)))
            .message("Product retrieved")
            .status(OK)
            .statusCode(OK.value())
            .build()
        );
    }

    @GetMapping
    public ResponseEntity<Response> getProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "category") String[] sort
    ) {
        List<Order> orders = this.sort.getOrders(sort);
        return ResponseEntity.ok(
            Response.builder()
                .timeStamp(now())
                .data(of("products", productService.list(PageRequest.of(page, size, by(orders)))))
                .message("Products retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<Response> getProductsByCategory(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "name") String[] sort,
        @PathVariable("name") String CategoryName
    ) {
        List<Order> orders = this.sort.getOrders(sort);
        return ResponseEntity.ok(
            Response.builder()
                .timeStamp(now())
                .data(of("products", productService.findByCategoryName(CategoryName, PageRequest.of(page, size, by(orders)))))
                .message("Products by category retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateProduct(@RequestBody @Valid Product newProduct, @PathVariable Long id) {
        return productService.getWithOptional(id).map(product -> {
            product.setName(newProduct.getName());
            product.setDescription(newProduct.getDescription());
            product.setPrice(newProduct.getPrice());
            product.setPictureURL(newProduct.getPictureURL());
            product.setCategory(newProduct.getCategory());
            return ResponseEntity.ok(
                Response.builder()
                    .timeStamp(now())
                    .data(of("product", productService.update(product)))
                    .message("Product updated")
                    .status(OK)
                    .statusCode(OK.value())
                    .build()
            );
        }).orElseGet(() -> {
            return ResponseEntity.status(CREATED).body(
                Response.builder()
                    .timeStamp(now())
                    .data(of("product", productService.create(newProduct)))
                    .message("Product created")
                    .status(CREATED)
                    .statusCode(CREATED.value())
                    .build()
            );
        });
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Response> updateParcial(@PathVariable Long id, @RequestBody JsonPatch patch) {
        Product product = productService.get(id);
        Product patched = applyPatchToProduct(patch, product);
        return ResponseEntity.ok().body(
            Response.builder()
            .timeStamp(now())
            .data(of("product", productService.update(patched)))
            .message("Product patched")
            .status(OK)
            .statusCode(OK.value())
            .build()
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    private String parsePathPicture(String fileName) {
        String parse = fileName.replace(" ", "-")
            .replace(":", "")
            .replace("\\", "");
        return new StringBuilder().append(UUID.randomUUID().toString())
            .append("-")
            .append(parse)
            .toString();
    }

    private Product applyPatchToProduct(JsonPatch patch, Product targetProduct) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched;
        Product productPatched;
        try {
            patched = patch.apply(objectMapper.convertValue(targetProduct, JsonNode.class));
            productPatched = objectMapper.treeToValue(patched, Product.class);
        } catch (JsonPatchException | JsonProcessingException e) {
            e.printStackTrace();
            throw new ConflictException("Error applying patch to product");
        }
        return productPatched;
    }
}
