module com.example.inventory_program {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.inventory_program to javafx.fxml;
    exports com.example.inventory_program;
}