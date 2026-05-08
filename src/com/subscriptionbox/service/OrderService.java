package com.subscriptionbox.service;

import com.subscriptionbox.model.Box;
import com.subscriptionbox.model.Order;
import com.subscriptionbox.model.User;
import com.subscriptionbox.singleton.OrderManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderService - Handles order creation and status management.
 * Coordinates with OrderManager (Singleton) and notifies observers on changes.
 */
public class OrderService {
    private final OrderManager orderManager;

    public OrderService() {
        this.orderManager = OrderManager.getInstance();
    }

    /**
     * Creates a new order for a user's subscription.
     */
    public Order createOrder(User user, Box box, double amount) {
        System.out.println("[SERVICE] Creating order for " + user.getName() + "...");
        Order order = new Order(user, box, amount);
        orderManager.addOrder(order);
        System.out.println("[SERVICE] Order created: " + order);
        return order;
    }

    /**
     * Returns all orders in the system.
     */
    public List<Order> getAllOrders() {
        return orderManager.getOrders();
    }

    /**
     * Gets orders for a specific user.
     */
    public List<Order> getOrdersByUser(User user) {
        return orderManager.getOrders().stream()
                .filter(o -> o.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Updates order status (triggers observer notifications).
     */
    public void updateOrderStatus(String orderId, com.subscriptionbox.model.Order.OrderStatus status) {
        orderManager.updateOrderStatus(orderId, status);
    }

    /**
     * Simulates order progression through all statuses.
     */
    public void processOrder(String orderId) {
        System.out.println("[SERVICE] Processing order: " + orderId);
        updateOrderStatus(orderId, com.subscriptionbox.model.Order.OrderStatus.PENDING);
        updateOrderStatus(orderId, com.subscriptionbox.model.Order.OrderStatus.SHIPPED);
        updateOrderStatus(orderId, com.subscriptionbox.model.Order.OrderStatus.DELIVERED);
    }
}
