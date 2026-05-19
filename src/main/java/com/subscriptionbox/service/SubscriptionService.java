package com.subscriptionbox.service;

import com.subscriptionbox.builder.BoxBuilder;
import com.subscriptionbox.model.Box;
import com.subscriptionbox.model.Subscription;
import com.subscriptionbox.model.User;
import com.subscriptionbox.singleton.SubscriptionManager;

/**
 * SubscriptionService - Handles subscription creation using Builder Pattern.
 * Coordinates with SubscriptionManager (Singleton) to store subscriptions.
 */
public class SubscriptionService {
    private final SubscriptionManager subscriptionManager;

    public SubscriptionService() {
        this.subscriptionManager = SubscriptionManager.getInstance();
    }

    /**
     * Creates a new subscription using the Builder Pattern.
     * @param user The subscribing user
     * @param builder The box builder to construct the box
     * @param durationMonths Subscription duration in months
     * @return The created Subscription
     */
    public Subscription createSubscription(User user, BoxBuilder builder, int durationMonths) {
        System.out.println("[SERVICE] Creating subscription for " + user.getName() + "...");

        builder.createNewBox();
        builder.addItems();
        builder.setPrice();
        builder.setDuration(durationMonths);

        Box box = builder.getBox();
        System.out.println("[SERVICE] Box constructed: " + box);

        Subscription subscription = new Subscription(user, box, durationMonths);
        subscriptionManager.addSubscription(subscription);

        System.out.println("[SERVICE] Subscription created: " + subscription);
        return subscription;
    }
}
