package com.example.inventory_program;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection() {
//        String databaseName = "em_inventorymanagementsystem";
//        String databaseUser = "root";
//        String databasePassword = "password";
//        String url = "jdbc:mysql://127.0.0.1:3306/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
//            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
            databaseLink = DriverManager.getConnection("jdbc:mysql://localhost/em_inventorymanagementsystem?" +
                    "user=root&password=password");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }
}
