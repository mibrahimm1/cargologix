package com.cargologix.cargo_logix.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import com.cargologix.cargo_logix.database.db;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import com.cargologix.cargo_logix.classes.LoginStatus;
import com.cargologix.cargo_logix.classes.WindowLoader;
import javafx.stage.Stage;
import com.cargologix.cargo_logix.classes.AlertMessege;

public class Login_Controller implements Initializable {

    @FXML
    private TextField addressR;

    @FXML
    private CheckBox showPassCheck;

    @FXML
    private PasswordField confirmPassR;

    @FXML
    private TextField contactR;

    @FXML
    private TextField emailR;

    @FXML
    private TextField idR;

    @FXML
    private AnchorPane loginForm;

    @FXML
    private TextField nameR;

    @FXML
    private PasswordField passwordL;

    @FXML
    private PasswordField passwordR;

    @FXML
    private AnchorPane registerForm;

    @FXML
    private StackPane rootPane;

    @FXML
    private TextField usernameL;

    @FXML
    private TextField usernameR;

    @FXML
    private TextField showPassL;

    db databaseHandler;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    AlertMessege alert = new AlertMessege() ;


    @FXML
    void cancel(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void logIn(ActionEvent event) {
        databaseHandler = db.getInstance() ;
        String uName = usernameL.getText();
        String pWord = passwordL.getText();
        if (uName.isEmpty() || pWord.isEmpty()) {
            alert.errorMessege("Attention","Please fill in all fields!");
        } else {
            String qu = "SELECT * FROM ACCOUNT WHERE username = '" + uName + "'";
            ResultSet rs = databaseHandler.executeQuery(qu);
            try {
                if (rs.next()) {
                    try {
                        String passwordCheck = rs.getString("password");
                        if (Objects.equals(pWord, passwordCheck)) {
                            boolean isAdmin = rs.getBoolean("isAdmin");
                            boolean isManager = rs.getBoolean("isManager");
                            boolean isCustomer = rs.getBoolean("isCustomer");
                            if (isAdmin) {
                                LoginStatus.setAdminLogged(true);
                                loadAdminDashboard();
                            } else if (isManager) {
                                LoginStatus.setManagerLogged(true);
                                LoginStatus.setManagerID("XYZ");
                                loadManagerDashboard();
                            } else if (isCustomer) {
                                LoginStatus.setCustomerLogged(true);
                                LoginStatus.setCustomerID("XYZ");
                                loadCustomerDashboard();
                            }
                        } else {
                            alert.errorMessege("Attention","Incorrect Password!");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    alert.infoMessege("Attention","Please enter a valid username!");
                }
            } catch (SQLException e) {
                alert.infoMessege("Attention","Please enter a valid username!");
            }
        }
    }

    @FXML
    void register(ActionEvent event) {
        databaseHandler = db.getInstance() ;
        String uName = usernameR.getText() ;
        String pWord = passwordR.getText() ;
        String memberName = nameR.getText() ;
        String mID = idR.getText() ;
        String contact = contactR.getText() ;
        String email = emailR.getText();
        String address = addressR.getText();
        if (uName.isEmpty() || pWord.isEmpty() || memberName.isEmpty() || mID.isEmpty() || contact.isEmpty() || email.isEmpty() || address.isEmpty()) {
            alert.errorMessege("Attention","Please fill in all fields!");
        } else {
            String qu = "SELECT * FROM ACCOUNT WHERE username = '" + uName + "'" ;
            ResultSet rs = databaseHandler.executeQuery(qu) ;
            try {
                if (rs.next()) {
                    alert.errorMessege("Attention","Username Already Exists!");

                } else {
                    if (Objects.equals(pWord,confirmPassR.getText())) {
                        String action2 = "INSERT INTO MEMBER(name,id,phonenumber,email,address) VALUES ("
                                + "'" + memberName + "',"
                                + "'" + mID + "',"
                                + "'" + contact + "',"
                                + "'" + email + "',"
                                + "'" + address + "')";
                        String action = "INSERT INTO ACCOUNT(username,password,id) VALUES ("
                                + "'" + uName + "',"
                                + "'" + pWord + "',"
                                + "'" + mID + "')";
                        if (databaseHandler.executeAction(action2) && databaseHandler.executeAction(action)) {
                            alert.confirmMessege("Success","User Registered Successfully!");
                            registerForm.setVisible(false);
                            loginForm.setVisible(true);
                        } else {
                            alert.errorMessege("Attention","Failed to Register User!");
                        }
                    } else {
                        alert.errorMessege("Attention","Passwords do not match!");

                    }
                }
            } catch (SQLException e) {
                alert.errorMessege("Attention","Failed to Register User!");
            }
        }
    }



    @FXML
    void showPassword(ActionEvent event) {
        if (showPassCheck.isSelected()) {
            showPassL.setText(passwordL.getText());
            passwordL.setVisible(false);
            showPassL.setVisible(true);
        }
        if (!showPassCheck.isSelected()) {
            passwordL.setText(showPassL.getText());
            showPassL.setVisible(false);
            passwordL.setVisible(true);

        }

    }

    @FXML
    void switchToLogin(ActionEvent event) {
        registerForm.setVisible(false);
        loginForm.setVisible(true);
    }

    @FXML
    void switchToRegister(ActionEvent event) {
        registerForm.setVisible(true);
        loginForm.setVisible(false);

    }

    @FXML
    private void loadAdminDashboard() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
        WindowLoader windowLoader = new WindowLoader();
        windowLoader.loadWindow("admin_dashboard.fxml", "Admin Dashboard");

    }

    @FXML
    private void loadManagerDashboard() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
        WindowLoader windowLoader = new WindowLoader();
        windowLoader.loadWindow("manager_dashboard.fxml", "Customer Dashboard");
    }
    
    @FXML
    private void loadCustomerDashboard() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
        WindowLoader windowLoader = new WindowLoader();
        windowLoader.loadWindow("customer_dashboard.fxml", "Customer Dashboard");

    }
}

