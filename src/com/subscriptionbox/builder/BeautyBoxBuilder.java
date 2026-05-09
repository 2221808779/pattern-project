package com.subscriptionbox.builder;

import com.subscriptionbox.factory.BoxFactory;

public class BeautyBoxBuilder extends BoxBuilder {

    @Override
    public void createNewBox() {
        box = BoxFactory.createBox("Beauty");
    }

    @Override
    public void addItems() {
        box.addItem("Lipstick");
        box.addItem("Face Cream");
        box.addItem("Nail Polish");
        box.addItem("Perfume Sample");
        System.out.println("[BUILDER] Added beauty items to box");
    }

    @Override
    public void setPrice() {
        box.setPrice(39.99);
        System.out.println("[BUILDER] Set BeautyBox price: $39.99/month");
    }
}
