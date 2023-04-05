package com.example.inventory_program;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private Button signUpBtn;

    @FXML
    private StackPane addProfile_page;

    @FXML
    private PasswordField confirmpasswordField;

    @FXML
    private TextField setfullnameField;

    @FXML
    private PasswordField setpasswordField;

    @FXML
    private TextField setroleField;

    @FXML
    private TextField setusernameField;

    @FXML
    private Button saveUserBtn;

    @FXML
    private Button close;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void clickSaveUserBtn(ActionEvent event) throws IOException {
//        if(setusernameField.getText()) {
//
//        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error message");
//            alert.setHeaderText(null);
//            alert.setContentText("Username already exists. Please try again.");
//            alert.showAndWait();
//        }
        if(setpasswordField.getText().equals(confirmpasswordField.getText())) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Successful Registration");
//            alert.setHeaderText(null);
//            alert.setContentText("New user has been registered in EM Inventory Management System!");
//            alert.showAndWait();

            registerUser();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Password does not match. Please try again.");
            alert.showAndWait();
        }
//        try {
//            signUpBtn.getScene().getWindow().hide();
//            //create new stage
//            Stage addProfileWindow = new Stage();
//            addProfileWindow.setTitle("Parts and Products - EM Inventory Management System");
//
//            //create view for FXML
//            FXMLLoader addProfileLoader = new FXMLLoader(getClass().getResource("signUp.fxml"));
//
//            //set view in ppMainWindow
//            addProfileWindow.setScene(new Scene(addProfileLoader.load(), 500, 400));
//
//            //launch
//            addProfileWindow.show();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            e.getCause();
//        }
    }

    public void registerUser() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String fullName = setfullnameField.getText();
        String roleTitle = setroleField.getText();
        String username = setusernameField.getText();
        String password = setpasswordField.getText();

        String insertNewUserFields = "INSERT INTO users(full_name, role_title, username, u_password) VALUES ('";
        String insertNewUserValues = fullName + "', '" + roleTitle + "', '" + username + "', '" + password + "');";
        String insertNewUserFieldsToDB = insertNewUserFields + insertNewUserValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertNewUserFieldsToDB);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful Registration");
            alert.setHeaderText(null);
            alert.setContentText("New user has been registered in EM Inventory Management System!");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
