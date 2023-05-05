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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Evelyn G Morrow.
 * @version 1.0.
 * Public class RegisterController is used to register a new user account into our EM Inventory Management Software.
 * RUNTIME ERROR:
 * FUTURE ENHANCEMENT:
 */
public class RegisterController implements Initializable {

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

    /**
     * Void clickSaveUserBtn() method is used to validate that none of the fields are empty, and that the password and confirm password matches.
     * e represents the event that triggers the action.
     * If both validations pass, then the registerUser() method will be called; otherwise an error alert will be displayed.
     */
    @FXML
    void clickSaveUserBtn(ActionEvent event) throws IOException {

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

    /**
     * Void signUp_cancelBtnAction() method is used to go back to the landing page when the user hasn't been saved in the database.
     * e represents the event that triggers the action.
     * A confirmation alert will be shown when the user clicks the cancel button. If the user clicks OK, then the signUp Page will be hidden, and the user will be redirected to the landing page, unless an exception is caught. If the user press cancel, then it will return to the signUp page to keep editing its profile.
     */
    @FXML
    void signUp_cancelBtnAction(ActionEvent event) {
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
                landingPageWindow.setTitle("EM Inventory Management System");

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

    /**
     * Public void registerUser() method is used to insert the new user account into the EM database.
     * e represents the event that triggers the action.
     *  Once the data is inserted, the signupPartRedirectsToEMIMSHomePage() method will be called.
     */
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

    /**
     * Public void signupPartRedirectsToEMIMSHomePage() method is called when the user registration is done.
     * The home page is displayed.
     * @exception IOException if an input or output error occurred.
     * @see IOException
     */
    public void signupPartRedirectsToEMIMSHomePage() throws IOException {
        saveUserBtn.getScene().getWindow().hide();

        //create new stage
        Stage landingPageWindow = new Stage();
        landingPageWindow.setTitle("EM Inventory Management System");

        //create view for FXML
        FXMLLoader landingPageLoader = new FXMLLoader(getClass().getResource("landing_page.fxml"));

        //set view in ppMainWindow
        landingPageWindow.setScene(new Scene(landingPageLoader.load(), 600, 400));

        //launch
        landingPageWindow.show();
    }

    /**
     * Public void initialize() method called to initialize a controller after its root element has been completely processed.
     * @parameter url is used to resolve relative paths for the root object. It is null if the url is not known.
     * @parameter rb is used to localize the root object, and it is null if the root object is not located.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
}

