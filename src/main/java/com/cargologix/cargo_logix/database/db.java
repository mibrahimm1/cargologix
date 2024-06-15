package com.cargologix.cargo_logix.database;
import java.sql.*;

public class db {
    private static db handler = null;
    private static final String DB_URL = "jdbc:mysql://localhost/cargo_logix";
    private static final String DB_USER = "root"; // MySQL username
    private static final String DB_PASSWORD = ""; // MySQL password
    public static Connection connection = null;
    private static Statement stmnt = null;

    private db() throws ClassNotFoundException { // PRIVATE SO THAT NO OTHER CLASS CAN CREATE A NEW INSTANCE OF DATABASE
        createConnection();
        setupAccountTable();
        setupCustomerTable();
        setupManagerTable();
        setupShipmentTable();
        setupMemberTable();
        setupScheduleShipmentTable();

    }


    public static db getInstance() // METHOD TO BE USED BY CLASSES TO RETRIEVE INSTANCE OF DATABASE
    {
        if (handler == null) {
            try {
                handler = new db();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return handler;
    }

    void createConnection() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setupCustomerTable() {
        String TABLE_NAME = "CUSTOMER";
        try {
            stmnt = connection.createStatement();

            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists.");
            } else {
                stmnt.execute("CREATE TABLE " + TABLE_NAME + " ("
                        + "    id varchar(200) primary key,\n"
                        + "    noOfReqs integer default 0, \n"
                        + "    FOREIGN KEY (id) REFERENCES MEMBER(id)"
                        + " )");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " . . . setupCustomerTable");
        }
    }

    void setupManagerTable() {
        String TABLE_NAME = "MANAGER";
        try {
            stmnt = connection.createStatement();

            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists.");
            } else {
                stmnt.execute("CREATE TABLE " + TABLE_NAME + " ("
                        + "    id varchar(200) primary key,\n"
                        + "    noOfReqsA integer default 0,\n"
                        + "    noOfReqsR integer default 0, \n"
                        + "    FOREIGN KEY (id) REFERENCES MEMBER(id)"
                        + " )");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " . . . setupManagerTable");
        }
    }

    void setupMemberTable() {
        String TABLE_NAME = "MEMBER";
        try {
            stmnt = connection.createStatement();

            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            // Print all table names to verify what's in the database
            System.out.println("Tables in the database:");
            while (tables.next()) {
                System.out.println(tables.getString("TABLE_NAME"));
            }

            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists.");
            } else {
                stmnt.execute("CREATE TABLE " + TABLE_NAME + " ("
                        + "    id varchar(200) primary key,\n"
                        + "    name varchar(200),\n"
                        + "    phonenumber varchar(200),\n"
                        + "    email varchar(100),\n"
                        + "    address varchar(200)"
                        + " )");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " . . . setupMemberTable");
        }
    }


    void setupAccountTable() {
        String TABLE_NAME = "ACCOUNT";
        try {
            stmnt = connection.createStatement();

            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            // Print all table names to verify what's in the database
            System.out.println("Tables in the database:");
            while (tables.next()) {
                System.out.println(tables.getString("TABLE_NAME"));
            }

            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists.");
            } else {
                stmnt.execute("CREATE TABLE " + TABLE_NAME + " ("
                        + "    username varchar(200) primary key,\n"
                        + "    password varchar(200),\n"
                        + "    isAdmin boolean default 0,\n"
                        + "    isManager boolean default 0,\n"
                        + "    isCustomer boolean default 0,\n"
                        + "    id varchar(200),\n"
                        + "    UNIQUE (id)"
                        + " )");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " . . . setupAccountTable");
        }
    }

    void setupShipmentTable() {
        String TABLE_NAME = "SHIPMENT";
        try {
            stmnt = connection.createStatement();

            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists.");
            } else {
                stmnt.execute("CREATE TABLE " + TABLE_NAME + " ("
                        + "    id int(11) primary key AUTO_INCREMENT,\n"
                        + "    name varchar(200),\n"
                        + "    sender varchar(200),\n"
                        + "    receiver varchar(200),\n"
                        + "    destination varchar(200),\n"
                        + "    weight integer default 0,\n"
                        + "    isOutgoing boolean ,\n"
                        + "    type varchar(200),\n"
                        + "    isFragile boolean ,\n"
                        + "    isTempControl boolean, \n"
                        + "    isScheduled boolean default 0"
                        + " )");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " . . . setupShipmentTable");
        }
    }

    void setupScheduleShipmentTable() {
        String TABLE_NAME = "SCHEDULED_SHIPMENT";
        try {
            stmnt = connection.createStatement();

            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists.");
            } else {
                stmnt.execute("CREATE TABLE " + TABLE_NAME + " ("
                        + "    id int(11) primary key ,\n"
                        + "    containerType varchar(200) ,\n"
                        + "    shipmentTime timestamp ,\n"
                        + "    isApproved boolean ,\n"
                        + "    FOREIGN KEY (id) REFERENCES SHIPMENT(id) "
                        + " )");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " . . . setupShipmentTable");
        }
    }

    public ResultSet executeQuery(String query) {
        ResultSet result;
        try {
            stmnt = connection.createStatement();
            result = stmnt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        }
        return result;
    }

    public boolean executeAction(String q) {
        try {
            stmnt = connection.createStatement();
            stmnt.execute(q);
            return true;
        } catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        }
    }

    public static void main(String args[]) {
        try {
            db newdb = new db() ;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}


