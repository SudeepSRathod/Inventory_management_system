package model.inventory;

import model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "products")
@Getter
@Setter
@ToString(callSuper = true)
public class Product extends BaseEntity {

    @NotBlank(message = "Product name is required")
    @Size(max = 100, message = "Product name must be less than 100 characters")
    @Column(nullable = false, unique = true)
    private String name;

    @Size(max = 500, message = "Description must be less than 500 characters")
    @Column(length = 500)
    private String description;

    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private double price;

    @Min(value = 0, message = "Quantity cannot be negative")
    @Column(nullable = false)
    private int quantity;

    @Size(max = 50, message = "Category must be less than 50 characters")
    private String category;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Min(value = 0, message = "Low stock threshold cannot be negative")
    @Column(name = "low_stock_threshold", nullable = false)
    private int lowStockThreshold = 5;

    @Version
    private Long version;  // For optimistic locking

    // Business logic methods
    
    /**
     * Checks if product is low on stock
     * @return true if quantity is at or below threshold
     */
    public boolean isLowStock() {
        return quantity <= lowStockThreshold;
    }

    /**
     * Checks if product is out of stock
     * @return true if quantity is zero or negative
     */
    public boolean isOutOfStock() {
        return quantity <= 0;
    }

    /**
     * Decreases product quantity by specified amount
     * @param amount quantity to decrease
     * @throws IllegalArgumentException if amount is negative
     * @throws IllegalStateException if insufficient stock
     */
    public void decreaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Decrease amount must be positive");
        }
        if (this.quantity < amount) {
            throw new IllegalStateException("Insufficient stock available");
        }
        this.quantity -= amount;
    }

    /**
     * Increases product quantity by specified amount
     * @param amount quantity to increase
     * @throws IllegalArgumentException if amount is negative
     */
    public void increaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Increase amount must be positive");
        }
        this.quantity += amount;
    }

    /**
     * Calculates total inventory value
     * @return price multiplied by quantity
     */
    public double calculateValue() {
        return price * quantity;
    }

    /**
     * Checks if product belongs to specified category
     * @param category category to check
     * @return true if product belongs to category (case-insensitive)
     */
    public boolean belongsToCategory(String category) {
        return this.category != null && this.category.equalsIgnoreCase(category);
    }

    // Explicit getters and setters for fields that need special handling
    
    @Override
    public Long getId() {
        return super.getId();
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.price = price;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        this.name = name;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getLowStockThreshold() {
		return lowStockThreshold;
	}

	public void setLowStockThreshold(int lowStockThreshold) {
		this.lowStockThreshold = lowStockThreshold;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}
    
}