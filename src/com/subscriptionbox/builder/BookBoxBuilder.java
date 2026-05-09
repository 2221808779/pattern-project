package com.subscriptionbox.builder;

import com.subscriptionbox.factory.BoxFactory;

public class BookBoxBuilder extends BoxBuilder {

    @Override
    public void createNewBox() {
        box = BoxFactory.createBox("Book");
    }

    @Override
    public void addItems() {
        box.addItem("Bestseller Novel");
        box.addItem("Bookmark");
        box.addItem("Reading Journal");
        box.addItem("Tea Bag");
        System.out.println("[BUILDER] Added book items to box");
    }

    @Override
    public void setPrice() {
        box.setPrice(29.99);
        System.out.println("[BUILDER] Set BookBox price: $29.99/month");
    }
}
