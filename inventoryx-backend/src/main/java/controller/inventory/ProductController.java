package controller.inventory;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import dto.inventory.ProductRequest;
import dto.inventory.ProductResponse;
import service.inventory.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse createProduct(
            @RequestPart("product") ProductRequest productRequest,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        return productService.createProduct(productRequest, file);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse updateProduct(
            @PathVariable Long id,
            @RequestPart("product") ProductRequest productRequest,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        return productService.updateProduct(id, productRequest, file);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<ProductResponse> searchProducts(@RequestParam String query) {
        return productService.searchProducts(query);
    }

    @GetMapping("/low-stock")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ProductResponse> getLowStockProducts() {
        return productService.getLowStockProducts();
    }

    @GetMapping("/sorted")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<ProductResponse> getAllProductsSorted(
            @RequestParam String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return productService.getAllProductsSorted(sortBy, direction);
    }
}