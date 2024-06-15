package com.cargologix.cargo_logix.classes;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Shipment {
    private SimpleStringProperty id ;
    private SimpleStringProperty name ;
    private SimpleStringProperty sender ;
    private SimpleStringProperty receiver;
    private SimpleStringProperty destination ;
    private SimpleFloatProperty weight ;
    private SimpleStringProperty shipmentType ;
    private SimpleStringProperty containerType ;
    private SimpleStringProperty scheduledTime ;
    private SimpleBooleanProperty isOut ;
    private SimpleBooleanProperty fragile ;
    private SimpleBooleanProperty tempControl ;
    private SimpleBooleanProperty scheduled ;
    private SimpleBooleanProperty approved ;

    public Shipment(String id, String name, String sender, String receiver, String destination, float weight,
                    String shipmentType, String containerType, Timestamp scheduledTime, boolean isOut, boolean fragile,
                    boolean tempControl) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.sender = new SimpleStringProperty(sender);
        this.receiver = new SimpleStringProperty(receiver);
        this.destination = new SimpleStringProperty(destination);
        this.weight = new SimpleFloatProperty(weight);
        this.shipmentType = new SimpleStringProperty(shipmentType);
        this.containerType = new SimpleStringProperty(containerType);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.scheduledTime = new SimpleStringProperty(dateFormat.format(scheduledTime));
        this.isOut = new SimpleBooleanProperty(isOut);
        this.fragile = new SimpleBooleanProperty(fragile);
        this.tempControl = new SimpleBooleanProperty(tempControl);
        this.scheduled = new SimpleBooleanProperty(false);
        this.approved = new SimpleBooleanProperty(false);
    }

    public Shipment() {

    }
    public Shipment(String id, String destination, String weight,
                    String shipmentType) {
        this.id = new SimpleStringProperty(id);
        this.destination = new SimpleStringProperty(destination);
        this.weight = new SimpleFloatProperty(Float.parseFloat(weight));
        this.shipmentType = new SimpleStringProperty(shipmentType);
    }

    /* public Shipment(String id, String destination, String containertype,
                    String shipmentTime) {
        this.id = new SimpleStringProperty(id);
        this.destination = new SimpleStringProperty(destination);
        this.containerType = new SimpleStringProperty(containertype);
        this.scheduledTime = new SimpleStringProperty(shipmentTime);
    } */



    public Shipment(String id, String name, String sender, String receiver, String destination, float weight,
                    String shipmentType, boolean isOut, boolean fragile,
                    boolean tempControl) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.sender = new SimpleStringProperty(sender);
        this.receiver = new SimpleStringProperty(receiver);
        this.destination = new SimpleStringProperty(destination);
        this.weight = new SimpleFloatProperty(weight);
        this.shipmentType = new SimpleStringProperty(shipmentType);
        this.containerType = new SimpleStringProperty("NULL");
        this.scheduledTime = new SimpleStringProperty("NULL");
        this.isOut = new SimpleBooleanProperty(isOut);
        this.fragile = new SimpleBooleanProperty(fragile);
        this.tempControl = new SimpleBooleanProperty(tempControl);
        this.scheduled = new SimpleBooleanProperty(false);
        this.approved = new SimpleBooleanProperty(false);
    }

    public Shipment(String id, String name, String sender, String receiver, String destination, float weight,
                    String shipmentType, String containerType, Timestamp scheduledTime, boolean isOut, boolean fragile,
                    boolean tempControl, boolean scheduled) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.sender = new SimpleStringProperty(sender);
        this.receiver = new SimpleStringProperty(receiver);
        this.destination = new SimpleStringProperty(destination);
        this.weight = new SimpleFloatProperty(weight);
        this.shipmentType = new SimpleStringProperty(shipmentType);
        this.containerType = new SimpleStringProperty(containerType);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.scheduledTime = new SimpleStringProperty(dateFormat.format(scheduledTime));
        this.isOut = new SimpleBooleanProperty(isOut);
        this.fragile = new SimpleBooleanProperty(fragile);
        this.tempControl = new SimpleBooleanProperty(tempControl);
        this.scheduled = new SimpleBooleanProperty(scheduled);
        this.approved = new SimpleBooleanProperty(false);
    }

    public Shipment(String id, String name, String sender, String receiver, String destination, float weight,
                    String shipmentType, String containerType, Timestamp scheduledTime, boolean isOut, boolean fragile,
                    boolean tempControl, boolean scheduled, boolean approved) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.sender = new SimpleStringProperty(sender);
        this.receiver = new SimpleStringProperty(receiver);
        this.destination = new SimpleStringProperty(destination);
        this.weight = new SimpleFloatProperty(weight);
        this.shipmentType = new SimpleStringProperty(shipmentType);
        this.containerType = new SimpleStringProperty(containerType);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.scheduledTime = new SimpleStringProperty(dateFormat.format(scheduledTime));
        this.isOut = new SimpleBooleanProperty(isOut);
        this.fragile = new SimpleBooleanProperty(fragile);
        this.tempControl = new SimpleBooleanProperty(tempControl);
        this.scheduled = new SimpleBooleanProperty(scheduled);
        this.approved = new SimpleBooleanProperty(approved);
    }

    public String getId() {
        return id.get();
    }


    public void setId(String id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }


    public void setName(String name) {
        this.name.set(name);
    }

    public String getSender() {
        return sender.get();
    }

    public void setSender(String sender) {
        this.sender.set(sender);
    }

    public String getReceiver() {
        return receiver.get();
    }


    public void setReceiver(String receiver) {
        this.receiver.set(receiver);
    }

    public String getDestination() {
        return destination.get();
    }


    public void setDestination(String destination) {
        this.destination.set(destination);
    }

    public float getWeight() {
        return weight.get();
    }

    public void setWeight(float weight) {
        this.weight.set(weight);
    }

    public String getShipmentType() {
        return shipmentType.get();
    }

    public void setShipmentType(String shipmentType) {
        this.shipmentType.set(shipmentType);
    }

    public String getContainerType() {
        return containerType.get();
    }

    public void setContainerType(String containerType) {
        this.containerType.set(containerType);
    }

    public String getScheduledTime() {
        return scheduledTime.get();
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime.set(scheduledTime);
    }

    public boolean isIsOut() {
        return isOut.get();
    }

    public void setIsOut(boolean isOut) {
        this.isOut.set(isOut);
    }

    public boolean isFragile() {
        return fragile.get();
    }

    public void setFragile(boolean fragile) {
        this.fragile.set(fragile);
    }

    public boolean isTempControl() {
        return tempControl.get();
    }

    public void setTempControl(boolean tempControl) {
        this.tempControl.set(tempControl);
    }

    public boolean isScheduled() {
        return scheduled.get();
    }

    public void setScheduled(boolean scheduled) {
        this.scheduled.set(scheduled);
    }

    public boolean isApproved() {
        return approved.get();
    }

    public void setApproved(boolean approved) {
        this.approved.set(approved);
    }
}
