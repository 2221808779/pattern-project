package com.subscriptionbox.ui;

import com.subscriptionbox.model.Order;
import com.subscriptionbox.model.Subscription;
import com.subscriptionbox.model.User;
import com.subscriptionbox.service.OrderService;
import com.subscriptionbox.service.PaymentService;
import com.subscriptionbox.strategy.CashPayment;
import com.subscriptionbox.strategy.CreditCardPayment;
import com.subscriptionbox.strategy.PayPalPayment;
import com.subscriptionbox.strategy.PaymentStrategy;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Payment Screen - allows users to select a payment method and complete purchase.
 * Uses Strategy Pattern to process different payment types.
 */
public class PaymentScreen extends VBox {
    private final Stage stage;
    private final User user;
    private final Subscription subscription;
    private final OrderService orderService;
    private final PaymentService paymentService;

    private ComboBox<String> paymentMethodCombo;
    private TextField cardNumberField;
    private TextField cardHolderField;
    private TextField emailField;

    public PaymentScreen(Stage stage, User user, Subscription subscription,
                         OrderService orderService, PaymentService paymentService) {
        this.stage = stage;
        this.user = user;
        this.subscription = subscription;
        this.orderService = orderService;
        this.paymentService = paymentService;

        setupUI();
    }

    private void setupUI() {
        setAlignment(Pos.CENTER);
        setSpacing(10);
        setPadding(new Insets(35, 40, 35, 40));
        getStyleClass().add("screen");

        Label titleLabel = new Label("\uD83D\uDCB3 Payment");
        titleLabel.getStyleClass().add("title");

        Label infoLabel = new Label("Complete your subscription payment");
        infoLabel.setStyle("-fx-text-fill: #99AD7A; -fx-font-size: 13px; -fx-padding: 0 0 5 0;");

        Label summaryLabel = new Label(
                String.format("\uD83C\uDF81  %s Box  |  \u23F3  %d months  |  \uD83D\uDCB0  $%.2f",
                        subscription.getBox().getType(),
                        subscription.getDurationMonths(),
                        subscription.getTotalCost()));
        summaryLabel.getStyleClass().add("summary-label");
        summaryLabel.setWrapText(true);
        summaryLabel.setAlignment(Pos.CENTER);

        VBox formContainer = new VBox(16);
        formContainer.setAlignment(Pos.CENTER);

        VBox methodGroup = new VBox(6);
        methodGroup.setAlignment(Pos.CENTER_LEFT);
        Label methodLabel = new Label("\uD83D\uDCB5  Payment Method");
        methodLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        paymentMethodCombo = new ComboBox<>();
        paymentMethodCombo.getItems().addAll("\uD83D\uDCB3  Credit Card", "\uD83D\uDD35  PayPal", "\uD83D\uDCB5  Cash");
        paymentMethodCombo.setPromptText("Select method...");
        paymentMethodCombo.getStyleClass().add("combo-box");
        paymentMethodCombo.setOnAction(e -> updatePaymentFields());
        methodGroup.getChildren().addAll(methodLabel, paymentMethodCombo);

        VBox cardFields = new VBox(10);
        cardFields.setAlignment(Pos.CENTER_LEFT);
        cardNumberField = new TextField();
        cardNumberField.setPromptText("Card Number (16 digits)");
        cardNumberField.getStyleClass().add("text-field");
        cardNumberField.setVisible(false);
        cardHolderField = new TextField();
        cardHolderField.setPromptText("Card Holder Name");
        cardHolderField.getStyleClass().add("text-field");
        cardHolderField.setVisible(false);
        cardFields.getChildren().addAll(cardNumberField, cardHolderField);

        emailField = new TextField();
        emailField.setPromptText("PayPal Email");
        emailField.getStyleClass().add("text-field");
        emailField.setVisible(false);

        formContainer.getChildren().addAll(methodGroup, cardFields, emailField);

        VBox buttonGroup = new VBox(12);
        buttonGroup.setAlignment(Pos.CENTER);
        buttonGroup.setPadding(new Insets(15, 0, 0, 0));

        Button payBtn = new Button("\u2705  Pay Now");
        payBtn.getStyleClass().add("primary-button");
        payBtn.setOnAction(e -> handlePayment());

        Button backBtn = new Button("\u2B05  Back");
        backBtn.getStyleClass().add("secondary-button");
        backBtn.setOnAction(e -> {
            SubscriptionScreen screen = new SubscriptionScreen(stage, user,
                    new com.subscriptionbox.service.SubscriptionService(), orderService, paymentService);
            Scene scene = new Scene(screen, 550, 520);
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            stage.setScene(scene);
        });

        buttonGroup.getChildren().addAll(payBtn, backBtn);

        getChildren().addAll(titleLabel, infoLabel, summaryLabel, formContainer, buttonGroup);
    }

    private void updatePaymentFields() {
        String method = paymentMethodCombo.getValue();
        if (method == null) return;
        String m = method.toLowerCase();
        cardNumberField.setVisible(m.contains("credit"));
        cardHolderField.setVisible(m.contains("credit"));
        emailField.setVisible(m.contains("paypal"));
    }

    private void handlePayment() {
        String method = paymentMethodCombo.getValue();
        if (method == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Required", "Please select a payment method.");
            return;
        }

        PaymentStrategy strategy = getPaymentStrategy(method);

        boolean success = paymentService.processPayment(strategy, subscription.getTotalCost());

        if (success) {
            Order order = orderService.createOrder(user, subscription.getBox(), subscription.getTotalCost());
            showAlert(Alert.AlertType.INFORMATION, "Payment Successful",
                    "Order placed! Order ID: " + order.getId() + "\nStatus: " + order.getStatus());
            OrderScreen orderScreen = new OrderScreen(stage, user, orderService);
            Scene scene = new Scene(orderScreen, 600, 520);
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            stage.setScene(scene);
        } else {
            showAlert(Alert.AlertType.ERROR, "Payment Failed", "Please try again with a different payment method.");
        }
    }

    private PaymentStrategy getPaymentStrategy(String method) {
        String m = method.toLowerCase();
        if (m.contains("credit")) {
            String cardNum = cardNumberField.getText().isEmpty() ? "1234567890123456" : cardNumberField.getText();
            String holder = cardHolderField.getText().isEmpty() ? "John Doe" : cardHolderField.getText();
            return new CreditCardPayment(cardNum, holder);
        } else if (m.contains("paypal")) {
            String email = emailField.getText().isEmpty() ? "user@example.com" : emailField.getText();
            return new PayPalPayment(email);
        } else if (m.contains("cash")) {
            return new CashPayment();
        } else {
            throw new IllegalArgumentException("Unknown payment method: " + method);
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type, message);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
