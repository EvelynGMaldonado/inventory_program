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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModifyProductController implements Initializable {

    HelloController helloController;
    ProductData productData;
    PartsAndProductsInventory partsAndProductsInventory;

    @FXML
    private Button addPartPageBtn;

    @FXML
    private Button addProductPageBtn;

    @FXML
    private StackPane addProduct_page;

    @FXML
    private Button modifyProduct_closeBtn;

    @FXML
    private Button modifyPartPageBtn;

    @FXML
    private Button settingsBtn;

    @FXML
    private Button startBtn;

    @FXML
    private Button modifyProduct_cancelBtn;

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
    private TableView<ProductPartsData> associatedParts_tableview = new TableView<ProductPartsData>();

    @FXML
    private TableColumn<ProductPartsData, Integer> associatedParts_tableView_col_inventoryLevel = new TableColumn<>("stock");

    @FXML
    private TableColumn<ProductPartsData, Integer> associatedParts_tableView_col_partID = new TableColumn<>("partID");

    @FXML
    private TableColumn<ProductPartsData, String> associatedParts_tableView_col_partName = new TableColumn<>("part_name");

    @FXML
    private TableColumn<ProductPartsData, BigDecimal> associatedParts_tableView_col_priceUnit = new TableColumn<>("price_unit");

    @FXML
    private TextField modifyProduct_productIDTextField;

    @FXML
    private TextField modifyProduct_setInventoryLevel;

    @FXML
    private TextField modifyProduct_setMax;

    @FXML
    private TextField modifyProduct_setMin;

    @FXML
    private TextField modifyProduct_setPartName;

    @FXML
    private TextField modifyProduct_setPriceUnit;

    int index = -1;

    ObservableList<PartData> partList = FXCollections.observableArrayList();
    ObservableList<ProductPartsData> associatedPartsByProductList = FXCollections.observableArrayList();

    private final String getSingleProductID;
    private final String getSingleProductName;
    private final String getSingleProductStock;
    private final String getSingleProductPriceUnit;
    private final String getSingleProductMin;
    private final String getSingleProductMax;

    public ModifyProductController(PartsAndProductsInventory partsAndProductsInventory, ProductData productData, String getSingleProductID, String getSingleProductName, String getSingleProductStock, String getSingleProductPriceUnit, String getSingleProductMin, String getSingleProductMax) {
        this.partsAndProductsInventory = partsAndProductsInventory;
        this.productData = productData;
        this.getSingleProductID = getSingleProductID;
        this.getSingleProductName = getSingleProductName;
        this.getSingleProductStock= getSingleProductStock;
        this.getSingleProductPriceUnit= getSingleProductPriceUnit;
        this.getSingleProductMin= getSingleProductMin;
        this.getSingleProductMax= getSingleProductMax;
    }

    //on click addBtn located on Add Product page, I check if a row has been selected,
    //retrieve data from parts table (choose part data), and insert it into associated part data table.
    @FXML
    public void clickAddAssociatedPartBtn (ActionEvent event){
//        DatabaseConnection connectNow = new DatabaseConnection();
//        Connection connectDB = connectNow.getConnection();
        index = parts_tableView.getSelectionModel().getSelectedIndex();

        String getSingleAssociatedPartID = "";
        String getSingleAssociatedPartName = "";
        String getSingleAssociatedPartStock = "";
        String getSingleAssociatedPartPriceUnit = "";
        String getSingleAssociatedPartMin = "";
        String getSingleAssociatedPartMax = "";
        String getSingleAssociatedPartMachineID = "";
        String getSingleAssociatedPartCompanyName = "";

        //check if a row has been selected
        if(index > -1) {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();

            PartData selectedItem = parts_tableView.getSelectionModel().getSelectedItem();
            String verifyIfAssociatedPartIDAlreadyExists = "SELECT count(1) FROM associated_parts WHERE partID = '" + selectedItem.getPartID() + "'";
            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryUniqueAssociatedPartIDResult = statement.executeQuery(verifyIfAssociatedPartIDAlreadyExists);

                while(queryUniqueAssociatedPartIDResult.next()) {
                    if(queryUniqueAssociatedPartIDResult.getInt(1) == 1) {
                        //                    messageLabel.setText("Part Name already exists. Please try again.");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error message");
                        alert.setHeaderText(null);
                        alert.setContentText("Part is already associated to this product.");
                        alert.showAndWait();
                    } else {
                        //retrieve data of selected item
                        String associateSelectedPartToNewProduct = "SELECT * FROM parts WHERE partID = '" + selectedItem.getPartID() + "'";

                        try {
                            statement = connectDB.createStatement();
                            ResultSet querySelectedPartToAssociateResult = statement.executeQuery(associateSelectedPartToNewProduct);
                            // System.out.println("The selected part to associate is: "+querySelectedPartResult.);
                            while(querySelectedPartToAssociateResult.next()) {
                                System.out.println(querySelectedPartToAssociateResult.getString("partID"));
                                System.out.println(querySelectedPartToAssociateResult.getString("part_name"));
                                getSingleAssociatedPartID = querySelectedPartToAssociateResult.getString("partID");
                                getSingleAssociatedPartName = querySelectedPartToAssociateResult.getString("part_name");
                                //                    System.out.println("getSingleAssociatedPartName is: " + getSingleAssociatedPartName);
                                getSingleAssociatedPartStock = querySelectedPartToAssociateResult.getString("stock");
                                getSingleAssociatedPartPriceUnit = querySelectedPartToAssociateResult.getString("price_unit");
                                getSingleAssociatedPartMin = querySelectedPartToAssociateResult.getString("min");
                                getSingleAssociatedPartMax = querySelectedPartToAssociateResult.getString("max");
                                getSingleAssociatedPartMachineID = querySelectedPartToAssociateResult.getString("machineID");
                                //                    System.out.println("getSinglePartMachineID is: " + getSingleAssociatedPartMachineID);
                                getSingleAssociatedPartCompanyName = querySelectedPartToAssociateResult.getString("company_name");
                                //                    System.out.println("getSinglePartCompanyName is: " + getSingleAssociatedPartCompanyName);
                            }

                        } catch (SQLException e) {
                            e.printStackTrace();
                            e.getCause();
                        }
                        if(getSingleAssociatedPartCompanyName == null) {
                            String insertNewInHouseAssociatedPartFields = "INSERT INTO associated_parts (partID, part_name, stock, price_unit, min, max, machineID) VALUES ('";
                            String insertNewInHouseAssociatedPartValues = getSingleAssociatedPartID + "', '" + getSingleAssociatedPartName + "',  '" + getSingleAssociatedPartStock + "', '" + getSingleAssociatedPartPriceUnit + "', '" + getSingleAssociatedPartMin + "', '" + getSingleAssociatedPartMax + "', '" + getSingleAssociatedPartMachineID + "')";
                            String insertNewInHouseAssociatedPartToDB_associated_partsTable = insertNewInHouseAssociatedPartFields + insertNewInHouseAssociatedPartValues;

                            try {
                                statement = connectDB.createStatement();
                                statement.executeUpdate(insertNewInHouseAssociatedPartToDB_associated_partsTable);

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Successful In-House Part Registration");
                                alert.setHeaderText(null);
                                alert.setContentText("New In House  Part has been successfully added to EM Inventory Management System");
                                alert.showAndWait();

                                //After successfully saving a new part we redirect to the home_page and are able to see the updated data table
                                displayAssociatedPartDataTableView();

                            } catch (Exception e) {
                                e.printStackTrace();
                                e.getCause();
                            }

                        } else if(getSingleAssociatedPartMachineID == null) {
                            String insertNewOutsourcedAssociatedPartFields = "INSERT INTO associated_parts (partID, part_name, stock, price_unit, min, max, company_name) VALUES ('";
                            String insertNewOutsourcedAssociatedPartValues = getSingleAssociatedPartID + "', '" + getSingleAssociatedPartName + "', '" + getSingleAssociatedPartStock + "', '" + getSingleAssociatedPartPriceUnit + "', '" + getSingleAssociatedPartMin + "', '" + getSingleAssociatedPartMax + "', '" + getSingleAssociatedPartCompanyName + "')";
                            String insertNewOutsourcedAssociatedPartToDB_associated_partsTable = insertNewOutsourcedAssociatedPartFields + insertNewOutsourcedAssociatedPartValues;

                            try {
                                statement = connectDB.createStatement();
                                statement.executeUpdate(insertNewOutsourcedAssociatedPartToDB_associated_partsTable);

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Successful Outsourced Part Registration");
                                alert.setHeaderText(null);
                                alert.setContentText("New Outsourced Associated Part has been successfully added to EM Inventory Management System");
                                alert.showAndWait();

                                //After successfully saving a new part we redirect to the home_page and are able to see the updated data table
                                displayAssociatedPartDataTableView();

                            } catch (Exception e) {
                                e.printStackTrace();
                                e.getCause();
                            }
                        }
                    }
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

    @FXML
    void clickSaveUpdatedProductAndAssociatedParts(ActionEvent event) {
//        if(!addProduct_setProductName.getText().trim().isEmpty() || !addProduct_setInventoryLevel.getText().trim().isEmpty() || !addProduct_setPrice.getText().trim().isEmpty() || !addProduct_setMax.getText().trim().isEmpty() || !addProduct_setMin.getText().isEmpty()) {
//            validateProductName();
//        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error message");
//            alert.setHeaderText(null);
//            alert.setContentText("Please fill all blank fields.");
//            alert.showAndWait();
//        }
    }

    //remove associated part btn removes the data of the selected row from the associated part data table
    @FXML
    private void deleteSelectedAssociatedPart (ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        index = associatedParts_tableview.getSelectionModel().getSelectedIndex();
//        parts_tableView.getItems().remove(selectedItem);

        if(index > -1) {
            PreparedStatement pst;
            ProductPartsData selectedItem = associatedParts_tableview.getSelectionModel().getSelectedItem();

            String deleteSelectedAssociatedPart = "DELETE FROM modify_associated_parts WHERE partID = ?";

            try {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure that you want to delete this Associated Part from your Product?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)) {
                    pst = connectDB.prepareStatement(deleteSelectedAssociatedPart);
                    pst.setString(1, selectedItem.getPartID().toString());
                    pst.execute();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Deletion information");
                    alert.setHeaderText(null);
                    alert.setContentText("Associated Part has been successfully removed from Current Product");
                    alert.showAndWait();

                    displayAssociatedPartDataTableView();
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
            alert.setContentText("Please select the associated part data row that you want to delete.");
            alert.showAndWait();
        }
//        DatabaseConnection connectNow = new DatabaseConnection();
//        Connection connectDB = connectNow.getConnection();
//        index = associatedParts_tableview.getSelectionModel().getSelectedIndex();
//
//        if(index > -1) {
//            ProductPartsData selectedItem = associatedParts_tableview.getSelectionModel().getSelectedItem();
//
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setTitle("Confirmation Message");
//                alert.setHeaderText(null);
//                alert.setContentText("Are you sure that you want to delete this Associated Part from your Product?");
//                Optional<ButtonType> option = alert.showAndWait();
//
//                if(option.get().equals(ButtonType.OK)) {
//
//                    associatedParts_tableview.getItems().remove(selectedItem);
//
//                    alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Deletion information");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Associated Part has been successfully removed from Current Product");
//                    alert.showAndWait();
//
//                } else {
//                    return;
//                }
//        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error message");
//            alert.setHeaderText(null);
//            alert.setContentText("Please select the associated part that you want to delete.");
//            alert.showAndWait();
//        }
    }

    //display the current associated parts data after add a new associated part
    public void displayAssociatedPartDataTableView() {
        associatedPartsByProductList.clear();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String associatedPartsViewQuery = "SELECT partID, part_name, stock, price_unit FROM modify_associated_parts";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryAssociatedPartsView = statement.executeQuery(associatedPartsViewQuery);

            while (queryAssociatedPartsView.next()) {

                //populate the observableList
                associatedPartsByProductList.add(new ProductPartsData(queryAssociatedPartsView.getInt("partID"),
                        queryAssociatedPartsView.getString("part_name"),
                        queryAssociatedPartsView.getInt("stock"),
                        queryAssociatedPartsView.getBigDecimal("price_unit")));
            }
            //PropertyValueFactory corresponds to the new PartData fields
            //the table column is the one we annotate above
            associatedParts_tableView_col_partID.setCellValueFactory(new PropertyValueFactory<>("partID"));
            associatedParts_tableView_col_partName.setCellValueFactory(new PropertyValueFactory<>("part_name"));
            associatedParts_tableView_col_inventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
            associatedParts_tableView_col_priceUnit.setCellValueFactory(new PropertyValueFactory<>("price_unit"));

            associatedParts_tableview.setItems(associatedPartsByProductList);


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

//        DatabaseConnection connectNow = new DatabaseConnection();
//        Connection connectDB = connectNow.getConnection();
//
//        String getRemaindingPartsIDByProductQuery = "SELECT partID FROM products_associated_parts WHERE productID = '" + getSingleProductID + "' ";
//        String remaindingPartsIDByProduct = "";
//        try{
//            Statement statement = connectDB.createStatement();
//            ResultSet queryCurrentAssociatedPartsIDsResult = statement.executeQuery(getRemaindingPartsIDByProductQuery);
//
//            while(queryCurrentAssociatedPartsIDsResult.next()) {
//                remaindingPartsIDByProduct = queryCurrentAssociatedPartsIDsResult.getString("partID");
//                System.out.println("The remainding partsID on line 328 is: " + remaindingPartsIDByProduct);
//
//                String allPartsByProductsViewQuery = "SELECT partID, part_name, stock, price_unit FROM parts WHERE partID = '" + remaindingPartsIDByProduct + "' ";
//                try{
//                    statement = connectDB.createStatement();
//                    ResultSet queryAssociatedPartsByProductsOutput = statement.executeQuery(allPartsByProductsViewQuery);
//
//                    while(queryAssociatedPartsByProductsOutput.next()) {
//                        associatedPartsByProductList.add(new ProductPartsData(queryAssociatedPartsByProductsOutput.getInt("partID"),
//                                queryAssociatedPartsByProductsOutput.getString("part_name"),
//                                queryAssociatedPartsByProductsOutput.getInt("stock"),
//                                queryAssociatedPartsByProductsOutput.getBigDecimal("price_unit")));
//                    }
//
//                    //PropertyValueFactory corresponds to the new PartData fields
//                    //the table column is the one we annotate above
//                    associatedParts_tableView_col_partID.setCellValueFactory(new PropertyValueFactory<>("partID"));
//                    associatedParts_tableView_col_partName.setCellValueFactory(new PropertyValueFactory<>("part_name"));
//                    associatedParts_tableView_col_inventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
//                    associatedParts_tableView_col_priceUnit.setCellValueFactory(new PropertyValueFactory<>("price_unit"));
//
//                    associatedParts_tableview.setItems(associatedPartsByProductList);
//
//                }catch(SQLException e){
//                    e.printStackTrace();
//                    e.getCause();
//                }
//            }
//
//        } catch(SQLException e) {
//            e.printStackTrace();
//            e.getCause();
//        }

    }

    public void modifyProduct_cancelBtnAction(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Updated product hasn't been saved yet. Are you sure that you want to leave the window?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)) {
//              go back to the landing page by doing ...
                modifyProduct_cancelBtn.getScene().getWindow().hide();
                //create new stage
                Stage ppMainWindow = new Stage();
                ppMainWindow.setTitle("Parts and Products - EM Inventory Management System");

                //create view for FXML
                FXMLLoader ppMainLoader = new FXMLLoader(getClass().getResource("home_page-parts&products.fxml"));

                //set view in ppMainWindow
                ppMainWindow.setScene(new Scene(ppMainLoader.load(), 800, 400));

                //launch
                ppMainWindow.show();
            } else {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    //SIDE MENU
    public void modifyProductRedirectsToEMIMSHomePage() throws IOException {
        startBtn.getScene().getWindow().hide();
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

    public void modifyProductRedirectsToAddPartPage (ActionEvent event) throws IOException {
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

    public void modifyProductRedirectsToAddProductPage (ActionEvent event) throws IOException {
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

    public void modifyProductRedirectsToModifyPartPage (ActionEvent event) throws IOException {
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String getSingleAssociatedPartID = "";
        String getSingleAssociatedPartName = "";
        String getSingleAssociatedPartStock = "";
        String getSingleAssociatedPartPriceUnit = "";
        String getSingleAssociatedPartMin = "";
        String getSingleAssociatedPartMax = "";
        String getSingleAssociatedPartMachineID = "";
        String getSingleAssociatedPartCompanyName = "";

        //CLEAR THE MODIFY ASSOCIATED PARTS TABLE
        String clearAssociatedPartsTable = "DELETE FROM modify_associated_parts";
        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(clearAssociatedPartsTable);

        } catch(SQLException e) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            e.getCause();
        }

        modifyProduct_productIDTextField.setText(getSingleProductID);
        modifyProduct_setPartName.setText(getSingleProductName);
        modifyProduct_setInventoryLevel.setText(getSingleProductStock);
        modifyProduct_setPriceUnit.setText(getSingleProductPriceUnit);
        modifyProduct_setMin.setText(getSingleProductMin);
        modifyProduct_setMax.setText(getSingleProductMax);

        //SQL Query - executed in the backend database
        String partsViewQuery = "SELECT partID, part_name, stock, price_unit FROM parts";
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

        } catch(SQLException e) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            e.getCause();
        }

        String getPartsIDByProductQuery = "SELECT partID FROM products_associated_parts WHERE productID = '" + getSingleProductID + "' ";
        String allPartsIDByProduct = "";
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryCurrentAssociatedPartsIDsResult = statement.executeQuery(getPartsIDByProductQuery);

            while(queryCurrentAssociatedPartsIDsResult.next()) {
                allPartsIDByProduct = queryCurrentAssociatedPartsIDsResult.getString("partID");
                System.out.println("The current partsID on line 282 is: " + allPartsIDByProduct);

                String associatedPartsByProductsViewQuery = "SELECT partID, part_name, stock, price_unit FROM parts WHERE partID = '" + allPartsIDByProduct + "' ";
                try{
                    statement = connectDB.createStatement();
                    ResultSet queryAssociatedPartsByProductsOutput = statement.executeQuery(associatedPartsByProductsViewQuery);

                    while(queryAssociatedPartsByProductsOutput.next()) {
                        getSingleAssociatedPartID = queryAssociatedPartsByProductsOutput.getString("partID");
                        getSingleAssociatedPartName = queryAssociatedPartsByProductsOutput.getString("part_name");
                        System.out.println("Line 563 -- getSingleAssociatedPartName is: " + getSingleAssociatedPartName);
                        getSingleAssociatedPartStock = queryAssociatedPartsByProductsOutput.getString("stock");
                        getSingleAssociatedPartPriceUnit = queryAssociatedPartsByProductsOutput.getString("price_unit");

                        associatedPartsByProductList.add(new ProductPartsData(queryAssociatedPartsByProductsOutput.getInt("partID"),
                        queryAssociatedPartsByProductsOutput.getString("part_name"),
                        queryAssociatedPartsByProductsOutput.getInt("stock"),
                        queryAssociatedPartsByProductsOutput.getBigDecimal("price_unit")));
                    }

                    //PropertyValueFactory corresponds to the new PartData fields
                    //the table column is the one we annotate above
                    associatedParts_tableView_col_partID.setCellValueFactory(new PropertyValueFactory<>("partID"));
                    associatedParts_tableView_col_partName.setCellValueFactory(new PropertyValueFactory<>("part_name"));
                    associatedParts_tableView_col_inventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
                    associatedParts_tableView_col_priceUnit.setCellValueFactory(new PropertyValueFactory<>("price_unit"));

                    associatedParts_tableview.setItems(associatedPartsByProductList);

                }catch(SQLException e){
                    e.printStackTrace();
                    e.getCause();
                }
                String insertPreviouslyAssociatedPartFields = "INSERT INTO modify_associated_parts (partID, part_name, stock, price_unit) VALUES ('";
                String insertPreviouslyAssociatedPartValues = getSingleAssociatedPartID + "', '" + getSingleAssociatedPartName + "',  '" + getSingleAssociatedPartStock + "', '" + getSingleAssociatedPartPriceUnit + "')";
                String insertPreviouslyAssociatedPartsToDB_modify_associated_parts = insertPreviouslyAssociatedPartFields + insertPreviouslyAssociatedPartValues;

                try {
                    statement = connectDB.createStatement();
                    statement.executeUpdate(insertPreviouslyAssociatedPartsToDB_modify_associated_parts );

                } catch (Exception e) {
                    e.printStackTrace();
                    e.getCause();
                }
            }

        } catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
