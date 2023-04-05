package com.example.inventory_program;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
//public class HelloController {

    @FXML
    private AnchorPane landing_page;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button startBtn;

    @FXML
    private TextField usernameField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button signUpBtn;

    @FXML
    private StackPane addProfile_page;

    @FXML
    private PasswordField confirmpasswordField;

    @FXML
    private Button saveUserBtn;

    @FXML
    private TextField setfullnameField;

    @FXML
    private PasswordField setpasswordField;

    @FXML
    private TextField setroleField;

    @FXML
    private TextField setusernameField;

    @FXML
    private StackPane home_page;

    @FXML
    private Button LogOut_btn;

    @FXML
    private Button addNewPart_btn;

    @FXML
    private Button addNewProduct_btn;
    @FXML
    private Button deletePart_btn;

    @FXML
    private Button deleteProduct_btn;

    @FXML
    private Button modifyPart_btn;

    @FXML
    private Button modifyProduct_btn;

    @FXML
    private TableView<?> parts_tableView;

    @FXML
    private TableColumn<?, ?> parts_tableView_col_inventoryLevel;

    @FXML
    private TableColumn<?, ?> parts_tableView_col_priceUnit;

    @FXML
    private TableColumn<?, ?> parts_tableView_col_partID;

    @FXML
    private TableColumn<?, ?> parts_tableView_col_partName;

    @FXML
    private TableView<?> products_tableView;

    @FXML
    private TableColumn<?, ?> products_tableView_col_inventoryLevel;

    @FXML
    private TableColumn<?, ?> products_tableView_col_priceUnit;

    @FXML
    private TableColumn<?, ?> products_tableView_col_productID;

    @FXML
    private TableColumn<?, ?> products_tableView_col_productName;

    @FXML
    private Button searchPart_btn;

    @FXML
    private TextField searchPart_inputField;

    @FXML
    private Button searchProduct_btn;

    @FXML
    private TextField searchProduct_inputField;

    @FXML
    private Button addPartPageBtn;

    @FXML
    private Button addProductPageBtn;

    @FXML
    private Button modifyPartPageBtn;

    @FXML
    private Button modifyProductPageBtn;

    @FXML
    private Button settingsBtn;

    @FXML
    private StackPane addPart_page;

    @FXML
    private StackPane addProduct_page;

    @FXML
    private StackPane modifyPart_page;

    @FXML
    private StackPane modifyProduct_page;

    @FXML
    private Button close;

    //When we click the sign-up button that is in the landing page we are redirected to the addProfile_page
    public void createAccountForm(ActionEvent event) throws IOException {
        try {
            signUpBtn.getScene().getWindow().hide();
            //create new stage
            Stage addProfileWindow = new Stage();
            addProfileWindow.setTitle("Parts and Products - EM Inventory Management System");

            //create view for FXML
            FXMLLoader addProfileLoader = new FXMLLoader(getClass().getResource("signUp.fxml"));

            //set view in ppMainWindow
            addProfileWindow.setScene(new Scene(addProfileLoader.load(), 500, 400));

            //launch
            addProfileWindow.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void LoginButtonOnAction(ActionEvent e) {

        if(usernameField.getText().trim().isBlank() == false && passwordField.getText().trim().isBlank() == false) {
            validateLogin();
        } else {
//            messageLabel.setText("Please enter username and password");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields.");
            alert.showAndWait();
        }
    }

    public void validateLogin(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyLogin = "SELECT count(1) FROM users WHERE username = '" + usernameField.getText() + "' AND u_password = '" + passwordField.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                if(queryResult.getInt(1) == 1) {
//                    messageLabel.setText("Welcome to EM Inventory Management System!");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successful Log In");
                    alert.setHeaderText(null);
                    alert.setContentText("Welcome to EM Inventory Management System!");
                    alert.showAndWait();

                    startBtn.getScene().getWindow().hide();
//                    Platform.exit();
                    clickStartBtn();
                } else {
//                    messageLabel.setText("Invalid Login. Please try again.");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Login. Please try again.");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void clickStartBtn () throws IOException {
//        startBtn.getScene().getWindow().hide();
//        Stage stage1 = (Stage) startBtn.getScene().getWindow();
//        stage1.close();
        //create new stage
        Stage ppMainWindow = new Stage();
        ppMainWindow.setTitle("Parts and Products - EM Inventory Management System");

        //create view for FXML
        FXMLLoader ppMainLoader = new FXMLLoader(getClass().getResource("home_page-parts&products.fxml"));

        //set view in ppMainWindow
        ppMainWindow.setScene(new Scene(ppMainLoader.load(), 800, 400));

        //launch
        ppMainWindow.show();

    }

    public void clickAddPartPageBtn (ActionEvent event) throws IOException {
        addPartPageBtn.getScene().getWindow().hide();
        //create new stage
        Stage addPartPageWindow = new Stage();
        addPartPageWindow.setTitle("Add Part - EM Inventory Management System");

        //create view for FXML
        FXMLLoader addPartPageLoader = new FXMLLoader(getClass().getResource("addPart_page.fxml"));

        //set view in ppMainWindow
        addPartPageWindow.setScene(new Scene(addPartPageLoader.load(), 600, 400));

        //launch
        addPartPageWindow.show();

    }

    public void clickAddProductPageBtn (ActionEvent event) throws IOException {
        addProductPageBtn.getScene().getWindow().hide();
        //create new stage
        Stage addProductPageWindow = new Stage();
        addProductPageWindow.setTitle("Add Part - EM Inventory Management System");

        //create view for FXML
        FXMLLoader addProductPageLoader = new FXMLLoader(getClass().getResource("addProduct_page.fxml"));

        //set view in ppMainWindow
        addProductPageWindow.setScene(new Scene(addProductPageLoader.load(), 800, 610));

        //launch
        addProductPageWindow.show();

    }

    public void clickModifyProductPageBtn (ActionEvent event) throws IOException {
        modifyProductPageBtn.getScene().getWindow().hide();
        //create new stage
        Stage modifyProductPageWindow = new Stage();
        modifyProductPageWindow.setTitle("Add Part - EM Inventory Management System");

        //create view for FXML
        FXMLLoader modifyProductPageLoader = new FXMLLoader(getClass().getResource("modifyProduct_page.fxml"));

        //set view in ppMainWindow
        modifyProductPageWindow.setScene(new Scene(modifyProductPageLoader.load(), 800, 610));

        //launch
        modifyProductPageWindow.show();

    }

    public void clickModifyPartPageBtn (ActionEvent event) throws IOException {
        modifyPartPageBtn.getScene().getWindow().hide();
        //create new stage
        Stage modifyPartPageWindow = new Stage();
        modifyPartPageWindow.setTitle("Add Part - EM Inventory Management System");

        //create view for FXML
        FXMLLoader modifyPartPageLoader = new FXMLLoader(getClass().getResource("modifyPart_page.fxml"));

        //set view in ppMainWindow
        modifyPartPageWindow.setScene(new Scene(modifyPartPageLoader.load(), 600, 400));

        //launch
        modifyPartPageWindow.show();

    }

    public void closeBtnAction(ActionEvent e) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        borderpane.setCenter(GlyphsDude.createIcon(FontAwesomeIcon.DATABASE, "200px"));

    }

}