module com.example.project3gym {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project3gym to javafx.fxml;
    exports com.example.project3gym;
}