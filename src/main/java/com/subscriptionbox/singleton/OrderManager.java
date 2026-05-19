package com.subscriptionbox.singleton;

import com.subscriptionbox.model.Order;
import com.subscriptionbox.model.Order.OrderStatus;
import com.subscriptionbox.observer.Observer;
import com.subscriptionbox.observer.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderManager - Singleton Pattern implementation.
 * Manages all orders and acts as the Subject in the Observer Pattern.
 * Notifies observers when order status changes.
 */
public class OrderManager implements Subject {
    private static OrderManager instance;
    private final List<Order> orders;
    private final List<Observer> observers;

    private OrderManager() {
        orders = new ArrayList<>();
        observers = new ArrayList<>();
    }

    /**
     * Returns the single instance of OrderManager.
     */
    public static synchronized OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
            System.out.println("[SINGLETON] OrderManager instance created");
        }
        return instance;
    }

    /**
     * Adds a new order to the manager.
     */
    public void addOrder(Order order) {
        orders.add(order);
        System.out.println("[SINGLETON] Order added: " + order.getId());
        notifyObservers(order.getId(), OrderStatus.PENDING, "Order created successfully");
    }

    /**
     * Updates the status of an existing order and notifies observers.
     */
    public void updateOrderStatus(String orderId, OrderStatus newStatus) {
        Order order = getOrderById(orderId);
        if (order != null) {
            order.setStatus(newStatus);
            System.out.println("[SINGLETON] Order " + orderId + " status updated to: " + newStatus);
            notifyObservers(orderId, newStatus, "Order status changed to " + newStatus);
        }
    }

    /**
     * Returns all orders managed by this singleton.
     */
    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    /**
     * Finds an order by its ID.
     */
    public Order getOrderById(String id) {
        return orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
        System.out.println("[OBSERVER] New observer added");
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String orderId, OrderStatus status, String message) {
        for (Observer observer : observers) {
            observer.update(orderId, status, message);
        }
    }
}
