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

public class Admin_Dashboard_Controller implements Initializable {

    ObservableList<Shipment> todaysShipments = FXCollections.observableArrayList() ;
    ObservableList<Member> managers = FXCollections.observableArrayList() ;

    ObservableList<Member> customers = FXCollections.observableArrayList() ;
    AlertMessege alert = new AlertMessege();

    db DBhandler = db.getInstance();
    @FXML
    private AnchorPane rootPane;
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
    private ListView<String> customerDetailList;

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
    private ListView<String> managerDetailList;

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

    @FXML
    private Tab viewCustomerSection;

    @FXML
    private Tab viewManagerSection;


    Boolean customerDetailsOn = false ;
    Boolean managerDetailsOn = false ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewCustomerSection.setOnSelectionChanged(event -> {
            if (viewCustomerSection.isSelected()) {
                loadCustomerData();
            }
        });

        viewManagerSection.setOnSelectionChanged(event -> {
            if (viewManagerSection.isSelected()) {
                loadManagerData();
            }
        });
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
        customerTable.getItems().clear();
        customers.clear();
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
        managerTable.getItems().clear();
        managers.clear();
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
        if(!customerDetailsOn) {
            alert.errorMessege("Error","Please enter in a Customer ID to view the details");
            return ;
        }
        Optional<ButtonType> response = alert.confirmMessege("Confirm","Do you really want to delete this customer?");
        if (response.isPresent() && response.get() == ButtonType.OK) {
            String ac1 = "DELETE FROM MEMBER WHERE id = '" + customerDetailsID.getText() + "'" ;
            String ac2 = "DELETE FROM ACCOUNT WHERE id = '" + customerDetailsID.getText() + "'" ;
            if (DBhandler.executeAction(ac1) && DBhandler.executeAction(ac2)) {
                customerDetailList.getItems().clear();
                editIDC.clear();
                editNameC.clear();
                editContactC.clear();
                enterEmailC.clear();
                enterAddressC.clear();
                customerDetailsID.clear();
                loadCustomerData();
                alert.infoMessege("Success","Customer Deleted Successfully!");
            } else {
                alert.errorMessege("Error","Failed to Delete Customer");
            }
        } else {
            alert.infoMessege("Cancelled","Deletion Cancelled");
        }

    }

    @FXML
    void deleteManager(ActionEvent event) {
        if(!customerDetailsOn) {
            alert.errorMessege("Error","Please enter in a Manager ID to view the details");
            return ;
        }
        Optional<ButtonType> response = alert.confirmMessege("Confirm","Do you really want to delete this customer?");
        if (response.isPresent() && response.get() == ButtonType.OK) {
            String ac1 = "DELETE FROM MEMBER WHERE id = '" + enterIDm.getText() + "'" ;
            String ac2 = "DELETE FROM ACCOUNT WHERE id = '" + enterIDm.getText() + "'" ;
            if (DBhandler.executeAction(ac1) && DBhandler.executeAction(ac2)) {
                managerDetailList.getItems().clear();
                editIDM.clear();
                editNameM.clear();
                editContactM.clear();
                enterEmailM.clear();
                enterAddressM.clear();
                enterIDm.clear();
                loadManagerData();
                alert.infoMessege("Success","Manager Deleted Successfully!");
            } else {
                alert.errorMessege("Error","Failed to Delete Manager");
            }
        } else {
            alert.infoMessege("Cancelled","Deletion Cancelled");
        }
    }

    @FXML
    void loadAddMember(ActionEvent event) {
        WindowLoader windowLoader = new WindowLoader();
        windowLoader.loadWindow("add_member.fxml", "Add New User");
    }

    @FXML
    void loadApproveShipment(ActionEvent event) {
        WindowLoader windowLoader = new WindowLoader();
        windowLoader.loadWindow("approve_shipment.fxml", "Add Shipment Request");

    }

    @FXML
    void loadScheduleShipment(ActionEvent event) {
        WindowLoader windowLoader = new WindowLoader();
        windowLoader.loadWindow("schedule_shipment.fxml", "Schedule Shipment Request");

    }

    @FXML
    void loadViewShipments(ActionEvent event) {
        WindowLoader windowLoader = new WindowLoader();
        windowLoader.loadWindow("view_shipment.fxml", "View Shipment");

    }

    @FXML
    void logOut(ActionEvent event) {
        Optional<ButtonType> response = alert.confirmMessege("Confirm Logout","Are you sure you want to logout?");
        if (response.isPresent() & response.get() == ButtonType.OK) {
            LoginStatus.setAdminLogged(false);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();
            WindowLoader windowLoader = new WindowLoader();
            windowLoader.loadWindow("login.fxml", "Cargo Logix");
        } else {
            return ;
        }

    }

    @FXML
    void updateCustomer(ActionEvent event) {
        if (!customerDetailsOn) {
            alert.errorMessege("Failed","Please enter a Customer ID to view the customer details");
            return ;
        }
        Optional<ButtonType> response = alert.confirmMessege("Confirm","Are you sure you want to update the customer details?");
        if (response.isPresent() && response.get() == ButtonType.OK) {
            String newId = editIDC.getText();
            String newName = editNameC.getText();
            String newContact = editContactC.getText();
            String newEmail = enterEmailC.getText();
            String newAddress = enterAddressC.getText();
            String oldID = customerDetailsID.getText();
            if (newId.isEmpty() || newName.isEmpty() || newContact.isEmpty() || newEmail.isEmpty() || newAddress.isEmpty()) {
                alert.errorMessege("Error","Please enter in all fields");
            }
            try {
                String ac1 = "UPDATE MEMBER SET id = '" + newId + "', name = '" + newName +
                        "', phonenumber = '" + newContact + "', email = '" +
                        newEmail + "', address = '" + newAddress + "' WHERE id = '" + oldID + "'" ;
                String ac2 = "UPDATE ACCOUNT SET id = '" + newId + "' WHERE id = '" + oldID + "'" ;
                if (DBhandler.executeAction(ac1) && DBhandler.executeAction(ac2)) {
                    customerDetailList.getItems().clear();
                    editIDC.clear();
                    editNameC.clear();
                    editContactC.clear();
                    enterEmailC.clear();
                    enterAddressC.clear();
                    customerDetailsID.clear();
                    loadCustomerData();
                    alert.infoMessege("Success","Customer Updated Successfully!");
                } else {
                    alert.errorMessege("Error","Failed to Update Customer.");
                }
            } catch (Exception e) {
                alert.errorMessege("Error","Failed to Update Customer.");
                throw new RuntimeException(e);
            }
        } else {
            alert.infoMessege("Cancelled","Update Cancelled");
        }
    }


    @FXML
    void updateManager(ActionEvent event) {
        if (!managerDetailsOn) {
            alert.errorMessege("Failed","Please enter a Customer ID to view the customer details");
            return ;
        }
        Optional<ButtonType> response = alert.confirmMessege("Confirm","Are you sure you want to update the customer details?");
        if (response.isPresent() && response.get() == ButtonType.OK) {
            String newId = editIDM.getText();
            String newName = editNameM.getText();
            String newContact = editContactM.getText();
            String newEmail = enterEmailM.getText();
            String newAddress = enterAddressM.getText();
            String oldID = enterIDm.getText();
            if (newId.isEmpty() || newName.isEmpty() || newContact.isEmpty() || newEmail.isEmpty() || newAddress.isEmpty()) {
                alert.errorMessege("Error","Please enter in all fields");
            }
            try {
                String ac1 = "UPDATE MEMBER SET id = '" + newId + "', name = '" + newName +
                        "', phonenumber = '" + newContact + "', email = '" +
                        newEmail + "', address = '" + newAddress + "' WHERE id = '" + oldID + "'" ;
                String ac2 = "UPDATE ACCOUNT SET id = '" + newId + "' WHERE id = '" + oldID + "'" ;
                if (DBhandler.executeAction(ac1) && DBhandler.executeAction(ac2)) {
                    managerDetailList.getItems().clear();
                    editIDM.clear();
                    editNameM.clear();
                    editContactM.clear();
                    enterEmailM.clear();
                    enterAddressM.clear();
                    enterIDm.clear();
                    loadManagerData();
                    alert.infoMessege("Success","Customer Updated Successfully!");
                } else {
                    alert.errorMessege("Error","Failed to Update Customer.");
                }
            } catch (Exception e) {
                alert.errorMessege("Error","Failed to Update Customer.");
                throw new RuntimeException(e);
            }
        } else {
            alert.infoMessege("Cancelled","Update Cancelled");
        }

    }

    @FXML
    void viewCustomerDetails(ActionEvent event) {
        customerDetailsOn = false ;
        ObservableList<String> customerData = FXCollections.observableArrayList() ;
        String customerID = customerDetailsID.getText();
        String qu = "SELECT * FROM ACCOUNT WHERE id = '" + customerID + "' AND isCustomer = 1" ;
        ResultSet rs = DBhandler.executeQuery(qu);
        try {
            while (rs.next()) {
                String qu2 = "SELECT * FROM MEMBER WHERE id = '" + customerID + "'";
                ResultSet rs2 = DBhandler.executeQuery(qu2);
                try {
                    while (rs2.next()) {
                        String Name = rs2.getString("name");
                        String Email = rs2.getString("email");
                        String Contact = rs2.getString("phonenumber");
                        String Address = rs2.getString("address");

                        customerData.add("Customer Information: ");
                        customerData.add("\tID: " + customerID);
                        customerData.add("\tName: " + Name);
                        customerData.add("\tContact: " + Contact);
                        customerData.add("\tEmail: " + Email);
                        customerData.add("\tAddress: " + Address);

                        editIDC.setText(customerID);
                        editNameC.setText(Name);
                        editContactC.setText(Contact);
                        enterEmailC.setText(Email);
                        enterAddressC.setText(Address);

                        customerDetailsOn = true;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customerDetailList.getItems().setAll(customerData);

    }

    @FXML
    void viewManagerDetails(ActionEvent event) {
        managerDetailsOn = false ;
        ObservableList<String> managerData = FXCollections.observableArrayList() ;
        String managerID = enterIDm.getText();
        String qu = "SELECT * FROM ACCOUNT WHERE id = '" + managerID + "' AND isManager = 1" ;
        ResultSet rs = DBhandler.executeQuery(qu);
        try {
            while (rs.next()) {
                String qu2 = "SELECT * FROM MEMBER WHERE id = '" + managerID + "'";
                ResultSet rs2 = DBhandler.executeQuery(qu2);
                try {
                    while (rs2.next()) {
                        String Name = rs2.getString("name");
                        String Email = rs2.getString("email");
                        String Contact = rs2.getString("phonenumber");
                        String Address = rs2.getString("address");

                        managerData.add("Manager Information: ");
                        managerData.add("\tID: " + managerID);
                        managerData.add("\tName: " + Name);
                        managerData.add("\tContact: " + Contact);
                        managerData.add("\tEmail: " + Email);
                        managerData.add("\tAddress: " + Address);

                        editIDM.setText(managerID);
                        editNameM.setText(Name);
                        editContactM.setText(Contact);
                        enterEmailM.setText(Email);
                        enterAddressM.setText(Address);

                        managerDetailsOn = true;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        managerDetailList.getItems().setAll(managerData);
    }


}
