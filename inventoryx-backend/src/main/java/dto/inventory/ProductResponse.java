package dto.inventory;

public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String category;
    private String imageUrl;
    private boolean lowStock;

    public ProductResponse(Long id, String name, String description, double price, 
                         int quantity, String category, String imageUrl, boolean lowStock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.imageUrl = imageUrl;
        this.lowStock = lowStock;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isLowStock() {
        return lowStock;
    }
}