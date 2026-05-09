package com.subscriptionbox.builder;

import com.subscriptionbox.factory.BoxFactory;

public class GamingBoxBuilder extends BoxBuilder {

    @Override
    public void createNewBox() {
        box = BoxFactory.createBox("Gaming");
    }

    @Override
    public void addItems() {
        box.addItem("Gaming Mouse Pad");
        box.addItem("USB Cable");
        box.addItem("Gaming Stickers");
        box.addItem("Energy Drink");
        System.out.println("[BUILDER] Added gaming items to box");
    }

    @Override
    public void setPrice() {
        box.setPrice(49.99);
        System.out.println("[BUILDER] Set GamingBox price: $49.99/month");
    }
}
