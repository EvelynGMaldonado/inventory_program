package com.example.inventory_program;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController {
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

//    public void createAccountForm(ActionEvent event) throws IOException {
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
//    }
}
