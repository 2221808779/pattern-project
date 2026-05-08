package com.subscriptionbox.ui;

import com.subscriptionbox.model.User;
import com.subscriptionbox.service.OrderService;
import com.subscriptionbox.service.PaymentService;
import com.subscriptionbox.service.SubscriptionService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Main Menu screen - entry point of the application.
 * Provides navigation to Subscribe and View Orders screens.
 */
public class MainMenu extends VBox {
    private final Stage stage;
    private User currentUser;
    private final SubscriptionService subscriptionService;
    private final OrderService orderService;
    private final PaymentService paymentService;

    public MainMenu(Stage stage) {
        this.stage = stage;
        this.currentUser = new User("Guest", "guest@example.com");
        this.subscriptionService = new SubscriptionService();
        this.orderService = new OrderService();
        this.paymentService = new PaymentService();

        setupUI();
    }

    private void setupUI() {
        setAlignment(Pos.CENTER);
        setSpacing(25);
        setPadding(new Insets(40, 30, 40, 30));
        getStyleClass().add("main-menu");

        Label titleLabel = new Label("\uD83D\uDCE6 Subscription Box Service");
        titleLabel.getStyleClass().add("title");
        titleLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 32px; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0, 0, 2);");

        Label subtitleLabel = new Label("Welcome, " + currentUser.getName() + "!");
        subtitleLabel.getStyleClass().add("subtitle");
        subtitleLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 16px; -fx-font-weight: 500;");

        VBox buttonContainer = new VBox(18);
        buttonContainer.setAlignment(Pos.CENTER);

        Button subscribeBtn = new Button("\uD83D\uDED2  Subscribe");
        subscribeBtn.getStyleClass().add("primary-button");
        subscribeBtn.setOnAction(e -> showSubscriptionScreen());

        Button viewOrdersBtn = new Button("\uD83D\uDCCB  View Orders");
        viewOrdersBtn.getStyleClass().add("secondary-button");
        viewOrdersBtn.setOnAction(e -> showOrderScreen());

        Button exitBtn = new Button("\u2716  Exit");
        exitBtn.getStyleClass().add("danger-button");
        exitBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.exit(0);
            }
        });

        buttonContainer.getChildren().addAll(subscribeBtn, viewOrdersBtn, exitBtn);
        getChildren().addAll(titleLabel, subtitleLabel, buttonContainer);
    }

    private void showSubscriptionScreen() {
        SubscriptionScreen screen = new SubscriptionScreen(stage, currentUser, subscriptionService, orderService, paymentService);
        Scene scene = new Scene(screen, 550, 520);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
    }

    private void showOrderScreen() {
        OrderScreen screen = new OrderScreen(stage, currentUser, orderService);
        Scene scene = new Scene(screen, 600, 520);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
