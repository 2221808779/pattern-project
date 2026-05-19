package com.subscriptionbox.ui;

import com.subscriptionbox.model.Order;
import com.subscriptionbox.model.Order.OrderStatus;
import com.subscriptionbox.service.OrderService;
import com.subscriptionbox.singleton.OrderManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

/**
 * Order View Screen - displays all orders and their status.
 * Demonstrates Observer Pattern by registering for notifications.
 */
public class OrderScreen extends VBox {
    private final Stage stage;
    private final OrderService orderService;
    private final VBox orderListContainer;

    public OrderScreen(Stage stage, OrderService orderService) {
        this.stage = stage;
        this.orderService = orderService;
        this.orderListContainer = new VBox(14);

        setupUI();
        registerObserver();
    }

    private void setupUI() {
        setAlignment(Pos.CENTER);
        setSpacing(10);
        setPadding(new Insets(35, 40, 35, 40));
        getStyleClass().add("screen");

        Label titleLabel = new Label("\uD83D\uDCCB Your Orders");
        titleLabel.getStyleClass().add("title");

        Label infoLabel = new Label("Track your subscription box deliveries");
        infoLabel.setStyle("-fx-text-fill: #99AD7A; -fx-font-size: 13px; -fx-padding: 0 0 10 0;");

        orderListContainer.setAlignment(Pos.TOP_CENTER);
        orderListContainer.setSpacing(14);
        orderListContainer.setPadding(new Insets(5));

        ScrollPane scrollPane = new ScrollPane(orderListContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300);
        scrollPane.getStyleClass().add("scroll-pane");

        loadOrders();

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        Button refreshBtn = new Button("\uD83D\uDD04  Refresh");
        refreshBtn.getStyleClass().add("primary-button");
        refreshBtn.setOnAction(e -> loadOrders());

        Button backBtn = new Button("\u2B05  Back to Menu");
        backBtn.getStyleClass().add("secondary-button");
        backBtn.setOnAction(e -> {
            MainMenu menu = new MainMenu(stage);
            Scene scene = new Scene(menu, 550, 520);
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            stage.setScene(scene);
        });

        buttonBox.getChildren().addAll(refreshBtn, backBtn);

        getChildren().addAll(titleLabel, infoLabel, scrollPane, buttonBox);
    }

    private void loadOrders() {
        orderListContainer.getChildren().clear();

        List<Order> orders = orderService.getAllOrders();

        if (orders.isEmpty()) {
            Label noOrdersLabel = new Label("No orders found. Subscribe to get started!");
            noOrdersLabel.getStyleClass().add("info-label");
            orderListContainer.getChildren().add(noOrdersLabel);
        } else {
            for (Order order : orders) {
                orderListContainer.getChildren().add(createOrderCard(order));
            }
        }
    }

    private VBox createOrderCard(Order order) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.getStyleClass().add("order-card");

        HBox headerRow = new HBox(10);
        headerRow.setAlignment(Pos.CENTER_LEFT);
        Label orderIdLabel = new Label("\uD83D\uDCE6 " + order.getId());
        orderIdLabel.getStyleClass().add("order-id");
        Label statusLabel = new Label(order.getStatus().toString());
        statusLabel.getStyleClass().add(getStatusStyle(order.getStatus()));
        headerRow.getChildren().addAll(orderIdLabel, statusLabel);

        Label boxTypeLabel = new Label("\uD83C\uDF81  Box: " + order.getBox().getType());
        boxTypeLabel.setStyle("-fx-text-fill: #546B41; -fx-font-size: 14px; -fx-font-weight: 500;");

        Label amountLabel = new Label(String.format("\uD83D\uDCB0  Amount: $%.2f", order.getAmount()));
        amountLabel.setStyle("-fx-text-fill: #546B41; -fx-font-size: 14px;");

        String dateStr = order.getCreatedAt() != null ? order.getCreatedAt().toString().substring(0, 10) : "N/A";
        Label dateLabel = new Label("\uD83D\uDCC5  Date: " + dateStr);
        dateLabel.setStyle("-fx-text-fill: #99AD7A; -fx-font-size: 12px;");

        card.getChildren().addAll(headerRow, boxTypeLabel, amountLabel, dateLabel);
        return card;
    }

    private String getStatusStyle(OrderStatus status) {
        if (status == OrderStatus.PENDING) {
            return "status-pending";
        } else if (status == OrderStatus.SHIPPED) {
            return "status-shipped";
        } else {
            return "status-delivered";
        }
    }

    /**
     * Registers this screen as an Observer to receive order status updates.
     * This demonstrates the Observer Pattern in action.
     */
    private void registerObserver() {
        OrderManager orderManager = OrderManager.getInstance();
        orderManager.addObserver(new UINotificationObserver());
        System.out.println("[OBSERVER] OrderScreen registered as observer");
    }
}
