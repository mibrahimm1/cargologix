package com.cargologix.cargo_logix.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.cargologix.cargo_logix.classes.Shipment;
import com.cargologix.cargo_logix.database.db;
import com.cargologix.cargo_logix.classes.AlertMessege;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Approve_Shipment_Controller implements Initializable {

    ObservableList<Shipment> pendingShipments = FXCollections.observableArrayList() ;
    ObservableList<Shipment> sameDateShipments = FXCollections.observableArrayList() ;

    AlertMessege alert = new AlertMessege();

    db DBhandler = db.getInstance();

    @FXML
    private TableColumn<Shipment, String> dateP;

    @FXML
    private DatePicker dateSelect;

    @FXML
    private TableColumn<Shipment, String> destP;

    @FXML
    private TableColumn<Shipment, String> destS;

    @FXML
    private TableColumn<Shipment, String> idP;

    @FXML
    private TableColumn<Shipment, String> idS;

    @FXML
    private TableColumn<Shipment, String> outOrinP;

    @FXML
    private TableColumn<Shipment, String> outOrinS;

    @FXML
    private TableView<Shipment> pendingRequestVIew;

    @FXML
    private TableColumn<Shipment, String> recieverP;

    @FXML
    private TableView<Shipment> sameDateView;

    @FXML
    private TableColumn<Shipment, String> senderP;

    @FXML
    private TextField shipmentID;

    @FXML
    private TableColumn<Shipment, String> weightP;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCol();
        loadData();

    }

    private void loadData() {
        String qu = "SELECT * FROM SHIPMENT WHERE isScheduled = 1";
        ResultSet rs = DBhandler.executeQuery(qu);
        System.out.println(rs);
        try {
            while (rs.next()) {
                String ShipmentID = rs.getString("id");
                String Sender = rs.getString("sender");
                String Receiver = rs.getString("receiver");
                String Destination = rs.getString("destination");
                Float Weight = rs.getFloat("weight");
                String ShipmentTime = "" ;
                String qu2 = "SELECT * FROM SCHEDULED_SHIPMENT WHERE id = " + ShipmentID + " AND isApproved = 0" ;
                ResultSet rs2 = DBhandler.executeQuery(qu2);
                try {
                    if (rs2.next()) {
                        ShipmentTime = rs2.getString("shipmentTime");
                        Shipment newShipment = new Shipment();
                        newShipment.setId(ShipmentID);
                        newShipment.setDestination(Destination);
                        newShipment.setSender(Sender);
                        newShipment.setReceiver(Receiver);
                        newShipment.setWeight(Weight);
                        newShipment.setIsOut(rs.getBoolean("isOutgoing"));
                        newShipment.setOutOrin();
                        newShipment.setScheduledTime(ShipmentTime);
                        pendingShipments.add(newShipment);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Schedule_Shipment_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        pendingRequestVIew.getItems().setAll(pendingShipments);
    }

    private void initCol() {
        idP.setCellValueFactory(new PropertyValueFactory<>("id"));
        destP.setCellValueFactory(new PropertyValueFactory<>("destination"));
        weightP.setCellValueFactory(new PropertyValueFactory<>("weight"));
        senderP.setCellValueFactory(new PropertyValueFactory<>("sender"));
        recieverP.setCellValueFactory(new PropertyValueFactory<>("receiver"));
        outOrinP.setCellValueFactory(new PropertyValueFactory<>("outOrin"));
        dateP.setCellValueFactory(new PropertyValueFactory<>("scheduledTime"));
        idS.setCellValueFactory(new PropertyValueFactory<>("id"));
        destS.setCellValueFactory(new PropertyValueFactory<>("destination"));
        outOrinS.setCellValueFactory(new PropertyValueFactory<>("outOrin"));

    }

    @FXML
    void approveShipment(ActionEvent event) {

    }

    @FXML
    void cancelShipment(ActionEvent event) {

    }

    @FXML
    void rejectShipment(ActionEvent event) {

    }

    @FXML
    void viewShipments(ActionEvent event) {
        sameDateShipments.clear();
        sameDateView.getItems().clear();
        LocalDate dateSelected = dateSelect.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        String formattedDateSelected = dateSelected.format(formatter);
        System.out.println(formattedDateSelected);
        String qu = "SELECT * FROM SCHEDULED_SHIPMENT WHERE isApproved = 1" ;
        ResultSet rs = DBhandler.executeQuery(qu);
        try {
            while (rs.next()) {
                Timestamp Date = rs.getTimestamp("shipmentTime");
                LocalDate timestampDate = Date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                String formattedDate = timestampDate.format(formatter);
                System.out.println(formattedDate);
                if (formattedDateSelected.equals(formattedDate)) {
                    String ShipID = rs.getString("id");
                    String qu2 = "SELECT * FROM SHIPMENT WHERE id = " + ShipID ;
                    ResultSet rs2 = DBhandler.executeQuery(qu2);
                    if (rs2.next()) {
                        String Destination = rs2.getString("destination");
                        Shipment newShipment = new Shipment();
                        newShipment.setId(ShipID);
                        newShipment.setDestination(Destination);
                        newShipment.setIsOut(rs2.getBoolean("isOutgoing"));
                        newShipment.setOutOrin();
                        sameDateShipments.add(newShipment);

                    }
                    rs2.close();
                }
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sameDateView.getItems().setAll(sameDateShipments);

    }
}
