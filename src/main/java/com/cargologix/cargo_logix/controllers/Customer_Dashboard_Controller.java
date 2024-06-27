package com.cargologix.cargo_logix.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cargologix.cargo_logix.classes.* ;
import com.cargologix.cargo_logix.database.db;
import javafx.stage.Stage;

public class Customer_Dashboard_Controller implements Initializable {

    ObservableList<Shipment> todaysShipments = FXCollections.observableArrayList() ;

    AlertMessege alert = new AlertMessege();

    db DBhandler = db.getInstance();
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Shipment> todayShipments;

    @FXML
    private TableColumn<Shipment, String> todayContainerType;

    @FXML
    private TableColumn<Shipment, String> todayDestination;

    @FXML
    private TableColumn<Shipment, String> todayName;

    @FXML
    private TableColumn<Shipment, String> todayOutorIn;

    @FXML
    private TableColumn<Shipment, String> todayReciever;

    @FXML
    private TableColumn<Shipment, String> todaySender;

    @FXML
    private TableColumn<Shipment, String> todayType;

    @FXML
    private TableColumn<Shipment, String> todayWeight;

    Boolean customerDetailsOn = false ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCol();
        loadTodayShipmentData();
    }

    private void initCol() {
        todayName.setCellValueFactory(new PropertyValueFactory<>("name"));
        todayDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        todayWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        todayType.setCellValueFactory(new PropertyValueFactory<>("shipmentType"));
        todaySender.setCellValueFactory(new PropertyValueFactory<>("sender"));
        todayReciever.setCellValueFactory(new PropertyValueFactory<>("receiver"));
        todayOutorIn.setCellValueFactory(new PropertyValueFactory<>("outOrin"));
        todayContainerType.setCellValueFactory(new PropertyValueFactory<>("containerType"));

    }

    private void loadTodayShipmentData() {
        String qu = "SELECT * FROM SCHEDULED_SHIPMENT WHERE isApproved = 1";
        ResultSet rs = DBhandler.executeQuery(qu);
        System.out.println(rs);
        try {
            while (rs.next()) {
                String ShipmentID = rs.getString("id");
                String qu2 = "SELECT * FROM SHIPMENT WHERE id = " + ShipmentID ;
                ResultSet rs2 = DBhandler.executeQuery(qu2);
                try {
                    while (rs2.next()) {
                        Timestamp Date = rs.getTimestamp("shipmentTime");
                        LocalDate timestampDate = Date.toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
                        String formattedDate = timestampDate.format(formatter);
                        LocalDate currentDate = LocalDate.now();
                        String formattedCurrentDate = currentDate.format(formatter);
                        if (formattedDate.equals(formattedCurrentDate)) {
                            String Name = rs2.getString("name");
                            String Sender = rs2.getString("sender");
                            String Receiver = rs2.getString("receiver");
                            String Destination = rs2.getString("destination");
                            float Weight = rs2.getFloat("weight");
                            Boolean isOutgoing = rs2.getBoolean("isOutgoing");
                            String Type = rs2.getString("type");
                            String ContainerType = rs.getString("containerType");
                            Shipment newShipment = new Shipment();
                            newShipment.setId(ShipmentID);
                            newShipment.setDestination(Destination);
                            newShipment.setSender(Sender);
                            newShipment.setReceiver(Receiver);
                            newShipment.setWeight(Weight);
                            newShipment.setShipmentType(Type);
                            newShipment.setContainerType(ContainerType);
                            newShipment.setName(Name);
                            newShipment.setIsOut(isOutgoing);
                            newShipment.setOutOrin();
                            todaysShipments.add(newShipment);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Schedule_Shipment_Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        todayShipments.getItems().setAll(todaysShipments);
    }


    @FXML
    void loadAddShipment(ActionEvent event) {
        WindowLoader windowLoader = new WindowLoader();
        windowLoader.loadWindow("add_shipment.fxml", "Add Shipment Request");
    }

    @FXML
    void loadEditDetails(ActionEvent event) {
        WindowLoader windowLoader = new WindowLoader();
        windowLoader.loadWindow("edit_member.fxml", "Edit Details");

    }

    @FXML
    void loadEditShipment(ActionEvent event) {
        WindowLoader windowLoader = new WindowLoader();
        windowLoader.loadWindow("edit_shipment.fxml", "Edit Shipment Request");

    }

    @FXML
    void logOut(ActionEvent event) {
        Optional<ButtonType> response = alert.confirmMessege("Confirm Logout","Are you sure you want to logout?");
        if (response.isPresent() & response.get() == ButtonType.OK) {
            LoginStatus.setCustomerLogged(false);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();
            WindowLoader windowLoader = new WindowLoader();
            windowLoader.loadWindow("login.fxml", "Cargo Logix");
        } else {
            return ;
        }

    }
}


