package com.example.inventory_program;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {

    @FXML
    private Button addProductPageBtn;

    @FXML
    private Button startBtn;

    @FXML
    private Label displayCompanyOrMachineLabel;

    @FXML
    private RadioButton inHouseRadioBtn;

    @FXML
    private TextField inputCompanyOrMachineInputField;

    @FXML
    private RadioButton outsourcedRadioBtn;

    @FXML
    private TextField addPart_setPartName;

    @FXML
    private TextField addPart_setInventoryLevel;

    @FXML
    private TextField addPart_setMax;

    @FXML
    private TextField addPart_setMin;

    @FXML
    private TextField addPart_setPriceUnit;

    @FXML
    private Button addPart_cancelBtn;

    /**
     * Toggle Group - radio buttons functionality.
     * e represents the event that triggers the action.
     */
    @FXML
    void displayMachineIDOrCompanyName(ActionEvent event) {
        if(inHouseRadioBtn.isSelected()) {
            displayCompanyOrMachineLabel.setText("Machine ID:");
            inputCompanyOrMachineInputField.setPromptText("machine ID");
        } else if(outsourcedRadioBtn.isSelected()) {
            displayCompanyOrMachineLabel.setText("Company Name:");
            inputCompanyOrMachineInputField.setPromptText("company name");
        }
    }

    /**
     * Void clickSavePartBtn() method is used to validate that none of the fields are empty, and that the correct data types have been used.
     * e represents the event that triggers the action.
     * If all validations pass, then the validatePartName() method will be called; otherwise an error alert will be displayed.
     */
    @FXML
    void clickSavePartBtn(ActionEvent event) {

        //retrieve variables for max, min, and inventory validation.
        String min = addPart_setMin.getText().trim();
        String max = addPart_setMax.getText().trim();
        String stock = addPart_setInventoryLevel.getText().trim();
        int min_check;
        int max_check;
        int stock_check;

        //Part Category Selection Validation - No null Accepted ~ it has to select inHouse or Outsourced
        if(inHouseRadioBtn.isSelected() || outsourcedRadioBtn.isSelected()) {
            //Not null accepted Input validation checks that none of the fields are blank or empty...
            if(!addPart_setPartName.getText().trim().isEmpty() || !addPart_setInventoryLevel.getText().trim().isEmpty() || !addPart_setPriceUnit.getText().trim().isEmpty() || !addPart_setMax.getText().trim().isEmpty() || !addPart_setMin.getText().trim().isEmpty() || !inputCompanyOrMachineInputField.getText().trim().isEmpty()) {
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
                                validatePartName();
                            } else{
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error message");
                                alert.setHeaderText(null);
                                alert.setContentText("Your Inventory value should be more or equal than your Min value, and less or equal than your Max value.");
                                alert.showAndWait();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error message");
                            alert.setHeaderText(null);
                            alert.setContentText("Your Max value should be more than your Min value.");
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error message");
                        alert.setHeaderText(null);
                        alert.setContentText("Your Min value should be more or equal than 0.");
                        alert.showAndWait();
                    }
                } else{
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
        } else {
            //alert error when part category hasn't been selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the Part Category: In-House or Outsourced.");
            alert.showAndWait();
        }
    };

    /**
     * Void addPart_cancelBtnAction() method is used to go back to the landing page while still working on adding a new part to the database.
     * e represents the event that triggers the action.
     * A confirmation alert will be shown when the user clicks the cancel button. If the user clicks OK, then the addPart Page will be hidden, and the user will be redirected to the landing page, unless an exception is caught. If the user press cancel, then it will return to the addPart page to keep working on the data part input.
     */
    @FXML
    void addPart_cancelBtnAction(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("New part hasn't been saved yet. Are you sure that you want to leave the window?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)) {
//              go back to the landing page by doing ...
                addPart_cancelBtn.getScene().getWindow().hide();
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
     * When the modify part button is clicked, an error alert will be displayed.
     * e represents the event that triggers the action.
     */
    @FXML
    void addPart_modifyPartBtnAction_Error(ActionEvent event) {
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
     * e represents the event that triggers the action.
     */
    @FXML
    void addPart_modifyProductBtnAction_Error(ActionEvent event) {
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
     * Void addPart_addProductBtnAction() method is used to call the addPartRedirectsToAddProductPage(), unless an exception is caught.
     * e represents the event that triggers the action.
     * A confirmation alert is displayed.
     */
    @FXML
    void addPart_addProductBtnAction(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("New part hasn't been saved yet. Are you sure that you want to switch to the Add Product window?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)) {
                addPartRedirectsToAddProductPage ();
            } else {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     * Public void validatePartName() method is used to validate that the new part name does not exist in the EM database.
     * When the validation passes, the method registerNewPart() is called, unless an Exception is caught.
     * When the validation does not pass, an error alert will show up, and the user will be requested to use a different name for the new part.
     */
    public void validatePartName() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyPartName = "SELECT count(1) FROM parts WHERE part_name = '" + addPart_setPartName.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryUniquePartNameResult = statement.executeQuery(verifyPartName);

            while(queryUniquePartNameResult.next()) {
                if(queryUniquePartNameResult.getInt(1) == 1) {
                    //                    messageLabel.setText("Part Name already exists. Please try again.");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Part Name already exists. Please try again.");
                    alert.showAndWait();
                } else {
                    registerNewPart();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    };

    /**
     * Public void registerNewPart() method called after the part name validation is passed, and no exceptions were caught.
     * Once the data is inserted, the addPartRedirectsToEMIMSHomePage() method will be called, if no exceptions are caught.
     * An information alert is displayed to notify that the new part has been successfully registered to the database.
     */
    public void registerNewPart() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String partName = addPart_setPartName.getText();
        String inventoryLevel = addPart_setInventoryLevel.getText();
        String priceUnit = addPart_setPriceUnit.getText();
        String max= addPart_setMax.getText();
        String min = addPart_setMin.getText();


        if(inHouseRadioBtn.isSelected()) {
            String machineId = inputCompanyOrMachineInputField.getText();
            String insertNewInHousePartFields = "INSERT INTO parts (part_name, stock, price_unit, min, max, machineID) VALUES ('";
            String insertNewInHousePartValues = partName + "', '" + inventoryLevel + "', '" + priceUnit + "', '" + min + "', '" + max + "', '" + machineId + "')";
            String insertNewInHousePartToDB = insertNewInHousePartFields + insertNewInHousePartValues;

            try {
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(insertNewInHousePartToDB);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful In-House Part Registration");
                alert.setHeaderText(null);
                alert.setContentText("New In House Part has been successfully added to EM Inventory Management System");
                alert.showAndWait();

                //After successfully saving a new part we redirect to the home_page and are able to see the updated data table
                addPartRedirectsToEMIMSHomePage();

            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }

        } else if(outsourcedRadioBtn.isSelected()){
            String companyName = inputCompanyOrMachineInputField.getText();
            String insertNewOutsourcedPartFields = "INSERT INTO parts (part_name, stock, price_unit, min, max, company_name) VALUES ('";
            String insertNewOutsourcedPartValues = partName + "', '" + inventoryLevel + "', '" + priceUnit + "', '" + min + "', '" + max + "', '" + companyName + "')";
            String insertNewOutsourcedPartToDB = insertNewOutsourcedPartFields + insertNewOutsourcedPartValues;

            try {
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(insertNewOutsourcedPartToDB);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful Outsourced Part Registration");
                alert.setHeaderText(null);
                alert.setContentText("New Outsourced Part has been successfully added to EM Inventory Management System");
                alert.showAndWait();

                //After successfully saving a new part we redirect to the home_page and are able to see the updated data table
                addPartRedirectsToEMIMSHomePage();

            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        }
    };

    //SIDE MENU
    /**
     * Public void addPartRedirectsToEMIMSHomePage() method called after the new part is successfully registered into the database, and no exceptions were caught.
     * The add Product page is hided, and the user is redirected to the homepage, where it can see the new part displaying on the parts table.
     */
    public void addPartRedirectsToEMIMSHomePage() throws IOException {
        startBtn.getScene().getWindow().hide();

        //create new stage
        Stage ppMainWindow = new Stage();
        ppMainWindow.setTitle("EM Inventory Management System");

        //create view for FXML
        FXMLLoader ppMainLoader = new FXMLLoader(getClass().getResource("home_page-parts&products.fxml"));

        //set view in ppMainWindow
        ppMainWindow.setScene(new Scene(ppMainLoader.load(), 800, 400));

        //launch
        ppMainWindow.show();
    }

    /**
     * Void addPartRedirectsToAddProductPage() method is called by the addPart_addProductBtnAction; and it is used to open Add Product Page, unless an exception is caught.
     * e represents the event that triggers the action.
     */
    public void addPartRedirectsToAddProductPage() throws IOException {
        addProductPageBtn.getScene().getWindow().hide();
        //create new stage
        Stage addProductPageWindow = new Stage();
        addProductPageWindow.setTitle("Add Product - EM Inventory Management System");

        //create view for FXML
        FXMLLoader addProductPageLoader = new FXMLLoader(getClass().getResource("addProduct_page.fxml"));

        //set view in ppMainWindow
        addProductPageWindow.setScene(new Scene(addProductPageLoader.load(), 800, 610));

        //launch
        addProductPageWindow.show();
    }


    /**
     * Public void initialize() method called to initialize a controller after its root element has been completely processed.
     * @parameter url is used to resolve relative paths for the root object. It is null if the url is not known.
     * @parameter rb is used to localize the root object, and it is null if the root object is not located.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inHouseRadioBtn.setSelected(true);
    }
}
