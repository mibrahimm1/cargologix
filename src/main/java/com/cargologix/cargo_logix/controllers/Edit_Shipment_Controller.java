package com.cargologix.cargo_logix.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import com.cargologix.cargo_logix.database.db;
import com.cargologix.cargo_logix.classes.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Edit_Shipment_Controller implements Initializable {
    boolean outOrin = false ;
    boolean detailsOn = false ;

    db DBHandler = db.getInstance();
    AlertMessege alert = new AlertMessege();
    @FXML
    private RadioButton isFragile;
    @FXML
    private ComboBox<String> newTypeBox;
    @FXML
    private TextField shipmentID;

    @FXML
    private RadioButton isIncoming;

    @FXML
    private RadioButton isOutgoing;

    @FXML
    private RadioButton isTempC;

    @FXML
    private TextField newDestination;

    @FXML
    private TextField newName;

    @FXML
    private TextField newReciever;

    @FXML
    private TextField newSender;

    @FXML
    private TextField newWeight;

    @FXML
    private AnchorPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newTypeBox.getItems().addAll(
                "General Cargo",
                "Dry Goods",
                "Liquid Goods",
                "Manufactured Goods",
                "Agricultural Products",
                "Perishable Goods"
        );

    }

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void editRequest(ActionEvent event) {
        if(!detailsOn) {
            alert.errorMessege("Error","Please enter a Shipment ID to continue");
        } else {
            String shipName = newName.getText();
            String sender = newName.getText();
            String reciever = newReciever.getText();
            String destination = newDestination.getText() ;
            String Weight = newWeight.getText();
            try {
                checkWeight(Weight);
                float weightinkg = Float.parseFloat(Weight) ;
                boolean isOut = isOutgoing.isSelected();
                String shipType = newTypeBox.getValue() ;
                boolean fragile = isFragile.isSelected() ;
                boolean tempControl = isTempC.isSelected() ;
                if (shipName.isEmpty() || sender.isEmpty() || reciever.isEmpty() || destination.isEmpty()
                        || Weight.isEmpty() || !outOrin || shipType.isEmpty()) {
                    alert.errorMessege("Error","Please fill in all necessary fields");
                } else {
                    String action = "UPDATE SHIPMENT SET"
                            + " name = '" + shipName + "',"
                            + " sender = '" + sender + "',"
                            + " receiver = '" + reciever + "',"
                            + " destination = '" + destination + "',"
                            + " weight = '" + weightinkg + "',"
                            + " isOutgoing = '" + (isOut?1:0) + "',"
                            + " type = '" + shipType + "',"
                            + " isFragile = '" + (fragile?1:0) + "',"
                            + " isTempControl = '" + (tempControl?1:0) + "'"
                            + " WHERE id = '" + shipmentID.getText() + "'" ;
                    if (DBHandler.executeAction(action)) {
                        shipmentID.clear();
                        newName.clear();
                        newSender.clear();
                        newReciever.clear();
                        newDestination.clear();
                        newWeight.clear();
                        alert.confirmMessege("Success","Shipment edited added successfully!");
                    } else {
                        shipmentID.clear();
                        newName.clear();
                        newSender.clear();
                        newReciever.clear();
                        newDestination.clear();
                        newWeight.clear();
                        alert.errorMessege("Error","Failed to edit shipment");
                    }
                }
            } catch (NumberFormatException e){
                alert.errorMessege("Error","Please enter all necessary fields");
            }
        }

    }

    @FXML
    void loadDetails(ActionEvent event) {
        detailsOn = false ;
        String ShipmentID = shipmentID.getText() ;
        if (ShipmentID.isEmpty()) {
            newName.clear();
            newSender.clear();
            newReciever.clear();
            newDestination.clear();
            newWeight.clear();
            alert.errorMessege("Error","Please enter a shipment ID");
        } else {
            String qu = "SELECT * FROM SHIPMENT WHERE id = '" + ShipmentID + "'" ;
            ResultSet rs = DBHandler.executeQuery(qu) ;
            try {
                if (rs.next()) {
                    String Name = rs.getString("name");
                    String Sender = rs.getString("sender");
                    String Receiver = rs.getString("receiver");
                    String Destination = rs.getString("destination");
                    String Weight = rs.getString("weight");
                    String Type = rs.getString("type");
                    boolean Outgoing = rs.getBoolean("isOutgoing");
                    boolean Fragile = rs.getBoolean("isFragile");
                    boolean TempControlled = rs.getBoolean("isTempControl");
                    newName.setText(Name);
                    newSender.setText(Sender);
                    newReciever.setText(Receiver);
                    newDestination.setText(Destination);
                    newWeight.setText(Weight);
                    newTypeBox.setValue(Type);
                    if(Outgoing) {
                        setOutgoing();
                    } else {
                        setIncoming();
                    }
                    if (Fragile) {
                        isFragile.setSelected(true);
                    }
                    if (TempControlled) {
                        isTempC.setSelected(true);
                    }
                    detailsOn = true ;
                } else {
                    alert.errorMessege("Error","Please enter a valid shipment ID");
                    newName.clear();
                    newSender.clear();
                    newReciever.clear();
                    newDestination.clear();
                    newWeight.clear();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @FXML
    void checkWeight(ActionEvent event) {
        String Weight = newWeight.getText();
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

    void setIncoming() {
        isOutgoing.setSelected(false);
        isIncoming.setSelected(true);
        outOrin = true ;
    }

    void setOutgoing() {
        isOutgoing.setSelected(true);
        isIncoming.setSelected(false);
        outOrin = true ;

    }
}

