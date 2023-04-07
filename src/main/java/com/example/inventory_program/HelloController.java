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
import java.util.Optional;
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

//WHEN WE CLOCK TO THE SIGN-UP BUTTON THAT IS IN THE LANDING PAGE WE ARE REDIRECTED TO THE addProfile_page
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

// WHEN WE TRY TO LOG-IN - IF ALL FIELDS ARE FILLED THEN WE CALL THE validateLogin() METHOD, OTHERWISE WE SHOW AN ERROR MESSAGE ALERT
    public void LoginButtonOnAction(ActionEvent e) {

        if(usernameField.getText().trim().isBlank() == false && passwordField.getText().trim().isBlank() == false) {
            validateLogin();
        } else {
           //messageLabel.setText("Please enter username and password");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields.");
            alert.showAndWait();
        }
    }

//DURING THE LOGIN VALIDATION WE CONNECT TO THE DATABASE, AND LOOK FOR A MATCH BETWEEN THE USERNAME AND PASSWORD,
// IF THERE IS A MATCH WE CALL THE startEMInventoryManagementSystem() method that is going to redirect us to the home_page-parts&products,
//OTHERWISE IT WILL SHOW AN ERROR MESSAGE ALERT
    public void validateLogin(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyLogin = "SELECT count(1) FROM users WHERE username = '" + usernameField.getText() + "' AND u_password = '" + passwordField.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                if(queryResult.getInt(1) == 1) {
                    //messageLabel.setText("Welcome to EM Inventory Management System!");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successful Log In");
                    alert.setHeaderText(null);
                    alert.setContentText("Welcome to EM Inventory Management System!");
                    alert.showAndWait();

                    //we hide the landing_page before we display the home_page-parts&products page
                    startBtn.getScene().getWindow().hide();
                    //Platform.exit();
                    viewEMInventoryManagementSystem();
                } else {
                    //messageLabel.setText("Invalid Login. Please try again.");
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

//THE startEMInventoryManagementSystem() METHOD THAT IS GOING TO DISPLAY THE home_page-parts&products WHERE WE CAN HAVE ACCESS TO
//THE Dashboard WHICH GIVES US A GENERAL VIEW OF OUR INVENTORY ~ Parts AND Products TABLES
    public void viewEMInventoryManagementSystem() throws IOException {
        //startBtn.getScene().getWindow().hide();
        //Stage stage1 = (Stage) startBtn.getScene().getWindow();
        //stage1.close();
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


//NAVIGATION FUNCTIONALITY
// clickAddPartPageBtn() METHOD HIDES THE CURRENT WINDOW AND TAKES US TO THE addPart_page WHERE WE WILL BE ABLE TO ADD A NEW PART
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

// clickAddProductPageBtn() METHOD HIDES THE CURRENT WINDOW AND TAKES US TO THE addProduct_page WHERE WE WILL BE ABLE TO ADD A NEW PRODUCT
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

// clickModifyProductPageBtn() METHOD HIDES THE CURRENT WINDOW AND TAKES US TO THE modifyProduct_page WHERE WE WILL BE ABLE TO MODIFY A PRODUCT
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

// clickModifyPartPageBtn() METHOD HIDES THE CURRENT WINDOW AND TAKES US TO THE modifyPart_page WHERE WE WILL BE ABLE TO MODIFY A PART
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

// closeBtnAction() METHOD HIDES THE CURRENT WINDOW AND TAKES US TO THE home_page-parts&products WHERE WE CAN HAVE ACCESS TO
////THE Dashboard WHICH GIVES US A GENERAL VIEW OF OUR INVENTORY ~ Parts AND Products TABLES
    public void closeBtnAction(ActionEvent e) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

// logOutBtnAction() METHOD HIDES THE CURRENT WINDOW AND TAKES US TO THE landing_page WHERE WE WILL BE ABLE TO EITHER SIGN UP OR CREATE A NEW ACCOUNT
    public void logOutBtnAction() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure that you wan to Log Out?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)) {
                //We can exit the plataform by doing...
                //Platform.exit();

                //or go back to the landing page by doing ...
                LogOut_btn.getScene().getWindow().hide();
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



//    //creating the switch window method that might be implemented on the menu and other buttons
//    public void switchWindow(ActionEvent event) {
//        if(event.getSource() == addNewPart_btn || addPartPageBtn) {
//            addPart_page.setVisible(true);
//            addProduct_page.setVisible(false);
//            modifyPart_page.setVisible(false);
//            modifyProduct_page.setVisible(false);
//            landing_page.setVisible(false);
//            home_page.setVisible(false);
//
//            addPartPageBtn.setStyle("-fx-background-color: linear-gradient(to top, #196f9a, #1ba32d); -fx-font-weight: bold;");
//
//        } else if(event.getSource() == addNewProduct_btn || addProductPageBtn) {
//            addPart_page.setVisible(false);
//            addProduct_page.setVisible(true);
//            modifyPart_page.setVisible(false);
//            modifyProduct_page.setVisible(false);
//            landing_page.setVisible(false);
//            home_page.setVisible(false);
//
//            addProductPageBtn.setStyle("-fx-background-color: linear-gradient(to top, #196f9a, #1ba32d); -fx-font-weight: bold;");
//
//        } else if(event.getSource() == modifyPart_btn || modifyPartPageBtn) {
//            addPart_page.setVisible(false);
//            addProduct_page.setVisible(false);
//            modifyPart_page.setVisible(true);
//            modifyProduct_page.setVisible(false);
//            landing_page.setVisible(false);
//            home_page.setVisible(false);
//
//            modifyPartPageBtn.setStyle("-fx-background-color: linear-gradient(to top, #196f9a, #1ba32d); -fx-font-weight: bold;");
//
//        } else if(event.getSource() == modifyProduct_btn || modifyProductPageBtn) {
//            addPart_page.setVisible(false);
//            addProduct_page.setVisible(false);
//            modifyPart_page.setVisible(false);
//            modifyProduct_page.setVisible(true);
//            landing_page.setVisible(false);
//            home_page.setVisible(false);
//
//            modifyProductPageBtn.setStyle("-fx-background-color: linear-gradient(to top, #196f9a, #1ba32d); -fx-font-weight: bold;");
//        }
//    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        borderpane.setCenter(GlyphsDude.createIcon(FontAwesomeIcon.DATABASE, "200px"));

    }

}