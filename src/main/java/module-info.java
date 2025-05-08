module monke {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;
    requires java.management;
    requires com.google.gson;

    opens monke.models to com.google.gson;
    opens monke.utils to com.google.gson;
    opens monke.models.entities to com.google.gson;
    opens monke.models.base to com.google.gson;
    opens monke.models.common to com.google.gson;

    exports monke;
    exports monke.enums;
    exports monke.controllers;
    exports monke.views;
    exports monke.models;
    exports monke.models.base;
    exports monke.models.common;
    exports monke.models.entities;
    exports monke.utils;
}