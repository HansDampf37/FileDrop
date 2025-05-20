package ui;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Toast {

    public static void show(Stage stage, String message, int durationMillis) {
        Label toastLabel = new Label(message);
        toastLabel.setStyle("-fx-background-color: #4FA24F; -fx-text-fill: white; -fx-padding: 10px; "
                + "-fx-background-radius: 5; -fx-font-size: 14px;");
        toastLabel.setOpacity(0);

        StackPane root = (StackPane) stage.getScene().getRoot();
        StackPane.setMargin(toastLabel, new Insets(0, 20, 20, 0));
        root.getChildren().add(toastLabel);
        StackPane.setAlignment(toastLabel, Pos.BOTTOM_RIGHT);

        // Fade in
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), toastLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(0.9);
        fadeIn.setOnFinished(e -> {
            // Fade out after delay
            PauseTransition delay = new PauseTransition(Duration.millis(durationMillis));
            delay.setOnFinished(ev -> {
                FadeTransition fadeOut = new FadeTransition(Duration.millis(300), toastLabel);
                fadeOut.setFromValue(0.9);
                fadeOut.setToValue(0);
                fadeOut.setOnFinished(event -> root.getChildren().remove(toastLabel));
                fadeOut.play();
            });
            delay.play();
        });

        fadeIn.play();
    }
}
