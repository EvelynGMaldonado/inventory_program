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
//                    statement.executeUpdate(finalAssociation);
                    ResultSet queryAssociatedPartsByProductsOutput = statement.executeQuery(associatedPartsByProductsViewQuery);

                    while(queryAssociatedPartsByProductsOutput.next()) {
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
            }

        } catch(SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

//        String associatedPartsByProductsViewQuery = "SELECT partID, part_name, stock, price_unit FROM parts WHERE partID = '" + allPartsIDByProduct + "' ";
//        String allDataFromAssociatedParts = "";
//        String allPartsNamesByProduct = "";
//        try {
//            Statement statement = connectDB.createStatement();
//            ResultSet queryAssociatedPartsByProductsOutput = statement.executeQuery(associatedPartsByProductsViewQuery);
//
//            while (queryAssociatedPartsByProductsOutput.next()) {
//                allPartsNamesByProduct = queryAssociatedPartsByProductsOutput.getString("part_name");
//                System.out.println("The current parts_names on line 286 is: " + allPartsNamesByProduct);
//
//                //populate the observableList
//                associatedPartsByProductList.add(new ProductPartsData(queryAssociatedPartsByProductsOutput.getInt("partID"),
//                        queryAssociatedPartsByProductsOutput.getString("part_name"),
//                        queryAssociatedPartsByProductsOutput.getInt("stock"),
//                        queryAssociatedPartsByProductsOutput.getBigDecimal("price_unit")));
//            }
//
//
//            //PropertyValueFactory corresponds to the new PartData fields
//            //the table column is the one we annotate above
//            associatedParts_tableView_col_partID.setCellValueFactory(new PropertyValueFactory<>("partID"));
//            associatedParts_tableView_col_partName.setCellValueFactory(new PropertyValueFactory<>("part_name"));
//            associatedParts_tableView_col_inventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
//            associatedParts_tableView_col_priceUnit.setCellValueFactory(new PropertyValueFactory<>("price_unit"));
//
//            associatedParts_tableview.setItems(associatedPartsByProductList);
//
//        } catch(SQLException e) {
//            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, e);
//            e.printStackTrace();
//            e.getCause();
//        }
    }


}
