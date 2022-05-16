package com.idforideas.pizzeria.product.v2;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.validation.Valid;

import com.idforideas.pizzeria.product.Product;
import com.idforideas.pizzeria.product.ProductService;
import com.idforideas.pizzeria.util.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController(value = "ProductResourceV2")
@RequestMapping("/api/v2/products")
@RequiredArgsConstructor
public class ProductResource {
    private final ProductService productService;

    @Value("${config.uploads.path}")
    private String path;

    /**
     * Añadir un nuevo <b>producto</b> junto con información de su imagen
     * @param product Información del producto
     * @param file Imagen del producto
     * @return {@link Response}
     * @throws IOException Error al procesar la imagen
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response> saveWithPicture(@Valid Product product,
        @RequestPart(required = true) MultipartFile file) throws  IOException {
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

    private String parsePathPicture(String fileName) {
        String parse = fileName.replace(" ", "-")
            .replace(":", "")
            .replace("\\", "");
        return new StringBuilder().append(UUID.randomUUID().toString())
            .append("-")
            .append(parse)
            .toString();
    }
}
