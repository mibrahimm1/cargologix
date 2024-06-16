package com.cargologix.cargo_logix.controllers;

import com.cargologix.cargo_logix.database.db;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.cargologix.cargo_logix.classes.Shipment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import com.cargologix.cargo_logix.classes.AlertMessege;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Schedule_Shipment_Controller implements Initializable {

    ObservableList<Shipment> unscheduledShipments = FXCollections.observableArrayList() ;
    ObservableList<Shipment> scheduledShipments = FXCollections.observableArrayList() ;

    @FXML
    private TableColumn<Shipment, String> containerA;

    @FXML
    private TableColumn<Shipment, String> dateA;

    @FXML
    private TableColumn<Shipment, String> destA;

    @FXML
    private TableColumn<Shipment, String> shipIDA;

    @FXML
    private TableColumn<Shipment, String> typeU;

    @FXML
    private TableColumn<Shipment, String> weightU;

    @FXML
    private TableColumn<Shipment, String> shipIDU;

    @FXML
    private TableColumn<Shipment, String> destU;


    @FXML
    private TableView<Shipment> approvedRequests;

    @FXML
    private Button cancelShipment;

    @FXML
    private ComboBox<String> containerSelect;

    @FXML
    private DatePicker dateSelect;

    @FXML
    private Label fragileD;

    @FXML
    private Label nameD;

    @FXML
    private Label outOrinD;

    @FXML
    private TableView<Shipment> pendingRequests;

    @FXML
    private Label recieverD;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button scheduleShipment;

    @FXML
    private Label senderD;

    @FXML
    private TextField shipmentID;

    @FXML
    private Label tempcontrolD;

    AlertMessege alert = new AlertMessege();

    db DBhandler = db.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        containerSelect.getItems().setAll("Dry", "Open Top", "Reefer", "Tank", "Flat Rack", "Ventilated", "Insulated Thermal");
        initCol();
        loadData();

    }

    @FXML
    void viewDetails(ActionEvent event) {
        String ShipmentID = shipmentID.getText() ;
        if (ShipmentID.isEmpty()) {
            alert.errorMessege("Error","Please enter a Shipment ID");
        } else {
            String qu = "SELECT * FROM SHIPMENT WHERE id = " + ShipmentID ;
            ResultSet rs = DBhandler.executeQuery(qu);
            try {
                if (rs.next()) {
                    String shipmentName = rs.getString("name");
                    String shipmentSender = rs.getString("sender");
                    String shipmentReceiver = rs.getString("receiver");
                    boolean outgoing = rs.getBoolean("isOutgoing") ;
                    boolean isfragile = rs.getBoolean("isFragile");
                    boolean istempControl = rs.getBoolean("isTempControl");
                    nameD.setText("Shipment Name: "+ shipmentName);
                    senderD.setText("Sender: "+ shipmentSender);
                    recieverD.setText("Receiver: "+ shipmentReceiver);
                    outOrinD.setText("Outgoing or Incoming: " + (outgoing? "Outgoing" : "Incoming"));
                    fragileD.setText("Fragile: " + (isfragile?"Yes":"No"));
                    tempcontrolD.setText("Temperature Controlled: " + (istempControl?"Yes":"No"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void loadData() {
        String qu = "SELECT * FROM SHIPMENT WHERE isScheduled = 0";
        ResultSet rs = DBhandler.executeQuery(qu);
        System.out.println(rs);
        try {
            while (rs.next()) {
                String ShipmentID = rs.getString("id");
                String Destination = rs.getString("destination");
                String Weight = rs.getString("weight");
                String ShipmentType = rs.getString("type");
                System.out.println(ShipmentID + " " + Destination + " " + Weight + " " + ShipmentType);
                unscheduledShipments.add(new Shipment(ShipmentID, Destination, Weight, ShipmentType));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Schedule_Shipment_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        pendingRequests.getItems().setAll(unscheduledShipments);

        qu = "SELECT * FROM `SCHEDULED_SHIPMENT` WHERE shipmentTime > CURRENT_TIMESTAMP AND isApproved = 1";
        rs = DBhandler.executeQuery(qu);
        System.out.println(rs);
        try {
            while (rs.next()) {
                String ShipID = rs.getString("id");
                String Destination = null;
                String ContainerType = rs.getString("containerType");
                String ShipmentTime = rs.getString("shipmentTime");
                String qu2 = "SELECT * FROM `SHIPMENT` WHERE id = " + ShipID;
                ResultSet rs2 = null;
                try {
                    rs2 = DBhandler.executeQuery(qu2);
                    if (rs2.next()) {
                        Destination = rs2.getString("destination");
                    }
                    rs2.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                if (Destination != null) {
                    System.out.println(ShipID + " " + Destination + " " + ContainerType + " " + ShipmentTime);
                    Shipment newShipment = new Shipment();  // Ensure this initializes all fields properly
                    newShipment.setId(ShipID);
                    newShipment.setDestination(Destination);
                    newShipment.setContainerType(ContainerType);
                    newShipment.setScheduledTime(ShipmentTime);
                    scheduledShipments.add(newShipment);
                }
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Schedule_Shipment_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        approvedRequests.getItems().setAll(scheduledShipments);
    }

    private void initCol() {
        shipIDU.setCellValueFactory(new PropertyValueFactory<>("id"));
        destU.setCellValueFactory(new PropertyValueFactory<>("destination"));
        weightU.setCellValueFactory(new PropertyValueFactory<>("weight"));
        typeU.setCellValueFactory(new PropertyValueFactory<>("shipmentType"));

        shipIDA.setCellValueFactory(new PropertyValueFactory<>("id"));
        destA.setCellValueFactory(new PropertyValueFactory<>("destination"));
        containerA.setCellValueFactory(new PropertyValueFactory<>("containerType"));
        dateA.setCellValueFactory(new PropertyValueFactory<>("scheduledTime"));

    }

    @FXML
    void scheduleShipment(ActionEvent event) {
        String shipID = shipmentID.getText();
        if (shipmentID.getText().isEmpty()) {
            alert.errorMessege("Error","Please enter a valid Shipment ID");
        } else {
            LocalDate scheduledate = dateSelect.getValue();
            String containertype = containerSelect.getValue();
            LocalDateTime localDateTime = scheduledate.atStartOfDay();
            Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
            Timestamp scheduleDate = Timestamp.from(instant) ;
            if (containertype.isEmpty() || scheduledate == null) {
                alert.errorMessege("Error","Please enter all necessary fields.");
            } else {
                String action1 = "UPDATE SHIPMENT SET isScheduled = 1 WHERE id = " + shipID ;
                String action = "INSERT INTO SCHEDULED_SHIPMENT (id,containerType, shipmentTime, isApproved) VALUES ('"
                        + shipID + "','" + containertype + "','" + scheduleDate + "',0)" ;
                if (DBhandler.executeAction(action1) && DBhandler.executeAction(action)) {
                    alert.confirmMessege("Success","The shipment had been scheduled and is sent for approval");
                    loadData();
                } else {
                    alert.errorMessege("Error","Failed to schedule shipment");
                }
            }
        }

    }


}
