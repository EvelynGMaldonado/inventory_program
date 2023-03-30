module com.example.inventory_program {
    requires javafx.controls;
    requires javafx.fxml;
    requires fontawesomefx;


    opens com.example.inventory_program to javafx.fxml;
    exports com.example.inventory_program;
}