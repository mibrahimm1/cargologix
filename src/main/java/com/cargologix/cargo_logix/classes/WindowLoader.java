package com.cargologix.cargo_logix.classes;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WindowLoader {
    public Initializable loadWindow(String location, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cargologix/cargo_logix/" + location));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
            return fxmlLoader.getController(); // Return the controller
        } catch (Exception ex) {
            Logger.getLogger(WindowLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}