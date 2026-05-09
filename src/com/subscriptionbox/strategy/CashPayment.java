package com.subscriptionbox.strategy;

public class CashPayment implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("[PAYMENT] Processing Cash payment of $" + String.format("%.2f", amount));
        System.out.println("[PAYMENT] Cash payment SUCCESSFUL - Please pay on delivery");
        return true;
    }

    @Override
    public String getMethodName() {
        return "Cash";
    }
}
