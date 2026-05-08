package com.subscriptionbox.ui;

import com.subscriptionbox.builder.BoxBuilder;
import com.subscriptionbox.model.Box;
import com.subscriptionbox.model.Subscription;
import com.subscriptionbox.model.User;
import com.subscriptionbox.service.OrderService;
import com.subscriptionbox.service.PaymentService;
import com.subscriptionbox.service.SubscriptionService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Box Details / Customization Screen.
 * Allows users to customize their selected box before payment.
 * Demonstrates the Builder Pattern by constructing the box step-by-step
 * based on user selections (size, extra items, premium option).
 */
public class BoxDetailsScreen extends VBox {
    private final Stage stage;
    private final User user;
    private final String boxType;
    private final int durationMonths;
    private final SubscriptionService subscriptionService;
    private final OrderService orderService;
    private final PaymentService paymentService;

    private ComboBox<String> sizeCombo;
    private CheckBox premiumCheck;
    private VBox extraItemsContainer;
    private Label priceLabel;

    public BoxDetailsScreen(Stage stage, User user, String boxType, int durationMonths,
                            SubscriptionService subscriptionService, OrderService orderService,
                            PaymentService paymentService) {
        this.stage = stage;
        this.user = user;
        this.boxType = boxType;
        this.durationMonths = durationMonths;
        this.subscriptionService = subscriptionService;
        this.orderService = orderService;
        this.paymentService = paymentService;

        setupUI();
    }

    private void setupUI() {
        setAlignment(Pos.CENTER);
        setSpacing(10);
        setPadding(new Insets(35, 40, 35, 40));
        getStyleClass().add("screen");

        Label titleLabel = new Label("\uD83C\uDF81 Customize Your " + boxType + " Box");
        titleLabel.getStyleClass().add("title");

        Label infoLabel = new Label("Personalize your box before checkout");
        infoLabel.setStyle("-fx-text-fill: #99AD7A; -fx-font-size: 13px; -fx-padding: 0 0 10 0;");

        Label basePriceLabel = new Label("\uD83D\uDCB0 Base Price: $" + getBasePrice() + "/month");
        basePriceLabel.getStyleClass().add("summary-label");
        basePriceLabel.setAlignment(Pos.CENTER);

        VBox formContainer = new VBox(14);
        formContainer.setAlignment(Pos.CENTER);

        VBox sizeGroup = new VBox(6);
        sizeGroup.setAlignment(Pos.CENTER_LEFT);
        Label sizeLabel = new Label("\uD83D\uDCCF Box Size");
        sizeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        sizeCombo = new ComboBox<>();
        sizeCombo.getItems().addAll("Small (-10%)", "Medium (Base)", "Large (+20%)");
        sizeCombo.setValue("Medium (Base)");
        sizeCombo.getStyleClass().add("combo-box");
        sizeCombo.setOnAction(e -> updatePrice());
        sizeGroup.getChildren().addAll(sizeLabel, sizeCombo);

        VBox itemsGroup = new VBox(6);
        itemsGroup.setAlignment(Pos.CENTER_LEFT);
        Label itemsLabel = new Label("\uD83D\uDC8E Extra Items");
        itemsLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        extraItemsContainer = new VBox(8);
        extraItemsContainer.setPadding(new Insets(5, 0, 5, 10));
        populateExtraItems();
        itemsGroup.getChildren().addAll(itemsLabel, extraItemsContainer);

        premiumCheck = new CheckBox("\u2B50 Premium Edition (+$15)");
        premiumCheck.setStyle("-fx-font-size: 14px; -fx-text-fill: #546B41; -fx-font-weight: 500;");
        premiumCheck.setOnAction(e -> updatePrice());

        formContainer.getChildren().addAll(sizeGroup, itemsGroup, premiumCheck);

        priceLabel = new Label("\uD83D\uDCB5 Total: $0.00");
        priceLabel.getStyleClass().add("summary-label");
        priceLabel.setAlignment(Pos.CENTER);
        updatePrice();

        VBox buttonGroup = new VBox(12);
        buttonGroup.setAlignment(Pos.CENTER);
        buttonGroup.setPadding(new Insets(10, 0, 0, 0));

        Button confirmBtn = new Button("\u2705 Confirm & Go to Payment");
        confirmBtn.getStyleClass().add("primary-button");
        confirmBtn.setOnAction(e -> handleConfirm());

        Button backBtn = new Button("\u2B05 Back");
        backBtn.getStyleClass().add("secondary-button");
        backBtn.setOnAction(e -> goBack());

        buttonGroup.getChildren().addAll(confirmBtn, backBtn);

        getChildren().addAll(titleLabel, infoLabel, basePriceLabel, formContainer, priceLabel, buttonGroup);
    }

    private double getBasePrice() {
        if ("beauty".equalsIgnoreCase(boxType)) return 39.99;
        if ("gaming".equalsIgnoreCase(boxType)) return 49.99;
        if ("book".equalsIgnoreCase(boxType)) return 29.99;
        return 0;
    }

    private List<String> getExtraItemsOptions() {
        if ("beauty".equalsIgnoreCase(boxType)) {
            List<String> items = new ArrayList<>();
            items.add("\uD83D\uDC84 Lip Gloss (+$5)");
            items.add("\uD83D\uDC85 Nail Kit (+$4)");
            items.add("\uD83E\uDDF4 Face Mask Pack (+$6)");
            items.add("\uD83C\uDF38 Hair Accessories (+$3)");
            return items;
        }
        if ("gaming".equalsIgnoreCase(boxType)) {
            List<String> items = new ArrayList<>();
            items.add("\uD83C\uDFAE Controller Skin (+$5)");
            items.add("\uD83D\uDD0B Gaming Batteries (+$4)");
            items.add("\uD83C\uDF99\uFE0F Keycap Set (+$8)");
            items.add("\uD83D\uDD79\uFE0F Mouse Pad XL (+$7)");
            return items;
        }
        if ("book".equalsIgnoreCase(boxType)) {
            List<String> items = new ArrayList<>();
            items.add("\uD83D\uDD16 Bookmark Set (+$3)");
            items.add("\uD83D\uDCD6 Reading Light (+$6)");
            items.add("\uD83D\uDCDD Reading Journal (+$5)");
            items.add("\uD83C\uDF75 Premium Tea Pack (+$4)");
            return items;
        }
        return new ArrayList<>();
    }

    private void populateExtraItems() {
        List<String> options = getExtraItemsOptions();
        for (String option : options) {
            CheckBox cb = new CheckBox(option);
            cb.setStyle("-fx-font-size: 13px; -fx-text-fill: #546B41;");
            cb.setOnAction(e -> updatePrice());
            extraItemsContainer.getChildren().add(cb);
        }
    }

    private void updatePrice() {
        double price = getBasePrice();

        String size = sizeCombo.getValue();
        if (size != null) {
            if (size.contains("Small")) price *= 0.9;
            else if (size.contains("Large")) price *= 1.2;
        }

        if (premiumCheck != null && premiumCheck.isSelected()) {
            price += 15;
        }

        if (extraItemsContainer != null) {
            for (javafx.scene.Node node : extraItemsContainer.getChildren()) {
                if (node instanceof CheckBox) {
                    CheckBox cb = (CheckBox) node;
                    if (cb.isSelected()) {
                        String text = cb.getText();
                        if (text.contains("+$5")) price += 5;
                        else if (text.contains("+$8")) price += 8;
                        else if (text.contains("+$7")) price += 7;
                        else if (text.contains("+$6")) price += 6;
                        else if (text.contains("+$4")) price += 4;
                        else if (text.contains("+$3")) price += 3;
                    }
                }
            }
        }

        double total = price * durationMonths;
        priceLabel.setText("\uD83D\uDCB5 Total: $" + String.format("%.2f", total));
    }

    private void handleConfirm() {
        BoxBuilder builder = getBuilderForType(boxType);

        builder.createNewBox();
        builder.addItems();
        builder.setPrice();
        builder.setDuration(durationMonths);

        Box box = builder.getBox();

        String size = sizeCombo.getValue();
        if (size != null && size.contains("Small")) {
            box.setSize("Small");
        } else if (size != null && size.contains("Large")) {
            box.setSize("Large");
        } else {
            box.setSize("Medium");
        }

        box.setPremium(premiumCheck != null && premiumCheck.isSelected());

        if (extraItemsContainer != null) {
            for (javafx.scene.Node node : extraItemsContainer.getChildren()) {
                if (node instanceof CheckBox) {
                    CheckBox cb = (CheckBox) node;
                    if (cb.isSelected()) {
                        String itemText = cb.getText().replaceAll("[^a-zA-Z\\s]", "").trim();
                        box.addItem(itemText);
                    }
                }
            }
        }

        double finalPrice = box.getPrice();
        if (box.getSize().equals("Small")) finalPrice *= 0.9;
        else if (box.getSize().equals("Large")) finalPrice *= 1.2;
        if (box.isPremium()) finalPrice += 15;
        if (extraItemsContainer != null) {
            for (javafx.scene.Node node : extraItemsContainer.getChildren()) {
                if (node instanceof CheckBox) {
                    CheckBox cb = (CheckBox) node;
                    if (cb.isSelected()) {
                        String text = cb.getText();
                        if (text.contains("+$5")) finalPrice += 5;
                        else if (text.contains("+$8")) finalPrice += 8;
                        else if (text.contains("+$7")) finalPrice += 7;
                        else if (text.contains("+$6")) finalPrice += 6;
                        else if (text.contains("+$4")) finalPrice += 4;
                        else if (text.contains("+$3")) finalPrice += 3;
                    }
                }
            }
        }

        Subscription subscription = new Subscription(user, box, durationMonths);

        PaymentScreen paymentScreen = new PaymentScreen(stage, user, subscription, orderService, paymentService);
        Scene scene = new Scene(paymentScreen, 550, 520);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
    }

    private BoxBuilder getBuilderForType(String type) {
        String t = type.toLowerCase();
        if ("beauty".contains(t) || t.contains("beauty")) {
            return new com.subscriptionbox.builder.BeautyBoxBuilder();
        } else if ("gaming".contains(t) || t.contains("gaming")) {
            return new com.subscriptionbox.builder.GamingBoxBuilder();
        } else if ("book".contains(t) || t.contains("book")) {
            return new com.subscriptionbox.builder.BookBoxBuilder();
        } else {
            throw new IllegalArgumentException("Unknown box type: " + type);
        }
    }

    private void goBack() {
        SubscriptionScreen screen = new SubscriptionScreen(stage, user, subscriptionService, orderService, paymentService);
        Scene scene = new Scene(screen, 550, 520);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type, message);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
