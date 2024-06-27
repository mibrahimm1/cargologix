package com.cargologix.cargo_logix.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import com.cargologix.cargo_logix.database.db;
import com.cargologix.cargo_logix.classes.AlertMessege;
import com.cargologix.cargo_logix.classes.LoginStatus;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class Add_Member_Controller implements Initializable {

    db databaseHandler = db.getInstance() ;

    AlertMessege alert = new AlertMessege() ;

    @FXML
    private ComboBox<String> accountType;

    @FXML
    private TextField idA;

    @FXML
    private TextField idM;

    @FXML
    private TextField addressA;

    @FXML
    private TextField addressM;

    @FXML
    private AnchorPane adminForm;

    @FXML
    private TextField emailA;

    @FXML
    private TextField emailM;

    @FXML
    private AnchorPane managerForm;

    @FXML
    private TextField nameA;

    @FXML
    private TextField nameM;

    @FXML
    private TextField passwordA;

    @FXML
    private TextField passwordM;

    @FXML
    private TextField phoneNumberA;

    @FXML
    private TextField phoneNumberM;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField usernameA;

    @FXML
    private TextField usernameM;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accountType.getItems().addAll(
                "Manager",
                "Customer"
        );
        if (LoginStatus.isAdminLogged()) {
            adminForm.setVisible(true);
            managerForm.setVisible(false);
        } else {
            adminForm.setVisible(false);
            managerForm.setVisible(true);
        }
    }

    @FXML
    void addAccount(ActionEvent event) {
        if (LoginStatus.isAdminLogged()) {
            String Username = usernameA.getText();
            String ID = idA.getText();
            String Password = passwordA.getText();
            String Name = nameA.getText();
            String Contact = phoneNumberA.getText();
            String Email = emailA.getText();
            String Address = addressA.getText();
            String AccountType = accountType.getValue();
            if (Username.isEmpty() || Password.isEmpty() || Name.isEmpty() || Contact.isEmpty() || Email.isEmpty() || Address.isEmpty() || AccountType.isEmpty() || accountType.getValue() == null ) {
                alert.errorMessege("Error","Please enter in all fields");
            } else {
                String qu = "SELECT * FROM ACCOUNT WHERE username = '" + Username + "'" ;
                ResultSet rs = databaseHandler.executeQuery(qu) ;
                try {
                    if (rs.next()) {
                        alert.errorMessege("Attention","Username Already Exists!");

                    } else {
                        String action2 = "INSERT INTO MEMBER(name,id,phonenumber,email,address) VALUES ("
                                + "'" + Name + "',"
                                + "'" + ID + "',"
                                + "'" + Contact + "',"
                                + "'" + Email + "',"
                                + "'" + Address + "')";
                        String action ;
                        if (AccountType.equals("Customer")) {
                            action = "INSERT INTO ACCOUNT(username,password,id,isCustomer) VALUES ("
                                    + "'" + Username + "',"
                                    + "'" + Password + "',"
                                    + "'" + ID + "',"
                                    + "1)";
                        } else {
                            action = "INSERT INTO ACCOUNT(username,password,id,isManager) VALUES ("
                                    + "'" + Username + "',"
                                    + "'" + Password + "',"
                                    + "'" + ID + "',"
                                    + "1)";
                        }
                        if (databaseHandler.executeAction(action2) && databaseHandler.executeAction(action)) {
                            alert.confirmMessege("Success","User Added Successfully!");
                        } else {
                            alert.errorMessege("Attention","Failed to Add User!");
                        }
                    }
                } catch (SQLException e) {
                    alert.errorMessege("Attention","Failed to Add User!");
                }
            }
        } else if (LoginStatus.isManagerLogged()) {
            String Username = usernameM.getText();
            String ID = idM.getText();
            String Password = passwordM.getText();
            String Name = nameM.getText();
            String Contact = phoneNumberM.getText();
            String Email = emailM.getText();
            String Address = addressM.getText();
            if (Username.isEmpty() || Password.isEmpty() || Name.isEmpty() || Contact.isEmpty() || Email.isEmpty() || Address.isEmpty()) {
                alert.errorMessege("Error","Please enter in all fields");
            } else {
                String qu = "SELECT * FROM ACCOUNT WHERE username = '" + Username + "'" ;
                ResultSet rs = databaseHandler.executeQuery(qu) ;
                try {
                    if (rs.next()) {
                        alert.errorMessege("Attention","Username Already Exists!");

                    } else {
                        String action2 = "INSERT INTO MEMBER(name,id,phonenumber,email,address) VALUES ("
                                + "'" + Name + "',"
                                + "'" + ID + "',"
                                + "'" + Contact + "',"
                                + "'" + Email + "',"
                                + "'" + Address + "')";
                        String action = "INSERT INTO ACCOUNT(username,password,id,isCustomer) VALUES ("
                                + "'" + Username + "',"
                                + "'" + Password + "',"
                                + "'" + ID + "',"
                                + "1)";
                        if (databaseHandler.executeAction(action2) && databaseHandler.executeAction(action)) {
                            alert.confirmMessege("Success","User Added Successfully!");
                        } else {
                            alert.errorMessege("Attention","Failed to Add User!");
                        }
                    }
                } catch (SQLException e) {
                    alert.errorMessege("Attention","Failed to Add User!");
                }
            }
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
}
