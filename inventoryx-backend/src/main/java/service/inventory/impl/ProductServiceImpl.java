package service.inventory.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dto.inventory.ProductRequest;
import dto.inventory.ProductResponse;
import exception.ResourceNotFoundException;
import model.inventory.Product;
import repository.inventory.ProductRepository;
import service.inventory.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest, MultipartFile file) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setCategory(productRequest.getCategory());
        product.setLowStockThreshold(productRequest.getLowStockThreshold());

        if (file != null && !file.isEmpty()) {
            String imageUrl = saveImage(file);
            product.setImageUrl(imageUrl);
        }

        Product savedProduct = productRepository.save(product);
        return convertToProductResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest, MultipartFile file) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setCategory(productRequest.getCategory());
        product.setLowStockThreshold(productRequest.getLowStockThreshold());

        if (file != null && !file.isEmpty()) {
            String imageUrl = saveImage(file);
            product.setImageUrl(imageUrl);
        }

        Product updatedProduct = productRepository.save(product);
        return convertToProductResponse(updatedProduct);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return convertToProductResponse(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        // Delete associated image if exists
        if (product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
            try {
                Path filePath = Paths.get(UPLOAD_DIR + product.getImageUrl().replace("/uploads/", ""));
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete product image: " + e.getMessage());
            }
        }
        
        productRepository.delete(product);
    }

    @Override
    public List<ProductResponse> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCase(query).stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getLowStockProducts() {
        return productRepository.findLowStockProducts().stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getAllProductsSorted(String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
                   Sort.by(sortBy).ascending() : 
                   Sort.by(sortBy).descending();
                   
        return productRepository.findAll(sort).stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
    }

    private String saveImage(MultipartFile file) {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(uploadPath.getParent());
            Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image: " + e.getMessage());
        }
    }

    private ProductResponse convertToProductResponse(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getQuantity(),
            product.getCategory(),
            product.getImageUrl(),
            product.isLowStock()
        );
    }
}