package com.subscriptionbox.observer;

import com.subscriptionbox.model.Order.OrderStatus;

/**
 * Observer interface for the Observer Pattern.
 * Any class that wants to receive notifications must implement this.
 */
public interface Observer {
    void update(String orderId, OrderStatus status, String message);
}
