package com.subscriptionbox.model;

/**
 * Subscription model linking a user to a box type and duration.
 */
public class Subscription {
    private final String id;
    private final User user;
    private final Box box;
    private int durationMonths;
    private double totalCost;

    public Subscription(User user, Box box, int durationMonths) {
        this.id = "SUB-" + System.currentTimeMillis();
        this.user = user;
        this.box = box;
        this.durationMonths = durationMonths;
        this.totalCost = box.getPrice() * durationMonths;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Box getBox() {
        return box;
    }

    public int getDurationMonths() {
        return durationMonths;
    }

    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public String toString() {
        return String.format("Subscription{id='%s', user='%s', box='%s', duration=%d, total=$%.2f}",
                id, user.getName(), box.getType(), durationMonths, totalCost);
    }
}
