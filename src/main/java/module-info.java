module com.example.majorproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.majorproject to javafx.fxml;
    exports com.example.majorproject;
}