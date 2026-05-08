package com.subscriptionbox.singleton;

import com.subscriptionbox.model.Subscription;
import java.util.ArrayList;
import java.util.List;

/**
 * SubscriptionManager - Singleton Pattern implementation.
 * Ensures only one instance manages all subscriptions globally.
 * Provides thread-safe access via synchronized getInstance().
 */
public class SubscriptionManager {
    private static SubscriptionManager instance;
    private final List<Subscription> subscriptions;

    private SubscriptionManager() {
        subscriptions = new ArrayList<>();
    }

    /**
     * Returns the single instance of SubscriptionManager.
     * Creates it on first call (lazy initialization).
     */
    public static synchronized SubscriptionManager getInstance() {
        if (instance == null) {
            instance = new SubscriptionManager();
            System.out.println("[SINGLETON] SubscriptionManager instance created");
        }
        return instance;
    }

    /**
     * Adds a new subscription to the manager.
     */
    public void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
        System.out.println("[SINGLETON] Subscription added: " + subscription.getId());
    }

    /**
     * Returns all subscriptions managed by this singleton.
     */
    public List<Subscription> getSubscriptions() {
        return new ArrayList<>(subscriptions);
    }

    /**
     * Finds a subscription by its ID.
     */
    public Subscription getSubscriptionById(String id) {
        return subscriptions.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
