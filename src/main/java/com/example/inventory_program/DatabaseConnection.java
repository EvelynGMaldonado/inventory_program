package com.example.inventory_program;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Evelyn G Morrow.
 * @version 1.0.
 * Public class DatabaseConnection handles the creation of a new connection for each interaction that the EM Inventory Management System needs to have with the database.
 * RUNTIME ERROR:
 * FUTURE ENHANCEMENT:
 */
public class DatabaseConnection {
    /**
     * public Connection databaseLink is an interface Connection that extends java.sql.Wrapper, AutoCloseable.
     * It generates a connection (session) with a specific database, SQL statements are executed and results are returned within the context of a connection.
     * A Connection object's database is able to provide information describing its tables, its supported SQL grammar, its stored procedures, the capabilities of this connection, and so on; This information is obtained with the getMetaData method.
     */
    public Connection databaseLink;

    /**
     * @return a Connection object called databaseLink.
     */
    public Connection getConnection() {
        //database parameters
        String databaseName = "em_inventorymanagementsystem";
        String databaseUser = "root";
        String databasePassword = "1234567";
        String url = "jdbc:mysql://localhost/" + databaseName;
        //String url = "jdbc:mysql://127.0.0.1:3306/" + databaseName;

        try {
            /**
             * Class.forName() method to load and register drivers; it dynamically loads the driver file into the memory.
             */
            Class.forName("com.mysql.cj.jdbc.Driver");

            /**
             * getConnection() method of DriverManager class is used to establish connection with the database.
             */
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
            //databaseLink = DriverManager.getConnection("jdbc:mysql://localhost/em_inventorymanagementsystem?" + "user=root&password=1234567");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }
}
