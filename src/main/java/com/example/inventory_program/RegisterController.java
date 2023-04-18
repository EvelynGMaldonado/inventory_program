package com.example.inventory_program;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
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
    private Button signUp_cancelBtn;

    @FXML
    private Button signUp_closeBtn;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void clickSaveUserBtn(ActionEvent event) throws IOException {

        //Not null accepted Input validation checks that none of the fields are blank or empty...
        if(!setfullnameField.getText().trim().isEmpty() || !setroleField.getText().trim().isEmpty() || !setusernameField.getText().trim().isEmpty() || !setpasswordField.getText().trim().isEmpty() || !confirmpasswordField.getText().trim().isEmpty()) {

            //check if the desired username is available or if it already exists using the validateUsername method
//            validateUsername();

            //check if the set password field matches with the confirm-password field...
            if(setpasswordField.getText().trim().equals(confirmpasswordField.getText().trim())) {
                registerUser();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Password does not match. Please try again.");
                alert.showAndWait();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields.");
            alert.showAndWait();
        }

    }

//    public void validateUsername(){
//        DatabaseConnection connectNow = new DatabaseConnection();
//        Connection connectDB = connectNow.getConnection();
//        String verifyUsername = "SELECT count(1) FROM users WHERE username = '" + setusernameField.getText() + "'";
//
//        try {
//            Statement statement = connectDB.createStatement();
//            ResultSet queryResult = statement.executeQuery(verifyUsername);
//
//            while(queryResult.next()) {
//                if(queryResult.getInt(1) == 1) {
//                    //check if the set password field matches with the confirm-password field using the validateNewPassword method
//                    if(setpasswordField.getText().trim().equals(confirmpasswordField.getText().trim())) {
//                        registerUser();
//
//                    } else {
//                        Alert alert = new Alert(Alert.AlertType.ERROR);
//                        alert.setTitle("Error message");
//                        alert.setHeaderText(null);
//                        alert.setContentText("Password does not match. Please try again.");
//                        alert.showAndWait();
//                    }
//                } else {
////                    messageLabel.setText("Invalid Login. Please try again.");
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Error message");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Username already exists. Please try again.");
//                    alert.showAndWait();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            e.getCause();
//        }
//    }

    public void registerUser() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String fullName = setfullnameField.getText();
        String roleTitle = setroleField.getText();
        String username = setusernameField.getText();
        String password = setpasswordField.getText();

//        String validateUniqueUsername = "SELECT username FROM users";
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

            signupPartRedirectsToEMIMSHomePage();


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void signupPartRedirectsToEMIMSHomePage() throws IOException {
        saveUserBtn.getScene().getWindow().hide();

        //create new stage
        Stage landingPageWindow = new Stage();
        landingPageWindow.setTitle("Add Part - EM Inventory Management System");

        //create view for FXML
        FXMLLoader landingPageLoader = new FXMLLoader(getClass().getResource("landing_page.fxml"));

        //set view in ppMainWindow
        landingPageWindow.setScene(new Scene(landingPageLoader.load(), 600, 400));

        //launch
        landingPageWindow.show();
    }

    public void signUp_cancelBtnAction(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("New profile hasn't been saved yet. Are you sure that you want to cancel?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)) {
//              go back to the landing page by doing ...
                signUp_cancelBtn.getScene().getWindow().hide();
                //create new stage
                Stage landingPageWindow = new Stage();
                landingPageWindow.setTitle("Add Part - EM Inventory Management System");

                //create view for FXML
                FXMLLoader landingPageLoader = new FXMLLoader(getClass().getResource("landing_page.fxml"));

                //set view in ppMainWindow
                landingPageWindow.setScene(new Scene(landingPageLoader.load(), 600, 400));

                //launch
                landingPageWindow.show();
            } else {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}

