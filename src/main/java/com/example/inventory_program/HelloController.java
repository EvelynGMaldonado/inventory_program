package com.example.inventory_program;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Evelyn G Morrow.
 * @version 1.0.
 * Public class HelloController is used to retrieve and display the most up-to-date data on the parts and products tables after the user successfully signs in, as well as to manage some functionality such as delete, search, etc.
 * RUNTIME ERROR:
 * FUTURE ENHANCEMENT:
 */
public class HelloController implements Initializable {
//public class HelloController {
    @FXML
    private PasswordField passwordField;

    @FXML
    private Button startBtn;

    @FXML
    private TextField usernameField;

    @FXML
    private Button signUpBtn;

    @FXML
    private Button LogOut_btn;

    @FXML
    private TextField homePage_searchPartInputField;

    @FXML
    private TextField homePage_searchProductInputField;

    @FXML
    private Button homePage_modifyPartBtn;

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
    private Button addPartPageBtn;

    @FXML
    private Button addProductPageBtn;

    @FXML
    private Button modifyPartPageBtn;

    @FXML
    private Button modifyProductPageBtn;

    @FXML
    private Button landingPage_closeBtn;

    /**
     * It is used to validate if a row from a table has been selected.
     */
    int index = -1;

    ObservableList<PartData> partList = FXCollections.observableArrayList();

    ObservableList<ProductData> productList = FXCollections.observableArrayList();

    PartsAndProductsInventory partsAndProductsInventory;

    /**
     * Void LoginButtonOnAction() method is used after the user clicks the login button.
     * e represents the event that triggers the action.
     * The username and the password inputs are validated.
     * validateLogin() method is called when validation is passed.
     * error alert is shown when validation is not passed.
     */
    @FXML
    void LoginButtonOnAction(ActionEvent e) {
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

    /**
     * Void closeBtnAction() method is used to close the landing page which will basically close the application.
     * e represents the event that triggers the action.
     */
    @FXML
    void closeBtnAction(ActionEvent e) {
        Stage stage = (Stage) landingPage_closeBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Void createAccountForm() method is used to display the addProfile page after the user clicks the sign-up button located on the landing page.
     * e represents the event that triggers the action.
     * The landing page hides, and the addProfile page is displayed, unless an Exception is caught.
     */
    @FXML
    void createAccountForm(ActionEvent event) {
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

    /**
     * Public void validateLogin() method is used to validate that the username and the password match with the information in our database, after the user clicks the login button on the landing page.
     * When username and password pass, the method for displaying the home page is called, unless an Exception is caught.
     * When username and password do not pass, an error alert will show up.
     */
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

    /**
     * Public void viewEMInventoryManagementSystem() method is called when the login validation passes.
     * The home page is displayed.
     * @exception IOException if an input or output error occurred.
     * @see IOException
     */
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

    /**
     * Void deleteSelectedPart() method is used to delete the records from the selected row on the parts table.
     * e represents the event that triggers the action.
     * A confirmation alert is displayed, if the user clicks ok then the part will be deleted, and the parts table will be updated. If the user clicks cancel, then the action is aborted.
     * An error alert is displayed when no row has been selected.
     */
    @FXML
    void deleteSelectedPart (ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        index = parts_tableView.getSelectionModel().getSelectedIndex();
//        parts_tableView.getItems().remove(selectedItem);

        if(index > -1) {
            PreparedStatement pst;
            PartData selectedItem = parts_tableView.getSelectionModel().getSelectedItem();

            String deleteSelectedPart = "DELETE FROM parts WHERE partID = ?";

            try {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure that you want to delete this Part from the EM Inventory Management System?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)) {
                    pst = connectDB.prepareStatement(deleteSelectedPart);
                    pst.setString(1, selectedItem.getPartID().toString());
                    pst.execute();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Deletion information");
                    alert.setHeaderText(null);
                    alert.setContentText("Part has been successfully removed from the EM Inventory Management System");
                    alert.showAndWait();

                    homePage_modifyPartBtn.getScene().getWindow().hide();
                    viewEMInventoryManagementSystem();
                } else {
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the data row that you want to delete.");
            alert.showAndWait();
        }
    }

    /**
     * Void deleteSelectedProduct() method is used to delete the product records from the selected row on the products table.
     * e represents the event that triggers the action.
     * A confirmation alert is displayed, if the user clicks ok then the product will be deleted, and the products table will be updated, unless an Exception is caught. If the user clicks cancel, then the action is aborted.
     * An error alert is displayed when no row has been selected.
     * An error alert is displayed when the selected product has an associated part.
     */
    @FXML
    void deleteSelectedProduct (ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        index = products_tableView.getSelectionModel().getSelectedIndex();
//        parts_tableView.getItems().remove(selectedItem);

        if(index > -1) {
            PreparedStatement pst;
            ProductData selectedItemProduct = products_tableView.getSelectionModel().getSelectedItem();

            String selectedProductID = selectedItemProduct.getProductID().toString();
            String selectedProductName = selectedItemProduct.getProduct_name();
            String verifyAssociatedParts = "SELECT * FROM products_associated_parts WHERE productID = '" + selectedProductID + "'";
            String getAssociatedRowsDataProduct = "";
            String getAssociatedRowsDataParts = "";

            try {
                Statement statement = connectDB.createStatement();
                ResultSet associatedRows = statement.executeQuery(verifyAssociatedParts);
                int countAssociatedRows;

                if(!associatedRows.next() || associatedRows == null) {
                    System.out.println("line 273 ---- " + selectedProductID + " get the associatedRows value: " + associatedRows.getRow());
                    System.out.println("the selected productID: " + selectedProductID + " does not have associated parts");

                    String deleteSelectedProduct = "DELETE FROM products WHERE productID = ?";
                    try {

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Are you sure that you want to delete " + selectedProductName + " from the EM Inventory Management System?");
                        Optional<ButtonType> option = alert.showAndWait();

                        if(option.get().equals(ButtonType.OK)) {
                            pst = connectDB.prepareStatement(deleteSelectedProduct);
                            pst.setString(1, selectedItemProduct.getProductID().toString());
                            pst.execute();

                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Deletion information");
                            alert.setHeaderText(null);
                            alert.setContentText("Product has been successfully removed from the EM Inventory Management System");
                            alert.showAndWait();

                            homePage_modifyPartBtn.getScene().getWindow().hide();
                            viewEMInventoryManagementSystem();
                        } else {
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        e.getCause();
                    }

                } else if (associatedRows.next() || associatedRows != null){

                    countAssociatedRows = associatedRows.getRow() +1;
                    System.out.println("line 303 ---- " + selectedProductID + " get the associatedRows value: " + associatedRows.getRow());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("The product " + selectedProductName + " with ID: " + selectedProductID + " can't be deleted because it has associated parts. Please remove the associated parts on the modify product section, and try again");
                    alert.showAndWait();

                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the data row that you want to delete.");
            alert.showAndWait();
        }
    }

    /**
     * Void clickModifyPartPageBtn() method is used to open Modify Part Page.
     * e represents the event that triggers the action.
     * An error alert is displayed when no row has been selected.
     * @exception IOException if an input or output error occurs.
     * @see IOException
     * @exception SQLException if a database error or other errors occur.
     * @see SQLException
     */
    @FXML
    void clickModifyPartPageBtn (ActionEvent event){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        index = parts_tableView.getSelectionModel().getSelectedIndex();
//        parts_tableView.getItems().remove(selectedItem);

        if(index > -1) {

            PartData selectedItem = parts_tableView.getSelectionModel().getSelectedItem();
            String getSinglePartID = "";
            String getSinglePartName = "";
            String getSinglePartStock = "";
            String getSinglePartPriceUnit = "";
            String getSinglePartMin = "";
            String getSinglePartMax = "";
            String getSinglePartMachineID = "";
            String getSinglePartCompanyName = "";

            String modifySelectedPart = "SELECT * FROM parts WHERE partID = '" + selectedItem.getPartID() + "'";
            try {
                Statement statement = connectDB.createStatement();
                ResultSet querySelectedPartResult = statement.executeQuery(modifySelectedPart);
               // System.out.println("Modify part is: "+querySelectedPartResult.);
                while(querySelectedPartResult.next()) {
//                    System.out.println(querySelectedPartResult.getString("partID"));
//                    System.out.println(querySelectedPartResult.getString("part_name"));
                    getSinglePartID = querySelectedPartResult.getString("partID");
                    getSinglePartName = querySelectedPartResult.getString("part_name");
                    System.out.println("getPartName is: " + getSinglePartName);
                    getSinglePartStock = querySelectedPartResult.getString("stock");
                    getSinglePartPriceUnit = querySelectedPartResult.getString("price_unit");
                    getSinglePartMin = querySelectedPartResult.getString("min");
                    getSinglePartMax = querySelectedPartResult.getString("max");
                    getSinglePartMachineID = querySelectedPartResult.getString("machineID");
                    System.out.println("getSinglePartMachineID is: " + getSinglePartMachineID);
                    getSinglePartCompanyName = querySelectedPartResult.getString("company_name");
                    System.out.println("getSinglePartCompanyName is: " + getSinglePartCompanyName);
                }
                modifyPartPageBtn.getScene().getWindow().hide();
                //create new stage
                Stage modifyPartPageWindow = new Stage();
                modifyPartPageWindow.setTitle("Modify Part - EM Inventory Management System");

                //create view for FXML
                FXMLLoader modifyPartPageLoader = new FXMLLoader(getClass().getResource("modifyPart_page.fxml"));

                //Get modifyPart_page Controller : ModifyPartController
                //ModifyPartController modifyPartController = modifyPartPageLoader.getController();

                //Pass any data we want, we can have multiple method calls here
                //modifyPartController.showSelectedPartDataInformation(getPartName);

                //***give it a try*****
                ModifyPartController modifyPartController = new ModifyPartController(partsAndProductsInventory, selectedItem, getSinglePartID, getSinglePartName, getSinglePartStock, getSinglePartPriceUnit, getSinglePartMin, getSinglePartMax, getSinglePartMachineID, getSinglePartCompanyName);
                modifyPartPageLoader.setController(modifyPartController);
//                modifyPartController.checkingIfInOrOutSourced(getSinglePartMachineID, getSinglePartCompanyName);

                //set view in ppMainWindow
                modifyPartPageWindow.setScene(new Scene(modifyPartPageLoader.load(), 600, 400));

                //launch
                modifyPartPageWindow.show();

            } catch (IOException e) {
                e.printStackTrace();
                e.getCause();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the data row that you want to modify.");
            alert.showAndWait();
        }
    }

    /**
     * Void clickModifyProductPageBtn() method is used to open Modify Product Page.
     * e represents the event that triggers the action.
     * An error alert is displayed when no row has been selected.
     * @exception IOException if an input or output error occurs.
     * @see IOException
     * @exception SQLException if a database error or other errors occur.
     * @see SQLException
     */
    @FXML
    void clickModifyProductPageBtn (ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        index = products_tableView.getSelectionModel().getSelectedIndex();
//        parts_tableView.getItems().remove(selectedItem);

        if(index > -1) {
            ProductData selectedItem = products_tableView.getSelectionModel().getSelectedItem();
            String getSingleProductID = "";
            String getSingleProductName = "";
            String getSingleProductStock = "";
            String getSingleProductPriceUnit = "";
            String getSingleProductMin = "";
            String getSingleProductMax = "";

            String modifySelectedProduct = "SELECT * FROM products WHERE productID = '" + selectedItem.getProductID() + "'";
            try {
                Statement statement = connectDB.createStatement();
                ResultSet querySelectedProductResult = statement.executeQuery(modifySelectedProduct);
                // System.out.println("Modify part is: "+querySelectedPartResult.);
                while(querySelectedProductResult.next()) {
//                    System.out.println(querySelectedPartResult.getString("partID"));
//                    System.out.println(querySelectedPartResult.getString("part_name"));
                    getSingleProductID = querySelectedProductResult.getString("productID");
                    getSingleProductName = querySelectedProductResult.getString("product_name");
                    System.out.println("getProductName is: " + getSingleProductName);
                    getSingleProductStock = querySelectedProductResult.getString("stock");
                    getSingleProductPriceUnit = querySelectedProductResult.getString("price_unit");
                    getSingleProductMin = querySelectedProductResult.getString("min");
                    getSingleProductMax = querySelectedProductResult.getString("max");
                }
                modifyProductPageBtn.getScene().getWindow().hide();
                //create new stage
                Stage modifyProductPageWindow = new Stage();
                modifyProductPageWindow.setTitle("Modify Product - EM Inventory Management System");

                //create view for FXML
                FXMLLoader modifyProductPageLoader = new FXMLLoader(getClass().getResource("modifyProduct_page.fxml"));

                ModifyProductController modifyProductController = new ModifyProductController(partsAndProductsInventory, selectedItem, getSingleProductID, getSingleProductName, getSingleProductStock, getSingleProductPriceUnit, getSingleProductMin, getSingleProductMax);
                modifyProductPageLoader.setController(modifyProductController);

                //set view in ppMainWindow
                modifyProductPageWindow.setScene(new Scene(modifyProductPageLoader.load(), 800, 610));

                //launch
                modifyProductPageWindow.show();

            } catch (IOException e) {
                e.printStackTrace();
                e.getCause();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the data row that you want to modify.");
            alert.showAndWait();
        }
    }

    /**
     * Void clickAddPartPageBtn() method is used to open Add Part Page.
     * e represents the event that triggers the action.
     * @exception IOException if an input or output error occurs.
     * @see IOException
     */
    @FXML
    void clickAddPartPageBtn (ActionEvent event) throws IOException {
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

    /**
     * Void clickAddProductPageBtn() method is used to open Add Product Page.
     * e represents the event that triggers the action.
     * @exception IOException if an input or output error occurs.
     * @see IOException
     */
    @FXML
    void clickAddProductPageBtn (ActionEvent event) throws IOException {
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

    /**
     * Void logOutBtnAction() method is used to log-out from the EM Inventory Management System Application.
     * The homepage is closed and the user is redirected to the landing page which has the option for sign-in or sign-up.
     */
    @FXML
    void logOutBtnAction() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure that you want to Log Out?");
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

    /**
     * Void btnSearchPart() method is used to find a part row by typing information in the input field and clicking the search button.
     * @param event represents the event that triggers the action.
     * @exception SQLException if a database error or other errors occur.
     * @see SQLException
     */
    @FXML
    void btnSearchPart(MouseEvent event) {
        String text = homePage_searchPartInputField.getText();
        System.out.println(text);

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        //SQL Query - executed in the backend database
        String partsViewQuery = "SELECT partID, part_name, stock, price_unit FROM parts";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryPartsOutput = statement.executeQuery(partsViewQuery);
            //populate the observableList
            String name = "";
            String partID = "";
            String inv = "";
            String price = "";
            BigDecimal num;

            parts_tableView.getItems().clear();
            if (queryPartsOutput.next()) {

                //populate the observableList
                name = queryPartsOutput.getString("part_name");
                partID = Integer.toString(queryPartsOutput.getInt("partID"));
                inv = Integer.toString(queryPartsOutput.getInt("stock"));
                price = queryPartsOutput.getBigDecimal("price_unit").toString();
                Double obj = Double.parseDouble(price);
                num = BigDecimal.valueOf(obj);
                PartData data = new PartData(
                        Integer.parseInt(partID),
                        name,
                        Integer.parseInt(inv),
                        num
                );
                if(inv.equals(text) || price.equals(text)) {
                    partList.add(data);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("No matches have been found. Please try again.");
                    alert.showAndWait();

                    homePage_searchPartInputField.clear();

                    //SQL Query - executed in the backend database
                    String refreshPartsViewQuery = "SELECT partID, part_name, stock, price_unit FROM parts";
                    try {
                        statement = connectDB.createStatement();
                        ResultSet queryRefreshPartsOutput = statement.executeQuery(refreshPartsViewQuery);

                        while (queryRefreshPartsOutput.next()) {

                            //populate the observableList
                            partList.add(new PartData(queryRefreshPartsOutput.getInt("partID"),
                                    queryRefreshPartsOutput.getString("part_name"),
                                    queryRefreshPartsOutput.getInt("stock"),
                                    queryRefreshPartsOutput.getBigDecimal("price_unit")));
                        }
                        //PropertyValueFactory corresponds to the new PartData fields
                        //the table column is the one we annotate above
                        parts_tableView_col_partID.setCellValueFactory(new PropertyValueFactory<>("partID"));
                        parts_tableView_col_partName.setCellValueFactory(new PropertyValueFactory<>("part_name"));
                        parts_tableView_col_inventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
                        parts_tableView_col_priceUnit.setCellValueFactory(new PropertyValueFactory<>("price_unit"));

                        parts_tableView.setItems(partList);

                    } catch(SQLException e) {
                        Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, e);
                        e.printStackTrace();
                        e.getCause();
                    }
                }
            }

            //PropertyValueFactory corresponds to the new PartData fields
            //the table column is the one we annotate above
            parts_tableView_col_partID.setCellValueFactory(new PropertyValueFactory<>("partID"));
            parts_tableView_col_partName.setCellValueFactory(new PropertyValueFactory<>("part_name"));
            parts_tableView_col_inventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
            parts_tableView_col_priceUnit.setCellValueFactory(new PropertyValueFactory<>("price_unit"));

            parts_tableView.setItems(partList);
            //closing statement once I am done with the query to avoid crashing!!
            statement.close();
            queryPartsOutput.close();
            connectDB.close();


        } catch(SQLException e) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     * Void KeyReleaseSearchPart() method is used to find a part row by typing information in the input field.
     * @param event represents the event that triggers the action.
     * @exception SQLException if a database error or other errors occur.
     * @see SQLException
     */
    @FXML
    void KeyReleaseSearchPart(KeyEvent event) {
        String text = homePage_searchPartInputField.getText();
        //System.out.println(text);

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        //SQL Query - executed in the backend database
        String partsViewQuery = "SELECT partID, part_name, stock, price_unit FROM parts";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryPartsOutput = statement.executeQuery(partsViewQuery);
            parts_tableView.getItems().clear();
            while (queryPartsOutput.next()) {

                //populate the observableList
                String name = queryPartsOutput.getString("part_name");
                String partID = Integer.toString(queryPartsOutput.getInt("partID"));
                PartData data = new PartData(Integer.parseInt(partID),
                        name,
                        queryPartsOutput.getInt("stock"),
                        queryPartsOutput.getBigDecimal("price_unit"));
                if(name.toLowerCase().contains(text.toLowerCase()) && partList.contains(data) == false || partID.equals(text)) {
                    partList.add(data);
                }
            }

            //PropertyValueFactory corresponds to the new PartData fields
            //the table column is the one we annotate above
            parts_tableView_col_partID.setCellValueFactory(new PropertyValueFactory<>("partID"));
            parts_tableView_col_partName.setCellValueFactory(new PropertyValueFactory<>("part_name"));
            parts_tableView_col_inventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
            parts_tableView_col_priceUnit.setCellValueFactory(new PropertyValueFactory<>("price_unit"));

            parts_tableView.setItems(partList);

            //closing statement once I am done with the query to avoid crashing!!
            statement.close();
            queryPartsOutput.close();
            connectDB.close();

        } catch(SQLException e) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     * Void btnSearchProduct() method is used to find a product row by typing information in the input field and clicking the search button.
     * e represents the event that triggers the action.
     * @exception SQLException if a database error or other errors occur.
     * @see SQLException
     */
    @FXML
    void btnSearchProduct(MouseEvent event) {
        String text = homePage_searchProductInputField.getText();
        System.out.println(text);
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        //SQL Query - executed in the backend database
        String productsViewQuery = "SELECT productID, product_name, stock, price_unit FROM products";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryProductsOutput = statement.executeQuery(productsViewQuery);
            //populate the observableList
            String productName = "";
            String productID = "";
            String inv = "";
            String price = "";
            BigDecimal num;

            products_tableView.getItems().clear();
            if (queryProductsOutput.next()) {
                //populate the observableList
                productName = queryProductsOutput.getString("product_name");
                productID = Integer.toString(queryProductsOutput.getInt("productID"));
                inv = Integer.toString(queryProductsOutput.getInt("stock"));
                price = queryProductsOutput.getBigDecimal("price_unit").toString();
                Double obj = Double.parseDouble(price);
                num = BigDecimal.valueOf(obj);
                ProductData searchProductData = new ProductData(
                        Integer.parseInt(productID),
                        productName,
                        Integer.parseInt(inv),
                        num
                );
                if(inv.equals(text) || price.equals(text)) {
                    productList.add(searchProductData);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("No matches have been found. Please try again.");
                    alert.showAndWait();

                    homePage_searchProductInputField.clear();

                    String refreshProductsViewQuery = "SELECT productID, product_name, stock, price_unit FROM products";
                    try {
                        statement = connectDB.createStatement();
                        ResultSet queryRefreshProductsOutput = statement.executeQuery(refreshProductsViewQuery);

                        while (queryRefreshProductsOutput.next()) {
                            productList.add(new ProductData(queryRefreshProductsOutput.getInt("productID"),
                                    queryRefreshProductsOutput.getString("product_name"),
                                    queryRefreshProductsOutput.getInt("stock"),
                                    queryRefreshProductsOutput.getBigDecimal("price_unit")));
                        }

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

            //PropertyValueFactory corresponds to the new PartData fields
            //the table column is the one we annotate above
            products_tableView_col_productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
            products_tableView_col_productName.setCellValueFactory(new PropertyValueFactory<>("product_name"));
            products_tableView_col_inventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
            products_tableView_col_priceUnit.setCellValueFactory(new PropertyValueFactory<>("price_unit"));

            products_tableView.setItems(productList);
            //closing statement once I am done with the query to avoid crashing!!
            statement.close();
            queryProductsOutput.close();
            connectDB.close();

        } catch(SQLException e) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     * Void KeyReleaseSearchProduct() method is used to find a product row by typing information in the input field.
     * e represents the event that triggers the action.
     * @exception SQLException if a database error or other errors occur.
     * @see SQLException
     */
    @FXML
    void KeyReleaseSearchProduct(KeyEvent event) {
        String text = homePage_searchProductInputField.getText();
//        System.out.println(text);
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        //SQL Query - executed in the backend database
        String productsViewQuery = "SELECT productID, product_name, stock, price_unit FROM products";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryProductsOutput = statement.executeQuery(productsViewQuery);
            products_tableView.getItems().clear();
            while (queryProductsOutput.next()) {
                //populate the observableList
                String productName = queryProductsOutput.getString("product_name");
                String productID = Integer.toString(queryProductsOutput.getInt("productID"));
                ProductData searchData = new ProductData(Integer.parseInt(
                        productID),
                        productName,
                        queryProductsOutput.getInt("stock"),
                        queryProductsOutput.getBigDecimal("price_unit"));
                if(productName.toLowerCase().contains(text.toLowerCase()) && productList.contains(searchData) == false || productID.equals(text)) {
                    productList.add(searchData);
                }
            }
            //PropertyValueFactory corresponds to the new PartData fields
            //the table column is the one we annotate above
            products_tableView_col_productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
            products_tableView_col_productName.setCellValueFactory(new PropertyValueFactory<>("product_name"));
            products_tableView_col_inventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
            products_tableView_col_priceUnit.setCellValueFactory(new PropertyValueFactory<>("price_unit"));

            products_tableView.setItems(productList);

            //closing statement once I am done with the query to avoid crashing!!
            statement.close();
            queryProductsOutput.close();
            connectDB.close();

        } catch(SQLException e) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     * Public void initialize() method called to initialize a controller after its root element has been completely processed.
     * @param url is used to resolve relative paths for the root object. It is null if the url is not known.
     * @param rb is used to localize the root object, and it is null if the root object is not located.
     * @exception SQLException if a database error or other errors occur.
     * @see SQLException
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        //SQL Query - executed in the backend database
        String partsViewQuery = "SELECT partID, part_name, stock, price_unit FROM parts";

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
            //closing statement once I am done with the query to avoid crashing!!
            statement.close();
            queryPartsOutput.close();

        } catch(SQLException e) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            e.getCause();
        }
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryProductsOutput = statement.executeQuery(productsViewQuery);

            while (queryProductsOutput.next()) {
                productList.add(new ProductData(queryProductsOutput.getInt("productID"),
                        queryProductsOutput.getString("product_name"),
                        queryProductsOutput.getInt("stock"),
                        queryProductsOutput.getBigDecimal("price_unit")));
            }

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