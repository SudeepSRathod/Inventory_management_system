package repository.inventory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import model.inventory.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategoryContainingIgnoreCase(String category);
    
    @Query("SELECT p FROM Product p WHERE p.quantity <= p.lowStockThreshold")
    List<Product> findLowStockProducts();
    
    List<Product> findAllByOrderByNameAsc();
    List<Product> findAllByOrderByPriceAsc();
    List<Product> findAllByOrderByQuantityAsc();
}