package com.subscriptionbox.ui;

import com.subscriptionbox.model.Order.OrderStatus;
import com.subscriptionbox.observer.Observer;
import javafx.application.Platform;

/**
 * UI Observer - receives order status notifications and prints to console.
 * Implements Observer Pattern to react to order changes.
 */
public class UINotificationObserver implements Observer {

    @Override
    public void update(String orderId, OrderStatus status, String message) {
        Platform.runLater(() -> {
            System.out.println("[NOTIFICATION] Order " + orderId + " - " + status + ": " + message);
        });
    }
}
