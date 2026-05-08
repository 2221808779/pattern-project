package com.subscriptionbox.strategy;

/**
 * Represents a payment method strategy interface.
 * Part of the Strategy Pattern - allows interchangeable payment algorithms.
 */
public interface PaymentStrategy {
    /**
     * Process the payment for the given amount.
     * @param amount The amount to charge
     * @return true if payment was successful, false otherwise
     */
    boolean pay(double amount);

    /**
     * Returns the display name of this payment method.
     */
    String getMethodName();
}
