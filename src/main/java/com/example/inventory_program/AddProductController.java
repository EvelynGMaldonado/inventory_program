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
 * Public class AddProductController is used to add a new product to the EM management system database.
 * RUNTIME ERROR:
 * FUTURE ENHANCEMENT:
 */
public class AddProductController implements Initializable {

    @FXML
    private Button addPartPageBtn;

    @FXML
    private Button startBtn;

    @FXML
    private Button addProduct_cancelBtn;

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
    private TableView<RowPartData> associatedParts_tableview = new TableView<RowPartData>();

    @FXML
    private TableColumn<RowPartData, Integer> associatedParts_tableView_col_inventoryLevel = new TableColumn<>("stock");

    @FXML
    private TableColumn<RowPartData, Integer> associatedParts_tableView_col_partID = new TableColumn<>("partID");

    @FXML
    private TableColumn<RowPartData, String> associatedParts_tableView_col_partName = new TableColumn<>("part_name");

    @FXML
    private TableColumn<RowPartData, BigDecimal> associatedParts_tableView_col_priceUnit = new TableColumn<>("price_unit");

    @FXML
    private TextField addProduct_searchPartInputField;

    @FXML
    private TextField addProduct_setInventoryLevel;

    @FXML
    private TextField addProduct_setMax;

    @FXML
    private TextField addProduct_setMin;

    @FXML
    private TextField addProduct_setPrice;

    @FXML
    private TextField addProduct_setProductName;

    int index = -1;

    ObservableList<PartData> partList = FXCollections.observableArrayList();
    ObservableList<RowPartData> associatedPartList = FXCollections.observableArrayList();

    //on click addBtn located on Add Product page, I check if a row has been selected,
    //retrieve data from parts table (choose part data), and insert it into associated part data table.
    /**
     * Void clickAddAssociatedPartBtn() method is used after the user selects one row from the upper table and clicks the add button.
     * event represents the event that triggers the action.
     * there is a validation to verify if one row from the part table has been selected.
     * All the data from the selected row is retrieved.
     * displayAssociatedPartDataTableView() method is called when the data is successfully retrieved from the parts table and inserted into the associated parts table.
     * error alert is shown when there is no selected row or when the part has been already associated to the product.
     * information alert is shown when the part data has been successfully associated to the current product.
     */
    @FXML
    void clickAddAssociatedPartBtn(ActionEvent event){
        //DatabaseConnection connectNow = new DatabaseConnection();
        //Connection connectDB = connectNow.getConnection();
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
                                //System.out.println("getSingleAssociatedPartName is: " + getSingleAssociatedPartName);
                                getSingleAssociatedPartStock = querySelectedPartToAssociateResult.getString("stock");
                                getSingleAssociatedPartPriceUnit = querySelectedPartToAssociateResult.getString("price_unit");
                                getSingleAssociatedPartMin = querySelectedPartToAssociateResult.getString("min");
                                getSingleAssociatedPartMax = querySelectedPartToAssociateResult.getString("max");
                                getSingleAssociatedPartMachineID = querySelectedPartToAssociateResult.getString("machineID");
                                //System.out.println("getSinglePartMachineID is: " + getSingleAssociatedPartMachineID);
                                getSingleAssociatedPartCompanyName = querySelectedPartToAssociateResult.getString("company_name");
                                //System.out.println("getSinglePartCompanyName is: " + getSingleAssociatedPartCompanyName);
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
                                alert.setContentText("New In House Part has been successfully added to EM Inventory Management System");
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
            alert.setContentText("Please select the data row part that you want to associate with your product.");
            alert.showAndWait();
        }
    }

    /**
     * Public void displayAssociatedPartDataTableView() method is called when the selected part has been successfully associated do our product.
     * The bottom associated parts table updates and shows the current associated parts data after adding a new associated part, unless an exception is caught.
     */
    public void displayAssociatedPartDataTableView() {
        associatedPartList.clear();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String associatedPartsViewQuery = "SELECT partID, part_name, stock, price_unit FROM associated_parts";
        try {
        Statement statement = connectDB.createStatement();
        ResultSet queryAssociatedPartsView = statement.executeQuery(associatedPartsViewQuery);

            while (queryAssociatedPartsView.next()) {

                //populate the observableList
                associatedPartList.add(new RowPartData(queryAssociatedPartsView.getInt("partID"),
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

            associatedParts_tableview.setItems(associatedPartList);

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     * Void clickSaveNewProductAndAssociatedParts() method is used to validate that none of the fields are empty, and that the correct data types have been used.
     * event represents the event that triggers the action (on click addProduct_saveBtn).
     * If all validations pass, then the validateProductName() method will be called; otherwise an error alert will be displayed.
     */
    @FXML
    void clickSaveNewProductAndAssociatedParts(ActionEvent event) {
        String min = addProduct_setMin.getText().trim();
        String max = addProduct_setMax.getText().trim();
        String stock = addProduct_setInventoryLevel.getText().trim();
        int min_check;
        int max_check;
        int stock_check;
        if(!addProduct_setProductName.getText().trim().isEmpty() || !addProduct_setInventoryLevel.getText().trim().isEmpty() || !addProduct_setPrice.getText().trim().isEmpty() || !addProduct_setMax.getText().trim().isEmpty() || !addProduct_setMin.getText().isEmpty()) {
            if(min.matches("\\d+") && max.matches("\\d+") && stock.matches("\\d+")){
                min_check = Integer.parseInt(min);
                max_check = Integer.parseInt(max);
                stock_check = Integer.parseInt(stock);
                //min validation --> min has to be >= 0
                if(min_check >= 0) {
                    //max validation --> max has to be > min
                    if(max_check > min_check) {
                        //inventory validation --> inventory level has to be >= than min, and <= than max
                        if(stock_check >= min_check && stock_check <= max_check){
                            //check if the part name is available or if it already exists using the validatePartName method
                            validateProductName();
                        } else{
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error message");
                            alert.setHeaderText(null);
                            alert.setContentText("Your Inventory value should be more or equal than your Min value, and less or equal than your Max value.");
                            alert.showAndWait();
                        }
                    } else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error message");
                        alert.setHeaderText(null);
                        alert.setContentText("Your Max value should be more than your Min value.");
                        alert.showAndWait();
                    }
                } else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Your Min value should be more or equal than 0.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Your must enter positive whole numbers only, for: Inventory Level, Min, and Max");
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
     * Public void validateProductName() method is used to validate that the new product name does not exist in the EM database.
     * When the validation passes, the method registerNewProduct() is called, unless an Exception is caught.
     * When the validation does not pass, an error alert will show up, and the user will be requested to use a different name for the new product.
     */
    public void validateProductName() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyProductName = "SELECT count(1) FROM products WHERE product_name = '" + addProduct_setProductName.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryUniqueProductNameResult = statement.executeQuery(verifyProductName);

            while(queryUniqueProductNameResult.next()) {
                if(queryUniqueProductNameResult.getInt(1) == 1) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Product Name already exists. Please try again.");
                    alert.showAndWait();
                } else {
                    registerNewProduct();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     * Public void registerNewProduct() method called after the product name validation is passed, and no exceptions were caught.
     * Once the data is inserted, the registerCurrentProductAssociatedParts() method will be called, if no exceptions are caught.
     * An information alert is displayed to notify that the new product has been successfully registered to the database.
     */
    public void registerNewProduct() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String productName = addProduct_setProductName.getText();
        String inventoryLevel = addProduct_setInventoryLevel.getText();
        String priceUnit = addProduct_setPrice.getText();
        String max= addProduct_setMax.getText();
        String min = addProduct_setMin.getText();

        String insertNewProductFields = "INSERT INTO products (product_name, stock, price_unit, min, max) VALUES ('";
        String insertNewProductValues = productName + "', '" + inventoryLevel + "', '" + priceUnit + "', '" + min + "', '" + max + "')";
        String insertNewProductToDB = insertNewProductFields + insertNewProductValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertNewProductToDB);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful Product Registration");
            alert.setHeaderText(null);
            alert.setContentText("New Product has been successfully added to EM Inventory Management System");
            alert.showAndWait();

            registerCurrentProductAssociatedParts();

            //After successfully saving a new product we call the registerCurrentProductAssociatedPart() method
//            registerCurrentProductAssociatedPart();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     * Public void registerCurrentProductAssociatedParts() method called after the new product is registered, and no exceptions were caught.
     * Once the data is inserted, the registerCurrentProductAssociatedParts() method will be called, if no exceptions are caught.
     * The productID is queried from the products table, and then the product and the associated parts are inserted into the products_associated_parts table.
     * An information alert is displayed to notify that the New Product and its PartsID have been successfully added to table products_associated_parts; and the addProductRedirectsToEMIMSHomePage() method is called.
     */
    //Last step on add New product functionality
    public void registerCurrentProductAssociatedParts() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String currentProductName = addProduct_setProductName.getText();
        System.out.println("the product name is: " + currentProductName);

        String currentProductID = "";
        String getCurrentProductIDQuery = "SELECT productID FROM products WHERE product_name = '" + currentProductName + "'";
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryCurrentProductIDResult = statement.executeQuery(getCurrentProductIDQuery);

            while(queryCurrentProductIDResult.next()) {
                currentProductID = queryCurrentProductIDResult.getString("productID");
                System.out.println("The current productID on line 374 is: " + currentProductID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        String currentAssociatedPartsIDs = "";
        String getCurrentAssociatedPartsIDsQuery = "SELECT partID FROM associated_parts";
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryCurrentAssociatedPartsIDsResult = statement.executeQuery(getCurrentAssociatedPartsIDsQuery);

            while(queryCurrentAssociatedPartsIDsResult.next()) {
                currentAssociatedPartsIDs = queryCurrentAssociatedPartsIDsResult.getString("partID");
                System.out.println("The current partsID on line 391 is: " + currentAssociatedPartsIDs);

                String insertPartsPerProductFields = "INSERT INTO products_associated_parts (productID, partID) VALUES ('";
                String insertPartsPerProductValues = currentProductID + "', '" + currentAssociatedPartsIDs + "') ";

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
            alert.setTitle("Successful Product and PartsID data association");
            alert.setHeaderText(null);
            alert.setContentText("New Product and its PartsID have been successfully added to table products_associated_parts at the EM Inventory Management System");
            alert.showAndWait();

            //After successfully saving a new product we redirect to the home_page and are able to see the updated data table
            addProductRedirectsToEMIMSHomePage();

        } catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Void deleteSelectedAssociatedPart() method is used to delete the associated part from the selected row on the products_associated_parts table.
     * @param event represents the event that triggers the action.
     * A confirmation alert is displayed, if the user clicks ok then the part will be unassociated from the product, and the products_associated_parts table will be updated, unless an Exception is caught. If the user clicks cancel, then the action is aborted.
     * An error alert is displayed when no row has been selected.
     */
    @FXML
    void deleteSelectedAssociatedPart(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        index = associatedParts_tableview.getSelectionModel().getSelectedIndex();
//        parts_tableView.getItems().remove(selectedItem);

        if(index > -1) {
            PreparedStatement pst;
            RowPartData selectedItem = associatedParts_tableview.getSelectionModel().getSelectedItem();

            String deleteSelectedAssociatedPart = "DELETE FROM associated_parts WHERE partID = ?";

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

    }

    /**
     * Void addProduct_cancelBtnAction() method is used to go back to the landing page while still working on adding a new product to the database.
     * e represents the event that triggers the action.
     * A confirmation alert will be shown when the user clicks the cancel button.
     * The alert confirms that changes haven't been saved and takes us to homepage.
     * If the user clicks OK, then the addProduct Page will be hidden, and the user will be redirected to the landing page, unless an exception is caught. If the user press cancel, then it will return to the addProduct page to keep working on the data part input.
     */
    @FXML
    void addProduct_cancelBtnAction(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("New product hasn't been saved yet. Are you sure that you want to leave the window?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)) {
//              go back to the landing page by doing ...
                addProduct_cancelBtn.getScene().getWindow().hide();
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

    /**
     * Void addProduct_addPartBtnAction() method is used to call the addProductRedirectsToAddPartPage();, unless an exception is caught.
     * @param event represents the event that triggers the action.
     * A confirmation alert is displayed.
     */
    @FXML
    void addProduct_addPartBtnAction(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("New product hasn't been saved yet. Are you sure that you want to switch to the Add Part window?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)) {
                addProductRedirectsToAddPartPage();
            } else {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     * When the modify part button is clicked, an error alert will be displayed.
     * e represents the event that triggers the action.
     */
    @FXML
    void addProduct_modifyPartBtnAction_Error(ActionEvent event) {
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

    /**
     * When the modify product button is clicked, an error alert will be displayed.
     * event represents the event that triggers the action.
     */
    @FXML
    void addProduct_modifyProductBtnAction_Error(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please go to Home and select the product that you want to modify.");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     * Void btnSearchPart() method is used to find a part row by typing information in the input field and clicking the search button.
     * e represents the event that triggers the action.
     * @exception SQLException if a database error or other errors occur.
     * @see SQLException
     */
    @FXML
    void btnSearchPart(MouseEvent event) {
        String text = addProduct_searchPartInputField.getText();
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

                    addProduct_searchPartInputField.clear();
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
     * Void keyReleaseSearchPart() method is used to find a part row by typing information in the input field.
     * e represents the event that triggers the action.
     * @exception SQLException if a database error or other errors occur.
     * @see SQLException
     */
    @FXML
    void keyReleaseSearchPart(KeyEvent event) {
        String text = addProduct_searchPartInputField.getText();
//        System.out.println(text);

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

//SIDE MENU
    /**
     * Public void addProductRedirectsToEMIMSHomePage() method called after the new product is successfully registered into the database, and no exceptions were caught.
     * The add Product page is hided, and the user is redirected to the homepage, where it can see the new part displaying on the parts table.
     * @throws IOException if an input or output error occurs
     * @see IOException
     */
    public void addProductRedirectsToEMIMSHomePage() throws IOException {
        startBtn.getScene().getWindow().hide();

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
     * Void addProductRedirectsToAddPartPage() method is called by the addProduct_addPartBtnAction; and it is used to open Add Part Page, unless an exception is caught.
     * @throws IOException if an input or output error occurs
     * @see IOException
     */
    public void addProductRedirectsToAddPartPage() throws IOException {
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
     * Public void initialize() method called to initialize a controller after its root element has been completely processed.
     * @parameter url is used to resolve relative paths for the root object. It is null if the url is not known.
     * @parameter rb is used to localize the root object, and it is null if the root object is not located.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        //SQL Query - executed in the backend database
        String partsViewQuery = "SELECT partID, part_name, stock, price_unit FROM parts";
        String clearAssociatedPartsTable = "DELETE FROM associated_parts";
        //**new
        String associatedPartsViewQuery = "SELECT partID, part_name, stock, price_unit FROM associated_parts";
        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(clearAssociatedPartsTable);

        } catch(SQLException e) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            e.getCause();
        }
        try {
            Statement statement = connectDB.createStatement();
//            statement.executeUpdate(clearAssociatedPartsTable);
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
        try {
            Statement statement = connectDB.createStatement();
//            statement.executeUpdate(clearAssociatedPartsTable);
            ResultSet queryAssociatedPartsOutput = statement.executeQuery(associatedPartsViewQuery);

            while (queryAssociatedPartsOutput.next()) {

                //populate the observableList
                associatedPartList.add(new RowPartData(queryAssociatedPartsOutput.getInt("partID"),
                        queryAssociatedPartsOutput.getString("part_name"),
                        queryAssociatedPartsOutput.getInt("stock"),
                        queryAssociatedPartsOutput.getBigDecimal("price_unit")));
            }


            //PropertyValueFactory corresponds to the new PartData fields
            //the table column is the one we annotate above
            associatedParts_tableView_col_partID.setCellValueFactory(new PropertyValueFactory<>("partID"));
            associatedParts_tableView_col_partName.setCellValueFactory(new PropertyValueFactory<>("part_name"));
            associatedParts_tableView_col_inventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
            associatedParts_tableView_col_priceUnit.setCellValueFactory(new PropertyValueFactory<>("price_unit"));

            associatedParts_tableview.setItems(associatedPartList);

        } catch(SQLException e) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            e.getCause();
        }
    }
}
