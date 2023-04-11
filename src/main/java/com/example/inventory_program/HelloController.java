package com.example.inventory_program;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private TableView<PartData> parts_tableView = new TableView<PartData>();

    @FXML
    private TableColumn<PartData, Integer> parts_tableView_col_inventoryLevel = new TableColumn<>("stock");

    @FXML
    private TableColumn<PartData, BigDecimal> parts_tableView_col_priceUnit = new TableColumn<>("price_unit");

    @FXML
    private TableColumn<PartData, Integer> parts_tableView_col_partID = new TableColumn<>("partID");

    @FXML
    private TableColumn<PartData, String> parts_tableView_col_partName = new TableColumn<>("part_name");

    @FXML
    private TableView<ProductData> products_tableView = new TableView<ProductData>();

    @FXML
    private TableColumn<ProductData, Integer> products_tableView_col_inventoryLevel = new TableColumn<>("stock");

    @FXML
    private TableColumn<ProductData, BigDecimal> products_tableView_col_priceUnit = new TableColumn<>("price_unit");

    @FXML
    private TableColumn<ProductData, Integer> products_tableView_col_productID = new TableColumn<>("productID");

    @FXML
    private TableColumn<ProductData, String> products_tableView_col_productName = new TableColumn<>("product_name");

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

    ObservableList<PartData> partList = FXCollections.observableArrayList();

    ObservableList<ProductData> productList = FXCollections.observableArrayList();

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
                    viewEMInventoryManagementSystem();
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

    public void viewEMInventoryManagementSystem() throws IOException {
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

    public void logOutBtnAction() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure that you wan to Log Out?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)) {
//                We can exit the plataform by doing...
//                Platform.exit();

//                or go back to the landing page by doing ...
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

    //creating the switch window method that might be implemented on the menu and other buttons
//    public void switchWindow(ActionEvent event) {
//        if(event.getSource() == addNewPart_btn || addPartPageBtn) {
//            addPart_page.setVisible(true);
//            addProduct_page.setVisible(false);
//            modifyPart_page.setVisible(false);
//            modifyProduct_page.setVisible(false);
//            landing_page.setVisible(false);
//            home_page.setVisible(false);
//
//            addPartPageBtn.setStyle("-fx-background-color: linear-gradient(to top, #196f9a, #1ba32d);
//                                     -fx-font-weight: bold;");
//
//        } else if(event.getSource() == addNewProduct_btn || addProductPageBtn) {
//            addPart_page.setVisible(false);
//            addProduct_page.setVisible(true);
//            modifyPart_page.setVisible(false);
//            modifyProduct_page.setVisible(false);
//            landing_page.setVisible(false);
//            home_page.setVisible(false);
//
//            addProductPageBtn.setStyle("-fx-background-color: linear-gradient(to top, #196f9a, #1ba32d);
//                                     -fx-font-weight: bold;");
//
//        } else if(event.getSource() == modifyPart_btn || modifyPartPageBtn) {
//            addPart_page.setVisible(false);
//            addProduct_page.setVisible(false);
//            modifyPart_page.setVisible(true);
//            modifyProduct_page.setVisible(false);
//            landing_page.setVisible(false);
//            home_page.setVisible(false);
//
//            modifyPartPageBtn.setStyle("-fx-background-color: linear-gradient(to top, #196f9a, #1ba32d);
//                                     -fx-font-weight: bold;");
//
//        } else if(event.getSource() == modifyProduct_btn || modifyProductPageBtn) {
//            addPart_page.setVisible(false);
//            addProduct_page.setVisible(false);
//            modifyPart_page.setVisible(false);
//            modifyProduct_page.setVisible(true);
//            landing_page.setVisible(false);
//            home_page.setVisible(false);
//
//            modifyProductPageBtn.setStyle("-fx-background-color: linear-gradient(to top, #196f9a, #1ba32d);
//                                     -fx-font-weight: bold;");
//        }
//    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        //SQL Query - executed in the backend database
        String partsViewQuery = "SELECT partID, part_name, stock, price_unit FROM parts";
        //**new
        String productsViewQuery = "SELECT productID, product_name, stock, price_unit FROM products";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryPartsOutput = statement.executeQuery(partsViewQuery);

            while (queryPartsOutput.next()) {

                //populate the observableList
                partList.add(new PartData(queryPartsOutput.getInt("partID"),
                                            queryPartsOutput.getString("part_name"),
                                            queryPartsOutput.getInt("stock"),
                                            queryPartsOutput.getBigDecimal("price_unit")));
            }


            //PropertyValueFactory corresponds to the new PartData fields
            //the table column is the one we annotate above
            parts_tableView_col_partID.setCellValueFactory(new PropertyValueFactory<>("partID"));
            parts_tableView_col_partName.setCellValueFactory(new PropertyValueFactory<>("part_name"));
            parts_tableView_col_inventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
            parts_tableView_col_priceUnit.setCellValueFactory(new PropertyValueFactory<>("price_unit"));

            parts_tableView.setItems(partList);

//            //Initial filtered list
//            FilteredList<PartData> filteredPartData = new FilteredList<>(partList, b -> true);
//            searchPart_inputField.textProperty().addListener((observable, oldValue, newValue) -> {
//                filteredPartData.setPredicate(partDataSearch -> {
//                    //if no search value, then display all records or whatever records it currently has. - no changes!
//                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null) {
//                        return true;
//                    }
//                    String searchPartByInput = newValue.toLowerCase();
//                    if(partDataSearch.getPart_name().toLowerCase().indexOf(searchPartByInput) > -1) {
//                        return true; //it means a part_name match has been found
//                    } else if(partDataSearch.getPartID().toString().indexOf(searchPartByInput) > -1) {
//                        return true; //it means a partID match has been found
//                    } else if (partDataSearch.getPrice_unit().toString().indexOf(searchPartByInput) > -1) {
//                        return true; //it means a price_unit match has been found
//                    } else {
//                        return false; //no matches have been found
//                    }
//                });
//            });
//            SortedList<PartData> sortedPartData = new SortedList<>(filteredPartData);
//            //Bind sorted result with Table view
//            sortedPartData.comparatorProperty().bind(parts_tableView.comparatorProperty());
//            //Apply filtered and sorted data to the Table View
//            parts_tableView.setItems(sortedPartData);

        } catch(SQLException e) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            e.getCause();
        }
        try {
            Statement statement = connectDB.createStatement();
            //**new
            ResultSet queryProductsOutput = statement.executeQuery(productsViewQuery);

            //**new
            while (queryProductsOutput.next()) {
                productList.add(new ProductData(queryProductsOutput.getInt("productID"),
                        queryProductsOutput.getString("product_name"),
                        queryProductsOutput.getInt("stock"),
                        queryProductsOutput.getBigDecimal("price_unit")));
            }
            //**new
            products_tableView_col_productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
            products_tableView_col_productName.setCellValueFactory(new PropertyValueFactory<>("product_name"));
            products_tableView_col_inventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
            products_tableView_col_priceUnit.setCellValueFactory(new PropertyValueFactory<>("price_unit"));

            products_tableView.setItems(productList);

        } catch (SQLException e) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            e.getCause();
        }
    }
}