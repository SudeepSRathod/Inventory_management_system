package dto.inventory;

import java.util.Date;
import java.util.List;

public class OrderResponse {
    private Long id;
    private Long userId;
    private String username;
    private List<OrderItemDto> items;
    private Date orderDate;
    private String status;
    private String shippingAddress;
    private String notes;
    private double total;

    public static class OrderItemDto {
        private Long productId;
        private String productName;
        private int quantity;
        private double priceAtPurchase;

        public OrderItemDto(Long productId, String productName, int quantity, double priceAtPurchase) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
            this.priceAtPurchase = priceAtPurchase;
        }

        // Getters
        public Long getProductId() {
            return productId;
        }

        public String getProductName() {
            return productName;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getPriceAtPurchase() {
            return priceAtPurchase;
        }
    }

    public OrderResponse(Long id, Long userId, String username, List<OrderItemDto> items, 
                        Date orderDate, String status, String shippingAddress, 
                        String notes, double total) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.items = items;
        this.orderDate = orderDate;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.notes = notes;
        this.total = total;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getNotes() {
        return notes;
    }

    public double getTotal() {
        return total;
    }
}
