package com.subscriptionbox.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete Box implementation for Book subscription boxes.
 */
public class BookBox implements Box {
    private List<String> items;
    private double price;
    private int durationMonths;
    private String size;
    private boolean premium;

    public BookBox() {
        this.items = new ArrayList<>();
        this.size = "Medium";
    }

    @Override
    public String getType() {
        return "Book";
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public List<String> getItems() {
        return items;
    }

    @Override
    public int getDurationMonths() {
        return durationMonths;
    }

    @Override
    public String getSize() {
        return size;
    }

    @Override
    public boolean isPremium() {
        return premium;
    }

    @Override
    public void addItem(String item) {
        items.add(item);
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public void setDurationMonths(int months) {
        this.durationMonths = months;
    }

    @Override
    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    @Override
    public String toString() {
        return String.format("BookBox [items=%s, price=$%.2f, duration=%d months, size=%s, premium=%b]",
                items, price, durationMonths, size, premium);
    }
}
