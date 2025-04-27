package service.inventory;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import dto.inventory.ProductRequest;
import dto.inventory.ProductResponse;
import model.inventory.Product;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest, MultipartFile file);
    ProductResponse updateProduct(Long id, ProductRequest productRequest, MultipartFile file);
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long id);
    void deleteProduct(Long id);
    List<ProductResponse> searchProducts(String query);
    List<ProductResponse> getLowStockProducts();
    List<ProductResponse> getAllProductsSorted(String sortBy, String direction);
}