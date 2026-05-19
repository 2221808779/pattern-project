package com.subscriptionbox.service;

import com.subscriptionbox.model.Box;
import com.subscriptionbox.model.Order;
import com.subscriptionbox.model.User;
import com.subscriptionbox.singleton.OrderManager;

import java.util.List;

public class OrderService {
    private final OrderManager orderManager;

    public OrderService() {
        this.orderManager = OrderManager.getInstance();
    }

    public Order createOrder(User user, Box box, double amount) {
        System.out.println("[SERVICE] Creating order for " + user.getName() + "...");
        Order order = new Order(user, box, amount);
        orderManager.addOrder(order);
        System.out.println("[SERVICE] Order created: " + order);
        return order;
    }

    public List<Order> getAllOrders() {
        return orderManager.getOrders();
    }
}
