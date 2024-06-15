module com.cargologix.cargo_logix {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.cargologix.cargo_logix.classes;
    opens com.cargologix.cargo_logix to javafx.fxml;
    exports com.cargologix.cargo_logix;
    exports com.cargologix.cargo_logix.controllers;
    opens com.cargologix.cargo_logix.controllers to javafx.fxml;
}