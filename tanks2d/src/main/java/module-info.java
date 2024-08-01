module cvut.soumar.java.tanks2d {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens cvut.soumar.java.tanks2d to javafx.fxml;
    //exports cvut.soumar.java.tanks2d;
    exports cvut.soumar.java.tanks2d.data;
    opens cvut.soumar.java.tanks2d.data to javafx.fxml;
    exports cvut.soumar.java.tanks2d.controller;
    opens cvut.soumar.java.tanks2d.controller to javafx.fxml;
    exports cvut.soumar.java.tanks2d.model;
    opens cvut.soumar.java.tanks2d.model to javafx.fxml;
    exports cvut.soumar.java.tanks2d.utils;
    opens cvut.soumar.java.tanks2d.utils to javafx.fxml;
    exports cvut.soumar.java.tanks2d;
}