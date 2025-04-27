package service.inventory.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dto.inventory.OrderItemRequest;
import dto.inventory.OrderRequest;
import dto.inventory.OrderResponse;
import exception.ResourceNotFoundException;
import exception.UnauthorizedAccessException;
import model.auth.ERole;
import model.auth.User;
import model.inventory.Order;
import model.inventory.OrderItem;
import model.inventory.Product;
import repository.inventory.OrderRepository;
import repository.inventory.ProductRepository;
import service.inventory.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, 
                          ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest, User user) {
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setNotes(orderRequest.getNotes());

        orderRequest.getItems().forEach(itemRequest -> {
            Product product = productRepository.findById(itemRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            
            product.decreaseQuantity(itemRequest.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPriceAtPurchase(product.getPrice());
            
            order.addItem(orderItem);
        });

        return convertToOrderResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setStatus(status);
        return convertToOrderResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse getOrderById(Long orderId, User user) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        
        if (!order.getUser().getId().equals(user.getId()) && !isAdmin(user)) {
            throw new UnauthorizedAccessException("Unauthorized access");
        }
        
        return convertToOrderResponse(order);
    }

    private boolean isAdmin(User user) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getName() == ERole.ROLE_ADMIN);
    }

    @Override
    public List<OrderResponse> getAllOrdersForUser(User user) {
        return orderRepository.findByUser(user).stream()
                .map(this::convertToOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        
        order.getItems().forEach(item -> {
            Product product = item.getProduct();
            product.increaseQuantity(item.getQuantity());
            productRepository.save(product);
        });
        
        orderRepository.delete(order);
    }

    private OrderResponse convertToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getUser().getId(),
                order.getUser().getUsername(),
                order.getItems().stream()
                        .map(item -> new OrderResponse.OrderItemDto(
                                item.getProduct().getId(),
                                item.getProduct().getName(),
                                item.getQuantity(),
                                item.getPriceAtPurchase()
                        ))
                        .collect(Collectors.toList()),
                order.getOrderDate(),
                order.getStatus().name(),
                order.getShippingAddress(),
                order.getNotes(),
                order.getTotal()
        );
    }
}