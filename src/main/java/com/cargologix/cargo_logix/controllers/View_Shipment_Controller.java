package com.cargologix.cargo_logix.controllers;

import com.cargologix.cargo_logix.classes.AlertMessege;
import com.cargologix.cargo_logix.classes.LoginStatus;
import com.cargologix.cargo_logix.database.db;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import com.cargologix.cargo_logix.classes.Shipment;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class View_Shipment_Controller implements Initializable {

    ObservableList<Shipment> shipmentList = FXCollections.observableArrayList();

    AlertMessege alert = new AlertMessege();
    db DBHandler = db.getInstance();

    @FXML
    private TableColumn<Shipment, String> destinationC;

    @FXML
    private TextField filterDestination;

    @FXML
    private RadioButton filterFragile;

    @FXML
    private RadioButton filterIncoming;

    @FXML
    private TextField filterName;

    @FXML
    private RadioButton filterOutgoing;

    @FXML
    private TextField filterReciever;

    @FXML
    private TextField filterSender;

    @FXML
    private RadioButton filterTemperatureControl;

    @FXML
    private ComboBox<String> filterType;

    @FXML
    private TableColumn<Shipment, String> fragileC;

    @FXML
    private TableColumn<Shipment, String> idC;

    @FXML
    private TableColumn<Shipment, String> nameC;

    @FXML
    private TableColumn<Shipment, String> outOrinC;

    @FXML
    private TableColumn<Shipment, String> recieverC;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableColumn<Shipment, String> senderC;

    @FXML
    private TableView<Shipment> shipmentTable;

    @FXML
    private TableColumn<Shipment, String> tempC;

    @FXML
    private TableColumn<Shipment, String> typeC;

    @FXML
    private TableColumn<Shipment, String> weightC;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCol();
        filterType.getItems().addAll(
                "General Cargo",
                "Dry Goods",
                "Liquid Goods",
                "Manufactured Goods",
                "Agricultural Products",
                "Perishable Goods"
        );
        loadData();
    }

    @FXML
    private void close(ActionEvent event) {

    }

    private void initCol() {
        idC.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameC.setCellValueFactory(new PropertyValueFactory<>("name"));
        destinationC.setCellValueFactory(new PropertyValueFactory<>("destination"));
        weightC.setCellValueFactory(new PropertyValueFactory<>("weight"));
        typeC.setCellValueFactory(new PropertyValueFactory<>("shipmentType"));
        senderC.setCellValueFactory(new PropertyValueFactory<>("sender"));
        recieverC.setCellValueFactory(new PropertyValueFactory<>("receiver"));
        outOrinC.setCellValueFactory(new PropertyValueFactory<>("outOrin"));
        fragileC.setCellValueFactory(new PropertyValueFactory<>("fragile"));
        tempC.setCellValueFactory(new PropertyValueFactory<>("tempControl"));

    }

    private void clearTable() {
        shipmentList.clear();
        shipmentTable.getItems().clear();
    }
    @FXML
    private void loadData(ActionEvent event) {
        filterOutgoing.setSelected(false);
        filterIncoming.setSelected(false);
        clearTable();

        String qu = null;

        if (LoginStatus.isAdminLogged()) {
            qu = "SELECT * FROM SHIPMENT";
        } else if (LoginStatus.isManagerLogged()) {
            qu = "SELECT * FROM SHIPMENT WHERE isScheduled = 0";
        }

        if (qu != null) {
            ResultSet rs = DBHandler.executeQuery(qu);
            System.out.println(rs);
            try {
                while (rs.next()) {
                    String ShipmentID = rs.getString("id");
                    String Name = rs.getString("name");
                    String Sender = rs.getString("sender");
                    String Receiver = rs.getString("receiver");
                    String Destination = rs.getString("destination");
                    Float Weight = rs.getFloat("weight");
                    String Type = rs.getString("type");
                    Boolean Fragile = rs.getBoolean("isFragile");
                    Boolean TempC = rs.getBoolean("isTempControl");
                    Boolean IsOut = rs.getBoolean("isOutgoing");

                    Shipment newShipment = new Shipment();
                    newShipment.setId(ShipmentID);
                    newShipment.setName(Name);
                    newShipment.setShipmentType(Type);
                    newShipment.setTempControl(TempC);
                    newShipment.setIsOut(IsOut);
                    newShipment.setOutOrin();
                    newShipment.setDestination(Destination);
                    newShipment.setSender(Sender);
                    newShipment.setReceiver(Receiver);
                    newShipment.setWeight(Weight);
                    newShipment.setFragile(Fragile);
                    newShipment.setIsOut(IsOut);
                    newShipment.setOutOrin();

                    shipmentList.add(newShipment);
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(Schedule_Shipment_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }

            shipmentTable.getItems().setAll(shipmentList);
        } else {
            System.out.println("No valid login status found.");
        }
    }


    private void loadData() {
        clearTable();

        String qu = null;
        if (LoginStatus.isAdminLogged()) {
            qu = "SELECT * FROM SHIPMENT";
        } else if (LoginStatus.isManagerLogged()) {
            qu = "SELECT * FROM SHIPMENT WHERE isScheduled = 0";
        }

        if (qu != null) {
            ResultSet rs = DBHandler.executeQuery(qu);
            System.out.println(rs);
            try {
                while (rs.next()) {
                    String ShipmentID = rs.getString("id");
                    String Name = rs.getString("name");
                    String Sender = rs.getString("sender");
                    String Receiver = rs.getString("receiver");
                    String Destination = rs.getString("destination");
                    Float Weight = rs.getFloat("weight");
                    String Type = rs.getString("type");
                    Boolean Fragile = rs.getBoolean("isFragile");
                    Boolean TempC = rs.getBoolean("isTempControl");
                    Boolean IsOut = rs.getBoolean("isOutgoing");
                    Shipment newShipment = new Shipment();
                    newShipment.setId(ShipmentID);
                    newShipment.setName(Name);
                    newShipment.setShipmentType(Type);
                    newShipment.setTempControl(TempC);
                    newShipment.setIsOut(IsOut);
                    newShipment.setOutOrin();
                    newShipment.setDestination(Destination);
                    newShipment.setSender(Sender);
                    newShipment.setFragile(Fragile);
                    newShipment.setReceiver(Receiver);
                    newShipment.setWeight(Weight);
                    newShipment.setIsOut(rs.getBoolean("isOutgoing"));
                    newShipment.setOutOrin();
                    shipmentList.add(newShipment);
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(Schedule_Shipment_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }

            shipmentTable.getItems().setAll(shipmentList);
        } else {
            System.out.println("No valid login status found.");
        }
    }


    @FXML
    private void loadDataFilter(ActionEvent event) {
        clearTable();
        String qu = null;
        if (LoginStatus.isAdminLogged()) {
            qu = "SELECT * FROM SHIPMENT WHERE 1=1";
        } else if (LoginStatus.isManagerLogged()) {
            qu = "SELECT * FROM SHIPMENT WHERE isScheduled = 0 AND WHERE 1=1";
        }

        if(!filterName.getText().isEmpty()) {
            qu += " AND name = '" + filterName.getText() + "'";
        }
        if(filterType.getValue() != null && !filterType.getValue().isEmpty()) {
            qu += " AND type = '" + filterType.getValue() + "'";
        }
        if(!filterSender.getText().isEmpty()) {
            qu += " AND sender = '" + filterSender.getText() + "'";
        }
        if(!filterReciever.getText().isEmpty()) {
            qu += " AND receiver = '" + filterReciever.getText() + "'";
        }
        if(!filterDestination.getText().isEmpty()) {
            qu += " AND destination = '" + filterDestination.getText() + "'";
        }
        if(filterFragile.isSelected()) {
            qu += " AND isFragile = 1";
        }
        if(filterTemperatureControl.isSelected()) {
            qu += " AND isTempControl = 1";
        }
        if (filterOutgoing.isSelected()) {
            qu += " AND isOutgoing = 1";
        }
        if (filterIncoming.isSelected()) {
            qu += " AND isOutgoing = 0";
        }
        if (qu.equals("SELECT * FROM SHIPMENT WHERE 1=1") || qu.equals("SELECT * FROM SHIPMENT WHERE isScheduled = 0 AND WHERE 1=1")) {
            if (LoginStatus.isAdminLogged()) {
                qu = "SELECT * FROM SHIPMENT";
            } else if (LoginStatus.isManagerLogged()) {
                qu = "SELECT * FROM SHIPMENT WHERE isScheduled = 0";
            }
        }
        System.out.println(qu);
        ResultSet rs = DBHandler.executeQuery(qu);
        System.out.println(rs);
        try {
            while (rs.next()) {
                String ShipmentID = rs.getString("id");
                String Name = rs.getString("name");
                String Sender = rs.getString("sender");
                String Receiver = rs.getString("receiver");
                String Destination = rs.getString("destination");
                Float Weight = rs.getFloat("weight");
                String Type = rs.getString("type");
                Boolean Fragile = rs.getBoolean("isFragile");
                Boolean TempC = rs.getBoolean("isTempControl");
                Boolean IsOut = rs.getBoolean("isOutgoing");
                Shipment newShipment = new Shipment();
                newShipment.setId(ShipmentID);
                newShipment.setName(Name);
                newShipment.setShipmentType(Type);
                newShipment.setTempControl(TempC);
                newShipment.setIsOut(IsOut);
                newShipment.setOutOrin();
                newShipment.setDestination(Destination);
                newShipment.setSender(Sender);
                newShipment.setReceiver(Receiver);
                newShipment.setWeight(Weight);
                newShipment.setFragile(Fragile);
                newShipment.setIsOut(rs.getBoolean("isOutgoing"));
                newShipment.setOutOrin();
                shipmentList.add(newShipment);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Schedule_Shipment_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        shipmentTable.getItems().setAll(shipmentList);
    }

    @FXML
    void setIncoming(ActionEvent event) {
        filterOutgoing.setSelected(false);
        filterIncoming.setSelected(true);
    }

    @FXML
    void setOutgoing(ActionEvent event) {
        filterOutgoing.setSelected(true);
        filterIncoming.setSelected(false);

    }

    }

