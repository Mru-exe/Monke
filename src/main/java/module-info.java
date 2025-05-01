module monke {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;
    requires java.management;

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