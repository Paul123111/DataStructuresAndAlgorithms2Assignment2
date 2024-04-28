module com.example.assignment2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jmh.core;


    opens com.example.assignment2 to javafx.fxml;
    exports com.example.assignment2;
    exports com.example.assignment2.controllers;
    opens com.example.assignment2.controllers to javafx.fxml;
    exports com.example.assignment2.utils;
    opens com.example.assignment2.utils to javafx.fxml;
}