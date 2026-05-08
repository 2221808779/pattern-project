package com.subscriptionbox.service;

import com.subscriptionbox.strategy.PaymentStrategy;

/**
 * PaymentService - Processes payments using the Strategy Pattern.
 * Delegates payment logic to the provided PaymentStrategy.
 */
public class PaymentService {

    /**
     * Processes a payment using the specified strategy.
     * @param strategy The payment method to use
     * @param amount The amount to charge
     * @return true if payment was successful
     */
    public boolean processPayment(PaymentStrategy strategy, double amount) {
        System.out.println("[SERVICE] Processing payment via " + strategy.getMethodName() + "...");
        boolean success = strategy.pay(amount);
        if (success) {
            System.out.println("[SERVICE] Payment of $" + String.format("%.2f", amount) + " completed");
        } else {
            System.out.println("[SERVICE] Payment FAILED");
        }
        return success;
    }
}
