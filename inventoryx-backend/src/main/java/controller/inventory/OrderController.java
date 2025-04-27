package controller.inventory;

import java.util.List;
import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import dto.inventory.OrderRequest;
import dto.inventory.OrderResponse;
import model.auth.User;
import model.inventory.Order;
import service.inventory.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public OrderResponse createOrder(
            @RequestBody OrderRequest orderRequest,
            @AuthenticationPrincipal User user) {
        return orderService.createOrder(orderRequest, user);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public OrderResponse getOrderById(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        return orderService.getOrderById(id, user);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<OrderResponse> getMyOrders(@AuthenticationPrincipal User user) {
        return orderService.getAllOrdersForUser(user);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public OrderResponse updateOrderStatus(
            @PathVariable Long id,
            @RequestParam Order.OrderStatus status) {
        return orderService.updateOrderStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
}