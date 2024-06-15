package com.cargologix.cargo_logix.controllers;

import com.cargologix.cargo_logix.classes.AlertMessege;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import com.cargologix.cargo_logix.database.db;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Add_Shipment_Controller implements Initializable {

    AlertMessege alert = new AlertMessege();

    @FXML
    private TextField destinationName;

    @FXML
    private RadioButton isFragile;

    @FXML
    private RadioButton isIncoming;

    @FXML
    private RadioButton isOutgoing;

    @FXML
    private RadioButton isTempControl;

    @FXML
    private TextField recieverName;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField senderName;

    @FXML
    private TextField shipmentName;

    @FXML
    private ComboBox<String> shipmentType;

    @FXML
    private TextField weight;

    boolean outOrin = false ;


    db databaseHandler ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shipmentType.getItems().addAll(
                "General Cargo",
                "Dry Goods",
                "Liquid Goods",
                "Manufactured Goods",
                "Agricultural Products",
                "Perishable Goods"
        );

    }

    @FXML
    void addShipment(ActionEvent event) {
        databaseHandler = db.getInstance() ;
        String shipName = shipmentName.getText();
        String sender = senderName.getText();
        String reciever = recieverName.getText();
        String destination = destinationName.getText() ;
        String Weight = weight.getText();
        try {
            checkWeight(Weight);
            float weightinkg = Float.parseFloat(Weight) ;
            boolean isOut = isOutgoing.isSelected();
            String shipType = shipmentType.getValue() ;
            boolean fragile = isFragile.isSelected() ;
            boolean tempControl = isTempControl.isSelected() ;
            if (shipName.isEmpty() || sender.isEmpty() || reciever.isEmpty() || destination.isEmpty()
            || Weight.isEmpty() || !outOrin || shipType.isEmpty()) {
                alert.errorMessege("Error","Please fill in all necessary fields");
            } else {
                String action = "INSERT INTO SHIPMENT(name,sender,receiver,destination,weight,isOutgoing,type,isFragile,isTempControl) VALUES ( "
                        + "'" + shipName + "',"
                        + "'" + sender + "',"
                        + "'" + reciever + "',"
                        + "'" + destination + "',"
                        + "'" + weightinkg + "',"
                        + "'" + (isOut?1:0) + "',"
                        + "'" + shipType + "',"
                        + "'" + (fragile?1:0) + "',"
                        + "'" + (tempControl?1:0) + "' )" ;
                if (databaseHandler.executeAction(action)) {
                    alert.confirmMessege("Success","Shipment request added successfully!");
                } else {
                    alert.errorMessege("Error","Failed to add shipment");
                }
            }
        } catch (NumberFormatException e){
            alert.errorMessege("Error","Please enter all necessary fields");
        }

    }

    @FXML
    void cancelShipment(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close() ;

    }

    @FXML
    void checkWeight(ActionEvent event) {
        String Weight = weight.getText();
        try {
            float weightinkg = Float.parseFloat(Weight) ;
        } catch (NumberFormatException e){
            alert.errorMessege("Error","Please enter a valid weight value");
        }
    }

    void checkWeight(String Weight) {
        try {
            float weightinkg = Float.parseFloat(Weight) ;
        } catch (NumberFormatException e){
            alert.errorMessege("Error","Please enter a valid weight value");
        }
    }


    @FXML
    void setIncoming(ActionEvent event) {
        isOutgoing.setSelected(false);
        isIncoming.setSelected(true);
        outOrin = true ;

    }

    @FXML
    void setOutgoing(ActionEvent event) {
        isOutgoing.setSelected(true);
        isIncoming.setSelected(false);
        outOrin = true ;

    }
}
