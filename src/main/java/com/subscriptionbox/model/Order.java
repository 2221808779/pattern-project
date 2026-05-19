package com.subscriptionbox.model;

import java.time.LocalDateTime;

/**
 * Order model that also acts as the Subject in the Observer Pattern.
 * Notifies observers when its status changes.
 */
public class Order {
    /**
     * Enumeration of possible order statuses.
     */
    public enum OrderStatus {
        PENDING, SHIPPED, DELIVERED
    }

    private final String id;
    private final User user;
    private final Box box;
    private OrderStatus status;
    private double amount;
    private LocalDateTime createdAt;

    public Order(User user, Box box, double amount) {
        this.id = "ORD-" + System.currentTimeMillis();
        this.user = user;
        this.box = box;
        this.amount = amount;
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Box getBox() {
        return box;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return String.format("Order{id='%s', user='%s', box='%s', status=%s, amount=$%.2f, created=%s}",
                id, user.getName(), box.getType(), status, amount, createdAt);
    }
}
