package com.cargologix.cargo_logix.controllers;

import com.cargologix.cargo_logix.classes.AlertMessege;
import com.cargologix.cargo_logix.classes.LoginStatus;
import com.cargologix.cargo_logix.database.db;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Edit_Member_Controller implements Initializable {

    AlertMessege alert = new AlertMessege();
    db DBhandler = db.getInstance();

    @FXML
    private TextField addressM;

    @FXML
    private TextField emailM;

    @FXML
    private AnchorPane managerForm;

    @FXML
    private TextField nameM;

    @FXML
    private TextField passwordM;

    @FXML
    private TextField phoneNumberM;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField usernameM;

    String ID ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (LoginStatus.isManagerLogged()) {
            ID = LoginStatus.getManagerID();
        } else if (LoginStatus.isCustomerLogged()) {
            ID = LoginStatus.getCustomerID();
        }
        String qu = "SELECT * FROM ACCOUNT WHERE id = '" + ID + "'";
        ResultSet rs = DBhandler.executeQuery(qu);
        try {
            while (rs.next()) {
                String Username = rs.getString("username");
                String Password = rs.getString("password");
                String qu2 = "SELECT * FROM MEMBER WHERE id = '" + ID + "'";
                ResultSet rs2 = DBhandler.executeQuery(qu2);
                try {
                    while (rs2.next()) {
                        String Name = rs2.getString("name");
                        String Email = rs2.getString("email");
                        String Contact = rs2.getString("phonenumber");
                        String Address = rs2.getString("address");


                        usernameM.setText(Username);
                        passwordM.setText(Password);
                        nameM.setText(Name);
                        phoneNumberM.setText(Contact);
                        emailM.setText(Email);
                        addressM.setText(Address);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void editAccount(ActionEvent event) {
        String Username = usernameM.getText();
        String Password = passwordM.getText();
        String Name = nameM.getText();
        String Contact = phoneNumberM.getText();
        String Email = emailM.getText();
        String Address = addressM.getText();
        if (Username.isEmpty() || Password.isEmpty() || Name.isEmpty() || Contact.isEmpty() || Email.isEmpty() || Address.isEmpty()) {
            alert.errorMessege("Error","Please enter in all fields");
        } else {
            String qu = "SELECT * FROM ACCOUNT WHERE username = '" + Username + "'" ;
            ResultSet rs = DBhandler.executeQuery(qu) ;
            try {
                if (rs.next()) {
                    alert.errorMessege("Attention","Username Already Exists!");

                } else {
                    String action2 = "UPDATE MEMBER SET"
                            + " name = '" + Name + "',"
                            + " phonenumber = '" + Contact + "',"
                            + " email = '" + Email + "',"
                            + " address = '" + Address
                            + "' WHERE id = '" + ID + "'";
                    String action = "UPDATE ACCOUNT SET"
                            + " username = '" + Username + "',"
                            + " password = '" + Password
                            + "' WHERE id = '" + ID + "'";

                    if (DBhandler.executeAction(action2) && DBhandler.executeAction(action)) {
                        alert.confirmMessege("Success","Updated Successfully!");
                        Stage stage = (Stage) rootPane.getScene().getWindow();
                        stage.close();
                    } else {
                        alert.errorMessege("Attention","Failed to Update Details!");
                        Stage stage = (Stage) rootPane.getScene().getWindow();
                        stage.close();
                    }
                }
            } catch (SQLException e) {
                alert.errorMessege("Attention","Failed to Update Details!");
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.close();
            }
        }
    }
}

