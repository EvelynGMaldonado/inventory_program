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
    private TextField modifyProduct_setProductName;

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
        index = parts_tableView.getSelectionModel().getSelectedIndex();

        String getSelectedPartIDToAssociate = "";
        String getSelectedPartNameToAssociate = "";
        String getSelectedPartStockToAssociate = "";
        String getSelectedPartPriceUnitToAssociate = "";

        //check if a row has been selected
        if(index > -1) {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();

            PartData selectedItem = parts_tableView.getSelectionModel().getSelectedItem();
            String verifyIfAssociatedPartIDAlreadyExists = "SELECT count(1) FROM modify_associated_parts WHERE partID = '" + selectedItem.getPartID() + "'";
            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryUniqueAssociatedPartIDResult = statement.executeQuery(verifyIfAssociatedPartIDAlreadyExists);

                while(queryUniqueAssociatedPartIDResult.next()) {
                    if(queryUniqueAssociatedPartIDResult.getInt(1) == 1) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error message");
                        alert.setHeaderText(null);
                        alert.setContentText("Part is already associated to this product.");
                        alert.showAndWait();
                    } else {
                        //retrieve data of selected item
                        String associateSelectedPartToCurrentProduct = "SELECT partID, part_name, stock, price_unit FROM parts WHERE partID = '" + selectedItem.getPartID() + "'";

                        try {
                            statement = connectDB.createStatement();
                            ResultSet querySelectedPartToAssociateResult = statement.executeQuery(associateSelectedPartToCurrentProduct);
                            // System.out.println("The selected part to associate is: "+querySelectedPartResult.);
                            while(querySelectedPartToAssociateResult.next()) {
                                System.out.println(querySelectedPartToAssociateResult.getString("partID"));
                                System.out.println(querySelectedPartToAssociateResult.getString("part_name"));
                                getSelectedPartIDToAssociate = querySelectedPartToAssociateResult.getString("partID");
                                getSelectedPartNameToAssociate = querySelectedPartToAssociateResult.getString("part_name");
                                //                    System.out.println("getSingleAssociatedPartName is: " + getSingleAssociatedPartName);
                                getSelectedPartStockToAssociate = querySelectedPartToAssociateResult.getString("stock");
                                getSelectedPartPriceUnitToAssociate = querySelectedPartToAssociateResult.getString("price_unit");
                            }
                            String insertSelectedAssociatedPartFields = "INSERT INTO modify_associated_parts (partID, part_name, stock, price_unit) VALUES ('";
                            String insertSelectedAssociatedPartValues = getSelectedPartIDToAssociate + "', '" + getSelectedPartNameToAssociate + "', '" + getSelectedPartStockToAssociate + "', '" + getSelectedPartPriceUnitToAssociate + "')";
                            String insertSelectedAssociatedPartToDB_modify_associated_partsTable = insertSelectedAssociatedPartFields + insertSelectedAssociatedPartValues;

                            try {
                                statement = connectDB.createStatement();
                                statement.executeUpdate(insertSelectedAssociatedPartToDB_modify_associated_partsTable);

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Successful New Part Association");
                                alert.setHeaderText(null);
                                alert.setContentText("New Part has been successfully associated to the current product");
                                alert.showAndWait();

                                //After successfully saving a new part we redirect to the home_page and are able to see the updated data table
                                displayAssociatedPartDataTableView();

                            } catch (Exception e) {
                                e.printStackTrace();
                                e.getCause();
                            }

                        } catch (SQLException e) {
                            e.printStackTrace();
                            e.getCause();
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
        if(!modifyProduct_setProductName.getText().trim().isEmpty() || !modifyProduct_setInventoryLevel.getText().trim().isEmpty() || !modifyProduct_setPriceUnit.getText().trim().isEmpty() || !modifyProduct_setMax.getText().trim().isEmpty() || !modifyProduct_setMin.getText().isEmpty()) {
            validateUpdatedProductNameAndProductID();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields.");
            alert.showAndWait();
        }
    }

    public void validateUpdatedProductNameAndProductID() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyUpdatedProductNameMatchesProductID = "SELECT count(1) FROM products WHERE product_name = '" + modifyProduct_setProductName.getText() + "' AND productID = '" + modifyProduct_productIDTextField.getText() +"'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryUniqueUpdatedProductNameAndProductIDResult = statement.executeQuery(verifyUpdatedProductNameMatchesProductID);

            while(queryUniqueUpdatedProductNameAndProductIDResult.next()) {
                //if updated part name matches with the ID... call the UpdatePart(); method
                if(queryUniqueUpdatedProductNameAndProductIDResult.getInt(1) == 1) {
                    //                    messageLabel.setText("Part Name already exists. Please try again.");
                    updateProduct();
                } else {
                    //if updated part name does not match with the ID... call the verifyIfPartNameAlreadyExists(); method
                    verifyIfProductNameAlreadyExists();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void verifyIfProductNameAlreadyExists() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyProductNameAvailability = "SELECT count(1) FROM products WHERE product_name = '" + modifyProduct_setProductName.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryUniqueUpdatedProductNameResult = statement.executeQuery(verifyProductNameAvailability);

            while(queryUniqueUpdatedProductNameResult.next()) {
                //if updated part name already exists in our DB, but it does not match with the current ID ... show an error alert
                if(queryUniqueUpdatedProductNameResult.getInt(1) == 1) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Product Name already exists and has a different ID. Please use a different product name or look for the product name in the dashboard, select the row, and click on the Modify button.");
                    alert.showAndWait();
                } else {
                    //if updated part name does not exist in our DB, and it does not match with the current ID... then call the call the UpdatePart(); method.
                    updateProduct();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void updateProduct(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String modifyPage_productID = modifyProduct_productIDTextField.getText();
        String updatedProductName = modifyProduct_setProductName.getText();
        String updatedProductInventoryLevel = modifyProduct_setInventoryLevel.getText();
        String updatedProductPriceUnit = modifyProduct_setPriceUnit.getText();
        String updatedProductMax= modifyProduct_setMax.getText();
        String updatedProductMin = modifyProduct_setMin.getText();

        String updateProductDataInDB = "UPDATE products SET product_name = '" + updatedProductName + "', stock = '" + updatedProductInventoryLevel + "', price_unit = '" + updatedProductPriceUnit + "', min = '" + updatedProductMin + "', max = '" + updatedProductMax + "' WHERE productID = '" + modifyPage_productID + "' ";

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(updateProductDataInDB);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful Product Data Update");
            alert.setHeaderText(null);
            alert.setContentText("Product Data has been successfully updated in EM Inventory Management System");
            alert.showAndWait();

            //After successfully updating the product, we call the updateCurrentProductAssociatedParts(); method
            updateCurrentProductAssociatedParts();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    //Last step on modify product functionality
    public void updateCurrentProductAssociatedParts() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String currentProductName = modifyProduct_setProductName.getText();
        System.out.println("line 318 -- The product name is: " + currentProductName);

        //Retrieve current productID from the products table
        String currentProductID = "";
        String getCurrentProductIDQuery = "SELECT productID FROM products WHERE product_name = '" + currentProductName + "'";
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryCurrentProductIDResult = statement.executeQuery(getCurrentProductIDQuery);

            while(queryCurrentProductIDResult.next()) {
                currentProductID = queryCurrentProductIDResult.getString("productID");
                System.out.println("The current productID on line 328 is: " + currentProductID);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        //Empty the pre-existent productID- partsID relations in the products_associated_parts table
        PreparedStatement pst;
        String deleteSelectedAssociatedPart = "DELETE FROM products_associated_parts WHERE productID =  '" + currentProductID + "'";
        try{
            pst = connectDB.prepareStatement(deleteSelectedAssociatedPart);
            pst.execute();

        } catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        //select all the partsID from our temporary modify_associated_parts table
        String updatedAssociatedPartsIDs = "";
        String getUpdatedAssociatedPartsIDsQuery = "SELECT partID FROM modify_associated_parts";
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryUpdatedAssociatedPartsIDsResult = statement.executeQuery(getUpdatedAssociatedPartsIDsQuery);

            //while we have partsIDS left we keep inserting them into products_associated_parts table
            while(queryUpdatedAssociatedPartsIDsResult.next()) {
                updatedAssociatedPartsIDs = queryUpdatedAssociatedPartsIDsResult.getString("partID");
                System.out.println("The current partsID on line 368 are: " + updatedAssociatedPartsIDs);

                String insertPartsPerProductFields = "INSERT INTO products_associated_parts (productID, partID) VALUES ('";
                String insertPartsPerProductValues = currentProductID + "', '" + updatedAssociatedPartsIDs + "') ";
                String finalAssociation = insertPartsPerProductFields + insertPartsPerProductValues;

                try{
                    statement = connectDB.createStatement();
                    statement.executeUpdate(finalAssociation);

                }catch(SQLException e){
                    e.printStackTrace();
                    e.getCause();
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful Update Product and PartsID data association");
            alert.setHeaderText(null);
            alert.setContentText("Product and its PartsID association has been successfully updated to table products_associated_parts at the EM Inventory Management System");
            alert.showAndWait();

            //After successfully saving a new product we redirect to the home_page and are able to see the updated data table
            modifyProductRedirectsToEMIMSHomePage();

        } catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void modifyProduct_addProductBtnAction(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Updated product hasn't been saved yet. Are you sure that you want to switch to the Add Product window?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)) {
                modifyProductRedirectsToAddProductPage();
            } else {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void modifyProduct_addPartBtnAction(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Updated product hasn't been saved yet. Are you sure that you want to switch to the Add Part window?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)) {
                modifyProductRedirectsToAddPartPage();
            } else {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void modifyProduct_modifyPartBtnAction_Error(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please go to Home and select the part that you want to modify.");
            alert.showAndWait();

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

    public void modifyProductRedirectsToAddPartPage () throws IOException {
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

    public void modifyProductRedirectsToAddProductPage () throws IOException {
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
        modifyProduct_setProductName.setText(getSingleProductName);
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
