package com.cargologix.cargo_logix.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cargologix.cargo_logix.classes.* ;
import com.cargologix.cargo_logix.database.db;

public class Admin_Dashboard_Controller implements Initializable {

    ObservableList<Shipment> todaysShipments = FXCollections.observableArrayList() ;
    ObservableList<Member> managers = FXCollections.observableArrayList() ;

    ObservableList<Member> customers = FXCollections.observableArrayList() ;
    AlertMessege alert = new AlertMessege();

    db DBhandler = db.getInstance();

    @FXML
    private TableView<Shipment> todayShipments;
    @FXML
    private TableColumn<Member, String> addressC;

    @FXML
    private TableColumn<Member, String> addressM;

    @FXML
    private TableColumn<Member, String> contactC;

    @FXML
    private TableColumn<Member, String> contactM;

    @FXML
    private ListView<?> customerDetailList;

    @FXML
    private TextField customerDetailsID;

    @FXML
    private AnchorPane customerSection;

    @FXML
    private TableView<Member> customerTable;

    @FXML
    private TextField editContactC;

    @FXML
    private TextField editContactM;

    @FXML
    private TextField editIDC;

    @FXML
    private TextField editIDM;

    @FXML
    private TextField editNameC;

    @FXML
    private TextField editNameM;

    @FXML
    private TableColumn<Member, String> emailC;

    @FXML
    private TableColumn<Member, String> emailM;

    @FXML
    private TextField enterAddressC;

    @FXML
    private TextField enterAddressM;

    @FXML
    private TextField enterEmailC;

    @FXML
    private TextField enterEmailM;

    @FXML
    private TextField enterIDm;

    @FXML
    private AnchorPane homeSection;

    @FXML
    private TableColumn<Member, String> idC;

    @FXML
    private TableColumn<Member, String> idM;

    @FXML
    private ListView<?> managerDetailList;

    @FXML
    private AnchorPane managerSection;

    @FXML
    private TableView<Member> managerTable;

    @FXML
    private TableColumn<Member, String> nameC;

    @FXML
    private TableColumn<Member, String> nameM;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCol();
        loadTodayShipmentData();
        loadCustomerData();
        loadManagerData();

    }

    private void initCol() {
        idM.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameM.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactM.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailM.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressM.setCellValueFactory(new PropertyValueFactory<>("address"));

        idC.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameC.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactC.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailC.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressC.setCellValueFactory(new PropertyValueFactory<>("address"));

        todayName.setCellValueFactory(new PropertyValueFactory<>("name"));
        todayDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        todayWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        todayType.setCellValueFactory(new PropertyValueFactory<>("shipmentType"));
        todaySender.setCellValueFactory(new PropertyValueFactory<>("sender"));
        todayReciever.setCellValueFactory(new PropertyValueFactory<>("receiver"));
        todayOutorIn.setCellValueFactory(new PropertyValueFactory<>("outOrin"));
        todayContainerType.setCellValueFactory(new PropertyValueFactory<>("containerType"));

    }

    private void loadCustomerData() {
        String qu = "SELECT * FROM ACCOUNT WHERE isCustomer = 1" ;
        ResultSet rs = DBhandler.executeQuery(qu);
        System.out.println(rs);
        try {
            while (rs.next()) {
                String id = rs.getString("id");
                String qu2 = "SELECT * FROM MEMBER WHERE id = '" + id + "'";
                ResultSet rs2 = DBhandler.executeQuery(qu2) ;
                try {
                    while (rs2.next()) {
                        String Name = rs2.getString("name");
                        String Contact = rs2.getString("phonenumber") ;
                        String Email = rs2.getString("email");
                        String Address = rs2.getString("address");
                        Member newMember = new Member() ;
                        newMember.setName(Name);
                        newMember.setPhoneNumber(Contact);
                        newMember.setEmail(Email);
                        newMember.setAddress(Address);
                        newMember.setId(id);
                        customers.add(newMember) ;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customerTable.getItems().setAll(customers);
    }

    private void loadManagerData() {
        String qu = "SELECT * FROM ACCOUNT WHERE isManager = 1" ;
        ResultSet rs = DBhandler.executeQuery(qu);
        System.out.println(rs);
        try {
            while (rs.next()) {
                String id = rs.getString("id");
                String qu2 = "SELECT * FROM MEMBER WHERE id = '" + id + "'" ;
                ResultSet rs2 = DBhandler.executeQuery(qu2) ;
                try {
                    while (rs2.next()) {
                        String Name = rs2.getString("name");
                        String Contact = rs2.getString("phonenumber") ;
                        String Email = rs2.getString("email");
                        String Address = rs2.getString("address");
                        Member newMember = new Member() ;
                        newMember.setName(Name);
                        newMember.setPhoneNumber(Contact);
                        newMember.setEmail(Email);
                        newMember.setAddress(Address);
                        newMember.setId(id);
                        managers.add(newMember) ;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        managerTable.getItems().setAll(managers);
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
    void deleteCustomer(ActionEvent event) {

    }

    @FXML
    void deleteManager(ActionEvent event) {

    }

    @FXML
    void loadAddMember(ActionEvent event) {

    }

    @FXML
    void loadApproveShipment(ActionEvent event) {

    }

    @FXML
    void loadScheduleShipment(ActionEvent event) {

    }

    @FXML
    void loadViewShipments(ActionEvent event) {

    }

    @FXML
    void logOut(ActionEvent event) {

    }

    @FXML
    void updateCustomer(ActionEvent event) {

    }

    @FXML
    void updateManager(ActionEvent event) {

    }

    @FXML
    void viewCustomerDetails(ActionEvent event) {

    }

    @FXML
    void viewManagerDetails(ActionEvent event) {

    }


}
