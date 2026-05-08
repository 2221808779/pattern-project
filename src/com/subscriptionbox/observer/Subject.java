package com.subscriptionbox.observer;

import com.subscriptionbox.model.Order.OrderStatus;
import java.util.ArrayList;
import java.util.List;

/**
 * Subject interface for the Observer Pattern.
 * Defines methods for managing observers and sending notifications.
 */
public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String orderId, OrderStatus status, String message);
}
