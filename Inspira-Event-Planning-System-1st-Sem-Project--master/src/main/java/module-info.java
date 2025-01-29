module edu.ijse.inspira1stsemesterproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;
    requires java.desktop;
    requires com.jfoenix;
    //requires java.mail;
    requires net.sf.jasperreports.core;
    requires javax.mail.api;
    requires xmlgraphics.commons;


    opens edu.ijse.inspira1stsemesterproject.controller to javafx.fxml;
    opens edu.ijse.inspira1stsemesterproject.view.tdm to javafx.base;
    exports edu.ijse.inspira1stsemesterproject;
}