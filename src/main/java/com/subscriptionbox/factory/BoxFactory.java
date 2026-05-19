package com.subscriptionbox.factory;

import com.subscriptionbox.model.BeautyBox;
import com.subscriptionbox.model.BookBox;
import com.subscriptionbox.model.Box;
import com.subscriptionbox.model.GamingBox;

/**
 * BoxFactory - Factory Pattern implementation.
 * Creates different types of Box objects based on the requested type.
 * Centralizes object creation logic and decouples client code from concrete classes.
 */
public class BoxFactory {

    /**
     * Creates a Box of the specified type.
     * @param type The type of box to create ("Beauty", "Gaming", or "Book")
     * @return A new Box instance of the requested type
     * @throws IllegalArgumentException if the type is not recognized
     */
    public static Box createBox(String type) {
        switch (type.toLowerCase()) {
            case "beauty":
                System.out.println("[FACTORY] Creating BeautyBox...");
                return new BeautyBox();
            case "gaming":
                System.out.println("[FACTORY] Creating GamingBox...");
                return new GamingBox();
            case "book":
                System.out.println("[FACTORY] Creating BookBox...");
                return new BookBox();
            default:
                throw new IllegalArgumentException("Unknown box type: " + type);
        }
    }
}
