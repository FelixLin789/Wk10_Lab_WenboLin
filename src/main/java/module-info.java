module com.example.wk10_lab_wenbolin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.wk10_lab_wenbolin to javafx.fxml;
    exports com.example.wk10_lab_wenbolin;
}