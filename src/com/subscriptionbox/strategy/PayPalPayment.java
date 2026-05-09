package com.subscriptionbox.strategy;

public class PayPalPayment implements PaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("[PAYMENT] Processing PayPal payment of $" + String.format("%.2f", amount));
        System.out.println("[PAYMENT] PayPal Account: " + email);
        System.out.println("[PAYMENT] PayPal payment SUCCESSFUL");
        return true;
    }

    @Override
    public String getMethodName() {
        return "PayPal";
    }
}
