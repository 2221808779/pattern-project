package com.subscriptionbox;

import com.subscriptionbox.singleton.OrderManager;
import com.subscriptionbox.singleton.SubscriptionManager;
import com.subscriptionbox.ui.MainMenu;
import com.subscriptionbox.ui.UINotificationObserver;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * MainApp - Application entry point for the Subscription Box Service.
 * Initializes the JavaFX application and sets up the main window.
 *
 * Design Patterns Used:
 * - Factory Pattern: Creates different box types
 * - Builder Pattern: Constructs boxes step-by-step
 * - Strategy Pattern: Handles multiple payment methods
 * - Observer Pattern: Notifies on order status changes
 * - Singleton Pattern: Global access to managers
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        System.out.println("===========================================");
        System.out.println("  Subscription Box Service System");
        System.out.println("===========================================");

        // Initialize singletons
        SubscriptionManager.getInstance();
        OrderManager orderManager = OrderManager.getInstance();

        // Register global notification observer
        orderManager.addObserver(new UINotificationObserver());

        // Set up the main menu
        MainMenu mainMenu = new MainMenu(primaryStage);
        Scene scene = new Scene(mainMenu, 550, 520);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setTitle("Subscription Box Service");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(480);
        primaryStage.show();

        System.out.println("[APP] Application started successfully");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
