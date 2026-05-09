package com.subscriptionbox.strategy;

public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cardHolder;

    public CreditCardPayment(String cardNumber, String cardHolder) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("[PAYMENT] Processing Credit Card payment of $" + String.format("%.2f", amount));
        System.out.println("[PAYMENT] Card: ****-****-****-" + cardNumber.substring(cardNumber.length() - 4));
        System.out.println("[PAYMENT] Card Holder: " + cardHolder);
        System.out.println("[PAYMENT] Credit Card payment SUCCESSFUL");
        return true;
    }

    @Override
    public String getMethodName() {
        return "Credit Card";
    }
}
