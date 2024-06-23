package com.cargologix.cargo_logix.classes;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertMessege {
    public void errorMessege(String title, String messege) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(messege);
        alert.showAndWait();
    }

    public void infoMessege(String title, String messege) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(messege);
        alert.showAndWait();
    }

    public Optional<ButtonType> confirmMessege(String title, String messege) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION) ;
        alert.setHeaderText(title);
        alert.setContentText(messege);
        Optional<ButtonType> response = alert.showAndWait();
        return response ;
    }

}
