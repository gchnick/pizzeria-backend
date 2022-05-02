package com.idforideas.pizzeria.product;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.idforideas.pizzeria.category.Category;
import com.idforideas.pizzeria.category.CategoryService;
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
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * @author Nick Galán
 */
@RestController()
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductResource {
    private final ProductService productService;
    private final CategoryService categoryService;

    private final SortUtil sort;

    @Value("${config.uploads.path}")
    private String path;

    /**
     * Añadir un nuevo <b>producto</b> sin imagen. Si desea añadir un nuevo producto junto con su imagen vaya a la version 2 del <code>endpoint</code> 
     * @param product Información del producto
     * @return {@link Response}
     */
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

    /**
     * Devuelve un producto por ID
     * @param id ID del producto a recuperar
     * @return {@link Response}
     */
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

    /**
     * Devuelve una lista paginada de productos
     * @param page Número de la página a recuperar comenzado por 0
     * @param size Tamaño de la página. Cantidad máxima de productos por página
     * @param sort Propiedad usada para ordenar la lista. Dirección de ordenamiento.
     * @return {@link Response}
     */
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

    /**
     * Devuelve una lista paginada de productos de una categoría
     * @param page Número de la página a recuperar comenzado por 0
     * @param size Tamaño de la página. Cantidad máxima de productos por página
     * @param sort Propiedad usada para ordenar la lista. Dirección de ordenamiento.
     * @param categoryName Nombre de la categoría que clasifica los productos a recuperar 
     * @return {@link Response}
     */
    @GetMapping("/category/{name}")
    public ResponseEntity<Response> getProductsByCategory(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "name") String[] sort,
        @PathVariable("name") String categoryName
    ) {
        List<Order> orders = this.sort.getOrders(sort);
        Category category = categoryService.get(categoryName);    
        return ResponseEntity.ok(
            Response.builder()
                .timeStamp(now())
                .data(of("products", productService.findByCategory(category, PageRequest.of(page, size, by(orders)))))
                .message("Products by category retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    /**
     * Actualiza todos los campos del producto al que pertenece el ID, con la nueva información. En caso de no existir un producto con el ID suministrado se procederá a crear un nuevo producto
     * @param newProduct Nueva información del producto para aplicar en la actualización
     * @param id ID del producto a actualizar
     * @return {@link Response}
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateProduct(@RequestBody @Valid Product newProduct, @PathVariable Long id) {
        return productService.getAsOptional(id).map(product -> {
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

    /**
     * Actualiza los campos específicos del producto al que pertenece el ID
     * @param id ID del producto a parchar
     * @param patch JsonPath con la información y las acciones a aplicar
     * @see <a>enlace</a>
     * @return {@link Response}
     */
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

    /**
     * Elimina el producto al que corresponde el ID suministrado.
     * @param id ID del producto a eliminar
     * @return Sin contenido
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.status(NO_CONTENT).build();
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
