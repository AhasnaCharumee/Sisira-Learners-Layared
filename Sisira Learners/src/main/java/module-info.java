module lk.ijse.gdse72.sisiralearners {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.sql;
    requires static lombok;


    opens lk.ijse.gdse72.sisiralearners to javafx.fxml;
    opens lk.ijse.gdse72.sisiralearners.dto to javafx.base;
    opens lk.ijse.gdse72.sisiralearners.dto.tm to javafx.base;
    exports lk.ijse.gdse72.sisiralearners;
    exports lk.ijse.gdse72.sisiralearners.controller;
    opens lk.ijse.gdse72.sisiralearners.controller to javafx.fxml;
}