package com.subscriptionbox.model;

import java.util.List;

/**
 * Box interface - defines the contract for all subscription box types.
 * Part of the Builder Pattern implementation.
 */
public interface Box {
    String getType();
    double getPrice();
    List<String> getItems();
    int getDurationMonths();
    String getSize();
    boolean isPremium();
    void addItem(String item);
    void setPrice(double price);
    void setDurationMonths(int months);
    void setSize(String size);
    void setPremium(boolean premium);
}
