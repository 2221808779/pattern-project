package com.subscriptionbox.builder;

import com.subscriptionbox.model.Box;

/**
 * Abstract Builder for the Builder Pattern.
 * Defines the step-by-step interface for constructing Box objects.
 */
public abstract class BoxBuilder {
    protected Box box;

    public void createNewBox() {
        box = null;
    }

    public abstract void addItems();
    public abstract void setPrice();

    public Box getBox() {
        return box;
    }

    public void setDuration(int months) {
        if (box != null) {
            box.setDurationMonths(months);
        }
    }
}
