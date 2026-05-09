package com.subscriptionbox.observer;

import com.subscriptionbox.model.Order.OrderStatus;

public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String orderId, OrderStatus status, String message);
}
