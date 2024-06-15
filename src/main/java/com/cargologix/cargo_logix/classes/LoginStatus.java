package com.cargologix.cargo_logix.classes;

public class LoginStatus {
    private static boolean AdminLogged ;
    private static boolean ManagerLogged ;
    private static boolean CustomerLogged ;

    private static String ManagerID ;
    private static String CustomerID ;

    public static String getManagerID() {
        return ManagerID;
    }

    public static void setManagerID(String managerID) {
        ManagerID = managerID;
    }

    public static String getCustomerID() {
        return CustomerID;
    }

    public static void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public static boolean isAdminLogged() {
        return AdminLogged;
    }

    public static void setAdminLogged(boolean adminLogged) {
        AdminLogged = adminLogged;
    }

    public static boolean isManagerLogged() {
        return ManagerLogged;
    }

    public static void setManagerLogged(boolean managerLogged) {
        ManagerLogged = managerLogged;
    }

    public static boolean isCustomerLogged() {
        return CustomerLogged;
    }

    public static void setCustomerLogged(boolean customerLogged) {
        CustomerLogged = customerLogged;
    }
}
