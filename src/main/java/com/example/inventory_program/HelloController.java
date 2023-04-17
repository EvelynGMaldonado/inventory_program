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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
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
    private Button homePage_searchPartBtn;

    @FXML
    private TextField homePage_searchPartInputField;

    @FXML
    private Button homePage_searchProductBtn;

    @FXML
    private TextField homePage_searchProductInputField;

    @FXML
    private Button homePage_addNewPartBtn;

    @FXML
    private Button homePage_addNewProductBtn;

    @FXML
    private Button homePage_deletePartBtn;

    @FXML
    private Button homePage_deleteProductBtn;

    @FXML
    private Button homePage_modifyPartBtn;

    @FXML
    private Button homePage_modifyProductBtn;

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
    private Button homePage_closeBtn;

    @FXML
    private Button landingPage_closeBtn;

    int index = -1;

    ObservableList<PartData> partList = FXCollections.observableArrayList();

    ObservableList<ProductData> productList = FXCollections.observableArrayList();

    //**new**
    PartsAndProductsInventory partsAndProductsInventory;

    //Method to get selected part records
    @FXML
    void getSelectedPart (MouseEvent event) {
//        index = parts_tableView.getSelectionModel().getSelectedIndex();
//        if(index <= -1) {
//            return;
//        } else{
//        //EXAMPLE:
//        //text_id.setText(col_id.getCellData(index).toString());
//            parts_tableView_col_partID.getCellData(index).toString();
//
//        }
    }


    //Method to delete selected part records
    @FXML
    private void deleteSelectedPart (ActionEvent event) {
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
                    alert.setTitle("Data Part has been deleted");
                    alert.setHeaderText(null);
                    alert.setContentText("Data Part has been successfully removed from the EM Inventory Management System");
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

    private ObservableList<RowPartData> rowPartDataList = FXCollections.observableArrayList();


    //********#2 Modify part
//    public void clickModifyPartPageBtn (ActionEvent event){
//        DatabaseConnection connectNow = new DatabaseConnection();
//        Connection connectDB = connectNow.getConnection();
//
//        index = parts_tableView.getSelectionModel().getSelectedIndex();
////        parts_tableView.getItems().remove(selectedItem);
//
//        if(index > -1) {
//            PartData selectedItem = parts_tableView.getSelectionModel().getSelectedItem();
//            String rowPartName = "";
////            String rowSql = "SELECT partID, part_name, stock, price_unit, min, max, machineID, company_name FROM parts WHERE partID =?";
//
//            String modifySelectedPart = "SELECT * FROM parts WHERE partID = '" + selectedItem.getPartID() + "'";
////
//            try {
//                Statement statement = connectDB.createStatement();
//                ResultSet querySelectedPartResult = statement.executeQuery(modifySelectedPart);
////                PreparedStatement pst = connectDB.prepareStatement(rowSql);
////                pst.setString(1, selectedItem.getPartID().toString());
////                ResultSet rs = pst.executeQuery();
//                 if(querySelectedPartResult.next()) {
//                     String rowPartID = querySelectedPartResult.getString("partID");
//
//                     rowPartName = querySelectedPartResult.getString("part_name");
//                     modifyPart_setPartName.setText(rowPartName);
//                     String rowPartStock = querySelectedPartResult.getString("stock");
//                     String rowPartPriceUnit = querySelectedPartResult.getString("price_unit");
//                     String rowPartMin = querySelectedPartResult.getString("min");
//                     String rowPartMax = querySelectedPartResult.getString("max");
//                     String rowPartMachineID = querySelectedPartResult.getString("machineID");
//                     String rowPartCompanyName = querySelectedPartResult.getString("company_name");
//
//                     System.out.println(rowPartID + rowPartName+ rowPartStock+ rowPartPriceUnit+ rowPartMin+ rowPartMax+ rowPartMachineID+ rowPartCompanyName);
//
//                 }
//////                while(querySelectedPartResult.next()) {
//////                    System.out.println(querySelectedPartResult.getString("partID"));
//////////                    System.out.println(querySelectedPartResult.getString("part_name"));
//////                    getRowPartName = querySelectedPartResult.getString("part_name");
//////                    System.out.println("This new try... getRowPartName is: " + getRowPartName);
//////
//////                }
//                modifyPartPageBtn.getScene().getWindow().hide();
//                Stage modifyPartPageWindow = new Stage();
//                modifyPartPageWindow.setTitle("Add Part - EM Inventory Management System");
//
//                //create view for FXML
//                FXMLLoader modifyPartPageLoader = new FXMLLoader(getClass().getResource("modifyPart_page.fxml"));
//
//                //set view in ppMainWindow
//                modifyPartPageWindow.setScene(new Scene(modifyPartPageLoader.load(), 600, 400));
//
//                //launch
//                modifyPartPageWindow.show();
//
//                if(modifyPart_setPartName.getText() == null || modifyPart_setPartName.getText() == "") {
//                    modifyPart_setPartName.setText(rowPartName);
//                }
//            } catch(Exception e) {
//                e.printStackTrace();
//                e.getCause();
//            }
//        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error message");
//            alert.setHeaderText(null);
//            alert.setContentText("Please select the data row that you want to delete.");
//            alert.showAndWait();
//        }
//
//        modifyPart_setPartName.setText("hello!!");
//
//    }

    @FXML
    public void clickModifyPartPageBtn (ActionEvent event){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        index = parts_tableView.getSelectionModel().getSelectedIndex();
//        parts_tableView.getItems().remove(selectedItem);

        if(index > -1) {
            PreparedStatement pst;

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
                modifyPartPageWindow.setTitle("Add Part - EM Inventory Management System");

                //create view for FXML
                FXMLLoader modifyPartPageLoader = new FXMLLoader(getClass().getResource("modifyPart_page.fxml"));

////        //Get modifyPart_page Controller : ModifyPartController
//        ModifyPartController modifyPartController = modifyPartPageLoader.getController();
////
////        //Pass any data we want, we can have multiple method calls here
//        modifyPartController.showSelectedPartDataInformation(getPartName);

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
            alert.setContentText("Please select the data row that you want to delete.");
            alert.showAndWait();
        }
    }

    //Method to delete selected product records
    @FXML
    private void deleteSelectedProduct (ActionEvent event) {

    }


    //!!!!!!REFRESH TABLE AFTER ADDING A NEW USER!!!!
//    public void refreshPartsTable() {
//        partList.clear();
//        DatabaseConnection connectNow = new DatabaseConnection();
//        Connection connectDB = connectNow.getConnection();
//        //SQL Query - executed in the backend database
//        String partsViewQuery = "SELECT partID, part_name, stock, price_unit FROM parts";
//        try {
//            Statement statement = connectDB.createStatement();
//            ResultSet queryPartsOutput = statement.executeQuery(partsViewQuery);
//
//            while (queryPartsOutput.next()) {
//
//                //populate the observableList
//                partList.add(new PartData(queryPartsOutput.getInt("partID"),
//                        queryPartsOutput.getString("part_name"),
//                        queryPartsOutput.getInt("stock"),
//                        queryPartsOutput.getBigDecimal("price_unit")));
//            }
//
//            //PropertyValueFactory corresponds to the new PartData fields
//            //the table column is the one we annotate above
//            parts_tableView_col_partID.setCellValueFactory(new PropertyValueFactory<>("partID"));
//            parts_tableView_col_partName.setCellValueFactory(new PropertyValueFactory<>("part_name"));
//            parts_tableView_col_inventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
//            parts_tableView_col_priceUnit.setCellValueFactory(new PropertyValueFactory<>("price_unit"));
//
//            parts_tableView.setItems(partList);
//
//        } catch(SQLException e) {
//            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, e);
//            e.printStackTrace();
//            e.getCause();
//        }
//    }

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

//    @FXML
//    public void clickModifyPartPageBtn (ActionEvent event) throws IOException {
//        modifyPartPageBtn.getScene().getWindow().hide();
//        //create new stage
//        Stage modifyPartPageWindow = new Stage();
//        modifyPartPageWindow.setTitle("Add Part - EM Inventory Management System");
//
//        //create view for FXML
//        FXMLLoader modifyPartPageLoader = new FXMLLoader(getClass().getResource("modifyPart_page.fxml"));
//
////        //Get modifyPart_page Controller : ModifyPartController
////        ModifyPartController modifyPartController = modifyPartPageLoader.getController();
////
////        //Pass any data we want, we can have multiple method calls here
////        modifyPartController.showSelectedPartDataInformation(nameScene1.getText(), ageScene1.getText());
//
//
//        //set view in ppMainWindow
//        modifyPartPageWindow.setScene(new Scene(modifyPartPageLoader.load(), 600, 400));
//
//        //launch
//        modifyPartPageWindow.show();
//
//    }

    public void closeBtnAction(ActionEvent e) {
        Stage stage = (Stage) landingPage_closeBtn.getScene().getWindow();
        stage.close();
    }

    public void logOutBtnAction() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure that you want to Log Out?");
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


    @FXML
    void btnSearchPart(MouseEvent event)
    {
        String text = homePage_searchPartInputField.getText();
        System.out.println(text);

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        //SQL Query - executed in the backend database
        String partsViewQuery = "SELECT partID, part_name, stock, price_unit FROM parts";

        String productsViewQuery = "SELECT productID, product_name, stock, price_unit FROM products";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryPartsOutput = statement.executeQuery(partsViewQuery);
            parts_tableView.getItems().clear();
            while (queryPartsOutput.next()) {

                //populate the observableList
                String name = queryPartsOutput.getString("part_name");
                String partID = Integer.toString(queryPartsOutput.getInt("partID"));
                String inv = Integer.toString(queryPartsOutput.getInt("stock"));
//                String price = DecimalFormat.getInstance().format(queryPartsOutput.getBigDecimal("price_unit"));
                String price = queryPartsOutput.getBigDecimal("price_unit").toString();
                Double obj = Double.parseDouble(price);
                BigDecimal num = BigDecimal.valueOf(obj);
                PartData data = new PartData(Integer.parseInt(partID),
                        name,
                        Integer.parseInt(inv),
                        num
                );
                if(inv.equals(text) || price.equals(text))
                {

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

    @FXML
    void KeyRelease(KeyEvent event)
    {
        String text = homePage_searchPartInputField.getText();
//        System.out.println(text);

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        //SQL Query - executed in the backend database
        String partsViewQuery = "SELECT partID, part_name, stock, price_unit FROM parts";

        String productsViewQuery = "SELECT productID, product_name, stock, price_unit FROM products";

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
                if(name.toLowerCase().contains(text.toLowerCase()) && partList.contains(data) == false || partID.equals(text))
                {

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
           // connectDB.close();
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