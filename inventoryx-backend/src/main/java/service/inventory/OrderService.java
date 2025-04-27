package service.inventory;

import java.util.List;

import dto.inventory.OrderRequest;
import dto.inventory.OrderResponse;
import model.auth.User;
import model.inventory.Order;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest, User user);
    OrderResponse updateOrderStatus(Long orderId, Order.OrderStatus status);
    OrderResponse getOrderById(Long orderId, User user);
    List<OrderResponse> getAllOrdersForUser(User user);
    List<OrderResponse> getAllOrders();
    void deleteOrder(Long orderId);
}