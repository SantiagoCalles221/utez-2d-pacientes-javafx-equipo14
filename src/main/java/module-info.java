module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.demo to javafx.fxml;

    opens com.example.demo.controller to javafx.fxml;

    opens com.example.demo.Repository to javafx.fxml, javafx.base;

    opens com.example.demo.Service to javafx.fxml;

    exports com.example.demo;
    opens com.example.demo.Array to javafx.base, javafx.fxml;
}