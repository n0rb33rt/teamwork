module com.norbert.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;

    opens com.norbert.frontend to javafx.fxml;
    exports com.norbert.frontend;
    exports com.norbert.frontend.entity;
    opens com.norbert.frontend.entity to javafx.fxml;
    exports com.norbert.frontend.controller;
    opens com.norbert.frontend.controller to javafx.fxml;
}