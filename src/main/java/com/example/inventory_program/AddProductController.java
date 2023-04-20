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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddProductController implements Initializable {

    @FXML
    private Button addPartPageBtn;

    @FXML
    private StackPane addProduct_page;

    @FXML
    private Button addProduct_closeBtn;

    @FXML
    private Button modifyPartPageBtn;

    @FXML
    private Button modifyProductPageBtn;

    @FXML
    private Button settingsBtn;

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
    private Button addProduct_addAssociatedPartBtn;

    @FXML
    private TextField addProduct_productIDTextField;

    @FXML
    private Button addProduct_removeAssociatedPartBtn;

    @FXML
    private Button addProduct_saveBtn;

    @FXML
    private Button addProduct_searchPartBtn;

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


    @FXML
    public void clickAddAssociatedPartBtn (ActionEvent event){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
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

            PartData selectedItem = parts_tableView.getSelectionModel().getSelectedItem();

            String associateSelectedPartToNewProduct = "SELECT * FROM parts WHERE partID = '" + selectedItem.getPartID() + "'";

            try {
                Statement statement = connectDB.createStatement();
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
                    Statement statement = connectDB.createStatement();
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

            } else if(getSingleAssociatedPartMachineID == null){
                String insertNewOutsourcedAssociatedPartFields = "INSERT INTO associated_parts (partID, part_name, stock, price_unit, min, max, company_name) VALUES ('";
                String insertNewOutsourcedAssociatedPartValues = getSingleAssociatedPartID + "', '" + getSingleAssociatedPartName + "', '" + getSingleAssociatedPartStock + "', '" + getSingleAssociatedPartPriceUnit + "', '" + getSingleAssociatedPartMin + "', '" + getSingleAssociatedPartMax + "', '" + getSingleAssociatedPartCompanyName + "')";
                String insertNewOutsourcedAssociatedPartToDB_associated_partsTable = insertNewOutsourcedAssociatedPartFields + insertNewOutsourcedAssociatedPartValues;

                try {
                    Statement statement = connectDB.createStatement();
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
            } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the data row that you want to delete.");
            alert.showAndWait();
            }
        }
    }

    public void displayAssociatedPartDataTableView() {
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

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful Outsourced Part Registration");
            alert.setHeaderText(null);
            alert.setContentText("New Outsourced Part has been successfully added to EM Inventory Management System");
            alert.showAndWait();

            //After successfully saving a new part we redirect to the home_page and are able to see the updated data table
    //                addPartRedirectsToEMIMSHomePage();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }


    public void addProduct_cancelBtnAction(ActionEvent event) {
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

//SIDE MENU
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

    public void addProductRedirectsToAddPartPage (ActionEvent event) throws IOException {
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

    public void addProductRedirectsToModifyProductPage (ActionEvent event) throws IOException {
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

    public void addProductRedirectsToModifyPartPage (ActionEvent event) throws IOException {
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

        //SQL Query - executed in the backend database
        String partsViewQuery = "SELECT partID, part_name, stock, price_unit FROM parts";
//        String clearAssociatedPartsTable = "DELETE FROM associated_parts";
        //**new
        String associatedPartsViewQuery = "SELECT partID, part_name, stock, price_unit FROM associated_parts";
//        try {
//            Statement statement = connectDB.createStatement();
//            statement.executeUpdate(clearAssociatedPartsTable);
//
//        } catch(SQLException e) {
//            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, e);
//            e.printStackTrace();
//            e.getCause();
//        }
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
