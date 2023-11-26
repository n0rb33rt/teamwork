package com.norbert.frontend.dialog;

import com.norbert.frontend.Application;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class AlertDialog {
    public static void showErrorDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image icon = new Image(Objects.requireNonNull(Application.class.getResourceAsStream("icon.png")));
        stage.getIcons().add(icon);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
