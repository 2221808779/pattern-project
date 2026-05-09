package com.subscriptionbox.ui;

import com.subscriptionbox.model.User;
import com.subscriptionbox.service.OrderService;
import com.subscriptionbox.service.PaymentService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Subscription Screen - allows users to select a box type and duration.
 * Uses Factory + Builder patterns to create the selected box.
 */
public class SubscriptionScreen extends VBox {
    private final Stage stage;
    private final User user;
    private final OrderService orderService;
    private final PaymentService paymentService;

    private ComboBox<String> boxTypeCombo;
    private TextField durationField;

    public SubscriptionScreen(Stage stage, User user, OrderService orderService, PaymentService paymentService) {
        this.stage = stage;
        this.user = user;
        this.orderService = orderService;
        this.paymentService = paymentService;

        setupUI();
    }

    private void setupUI() {
        setAlignment(Pos.CENTER);
        setSpacing(10);
        setPadding(new Insets(35, 40, 35, 40));
        getStyleClass().add("screen");

        Label titleLabel = new Label("\uD83D\uDCE6 Subscribe to a Box");
        titleLabel.getStyleClass().add("title");

        Label infoLabel = new Label("Choose your perfect subscription box and duration");
        infoLabel.setStyle("-fx-text-fill: #99AD7A; -fx-font-size: 13px; -fx-padding: 0 0 10 0;");

        VBox formContainer = new VBox(16);
        formContainer.setAlignment(Pos.CENTER);

        VBox boxGroup = new VBox(6);
        boxGroup.setAlignment(Pos.CENTER_LEFT);
        Label boxLabel = new Label("\uD83C\uDF81  Box Type");
        boxLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        boxTypeCombo = new ComboBox<>();
        boxTypeCombo.getItems().addAll("\uD83D\uDC84  Beauty Box", "\uD83C\uDFAE  Gaming Box", "\uD83D\uDCDA  Book Box");
        boxTypeCombo.setPromptText("Select a box...");
        boxTypeCombo.getStyleClass().add("combo-box");
        boxGroup.getChildren().addAll(boxLabel, boxTypeCombo);

        VBox durationGroup = new VBox(6);
        durationGroup.setAlignment(Pos.CENTER_LEFT);
        Label durationLabel = new Label("\u23F3  Duration");
        durationLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        durationField = new TextField();
        durationField.setPromptText("1 - 12 months");
        durationField.getStyleClass().add("text-field");
        durationGroup.getChildren().addAll(durationLabel, durationField);

        formContainer.getChildren().addAll(boxGroup, durationGroup);

        VBox buttonGroup = new VBox(12);
        buttonGroup.setAlignment(Pos.CENTER);
        buttonGroup.setPadding(new Insets(15, 0, 0, 0));

        Button confirmBtn = new Button("\uD83D\uDED2  Continue to Payment");
        confirmBtn.getStyleClass().add("primary-button");
        confirmBtn.setOnAction(e -> handleSubscribe());

        Button backBtn = new Button("\u2B05  Back to Menu");
        backBtn.getStyleClass().add("secondary-button");
        backBtn.setOnAction(e -> {
            MainMenu menu = new MainMenu(stage);
            Scene scene = new Scene(menu, 550, 520);
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            stage.setScene(scene);
        });

        buttonGroup.getChildren().addAll(confirmBtn, backBtn);

        getChildren().addAll(titleLabel, infoLabel, formContainer, buttonGroup);
    }

    private void handleSubscribe() {
        String selectedType = boxTypeCombo.getValue();
        String durationText = durationField.getText();

        if (selectedType == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Required", "Please select a box type.");
            return;
        }

        int duration;
        try {
            duration = Integer.parseInt(durationText);
            if (duration < 1 || duration > 12) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Duration must be between 1 and 12 months.");
            return;
        }

        String cleanType = getCleanType(selectedType);

        BoxDetailsScreen detailsScreen = new BoxDetailsScreen(stage, user, cleanType, duration,
                orderService, paymentService);
        Scene scene = new Scene(detailsScreen, 550, 520);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
    }

    private String getCleanType(String type) {
        String t = type.toLowerCase();
        if (t.contains("beauty")) return "Beauty";
        if (t.contains("gaming")) return "Gaming";
        if (t.contains("book")) return "Book";
        return type;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type, message);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
