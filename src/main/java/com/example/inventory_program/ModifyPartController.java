package com.example.inventory_program;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Evelyn G Morrow.
 * @version 1.0.
 * Public class ModifyPartController is used to retrieve and display the data row after selecting the part that the user needs to modify from the parts table, and click the modify part button.
 * after the user successfully signs in, as well as to manage some functionality such as delete, search, etc.
 */
public class ModifyPartController implements Initializable {

    HelloController helloController;
    PartData partData;
    PartsAndProductsInventory partsAndProductsInventory;

    @FXML
    private Button addPartPageBtn;

    @FXML
    private Button addProductPageBtn;

    @FXML
    private Button modifyPart_cancelBtn;

    @FXML
    private StackPane modifyPart_page;

    @FXML
    private Button modifyProductPageBtn;

    @FXML
    private Button settingsBtn;

    @FXML
    private Button startBtn;

    @FXML
    private Button modifyPart_closeBtn;

    @FXML
    private Button modifyPart_saveBtn;

    @FXML
    private ToggleGroup modifyPartSelectInHouseOutsourcedToggleGroup;

    @FXML
    private RadioButton modifyPartOutsourcedRadioBtn;

    @FXML
    private RadioButton modifyPartInHouseRadioBtn;

    @FXML
    private TextField modifyPart_partIDTextField;

    @FXML
    private TextField modifyPart_setPartName;

    @FXML
    private TextField modifyPart_setInventoryLevel;

    @FXML
    private TextField modifyPart_setMax;

    @FXML
    private TextField modifyPart_setMin;

    @FXML
    private TextField modifyPart_setPriceUnit;

    @FXML
    private TextField modifyPart_inputCompanyOrMachineInputField;

    @FXML
    private Label modifyPart_displayCompanyOrMachineLabel;

    /**
     * private final variables are not accessible outside the class.
     * private final variables values are final (no changes allowed) once the variable is initialized.
     */
    private final String getSinglePartID;
    private final String getSinglePartName;
    private final String getSinglePartStock;
    private final String getSinglePartPriceUnit;
    private final String getSinglePartMin;
    private final String getSinglePartMax;
    private final String getSinglePartMachineID;
    private final String getSinglePartCompanyName;

    //display info from the row selected to be modified/edited at the home_page
    //public void showSelectedPartDataInformation(String name, String age) {
        //nameScene2.setText(name);
        //ageScene2.setText(age);
    //};

    /**
     * Public ModifyPartController accepts:
     * @param partsAndProductsInventory parts and products inventory parameters and initializes the private final String getPartsAndProductsInventory variable.
     * @param partData partData parameter and initializes the private final String getSinglePartData variable.
     * @param getSinglePartID getSinglePartID parameter and initializes the private final String getSinglePartID variable.
     * @param getSinglePartName getSinglePartName parameter and initializes the private final String getSinglePartName;
     * @param getSinglePartStock getSinglePartStock parameter and
     * @param getSinglePartPriceUnit
     * @param getSinglePartMin
     * @param getSinglePartMax
     * @param getSinglePartMachineID
     * @param getSinglePartCompanyName
     *
     */
    public ModifyPartController(PartsAndProductsInventory partsAndProductsInventory, PartData partData, String getSinglePartID, String getSinglePartName, String getSinglePartStock, String getSinglePartPriceUnit, String getSinglePartMin, String getSinglePartMax, String getSinglePartMachineID, String getSinglePartCompanyName) {
        this.partsAndProductsInventory = partsAndProductsInventory;
        this.partData = partData;
        this.getSinglePartID = getSinglePartID;
        this.getSinglePartName = getSinglePartName;
        this.getSinglePartStock= getSinglePartStock;
        this.getSinglePartPriceUnit= getSinglePartPriceUnit;
        this.getSinglePartMin= getSinglePartMin;
        this.getSinglePartMax= getSinglePartMax;
        this.getSinglePartMachineID= getSinglePartMachineID;
        this.getSinglePartCompanyName= getSinglePartCompanyName;
    }

    public void displayMachineIDOrCompanyName_modifyPartPage(ActionEvent event) {
        if(modifyPartInHouseRadioBtn.isSelected()) {
            modifyPart_displayCompanyOrMachineLabel.setText("Machine ID:");
            modifyPart_inputCompanyOrMachineInputField.setText(getSinglePartMachineID);
        } else if(modifyPartOutsourcedRadioBtn.isSelected()) {
            modifyPart_displayCompanyOrMachineLabel.setText("Company Name:");
            modifyPart_inputCompanyOrMachineInputField.setText(getSinglePartCompanyName);
        }
    }

    @FXML
    public void clickSaveUpdatedPartBtn(ActionEvent event) {
        //retrieve variables for max, min, and inventory validation.
        String min = modifyPart_setMin.getText().trim();
        String max = modifyPart_setMax.getText().trim();
        String stock = modifyPart_setInventoryLevel.getText().trim();
        int min_check;
        int max_check;
        int stock_check;

        //Part Category Selection Validation - No null Accepted ~ it has to select inHouse or Outsourced
        if(modifyPartInHouseRadioBtn.isSelected() || modifyPartOutsourcedRadioBtn.isSelected()) {
            //Not null accepted Input validation checks that none of the fields are blank or empty...
            if(!modifyPart_setPartName.getText().trim().isEmpty() || !modifyPart_setInventoryLevel.getText().trim().isEmpty() || modifyPart_setPriceUnit.getText().trim().isEmpty() || !modifyPart_setMax.getText().trim().isEmpty() || !modifyPart_setMin.getText().trim().isEmpty() || !modifyPart_inputCompanyOrMachineInputField.getText().trim().isEmpty()) {
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
                                validateUpdatedPartNameAndPartID();
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
    }

    public void validateUpdatedPartNameAndPartID() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyUpdatedPartNameMatchesPartID = "SELECT count(1) FROM parts WHERE part_name = '" + modifyPart_setPartName.getText() + "' AND partID = '" + modifyPart_partIDTextField.getText() +"'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryUniqueUpdatedPartNameAndPartIDResult = statement.executeQuery(verifyUpdatedPartNameMatchesPartID);

            while(queryUniqueUpdatedPartNameAndPartIDResult.next()) {
                //if updated part name matches with the ID... call the UpdatePart(); method
                if(queryUniqueUpdatedPartNameAndPartIDResult.getInt(1) == 1) {
                    //                    messageLabel.setText("Part Name already exists. Please try again.");
                    UpdatePart();
                } else {
                    //if updated part name does not match with the ID... call the verifyIfPartNameAlreadyExists(); method
                  verifyIfPartNameAlreadyExists();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    };

    public void verifyIfPartNameAlreadyExists() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyPartNameAvailability = "SELECT count(1) FROM parts WHERE part_name = '" + modifyPart_setPartName.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryUniqueUpdatedPartNameResult = statement.executeQuery(verifyPartNameAvailability);

            while(queryUniqueUpdatedPartNameResult.next()) {
                //if updated part name already exists in our DB, but it does not match with the current ID ... show an error alert
                if(queryUniqueUpdatedPartNameResult.getInt(1) == 1) {
                    //                    messageLabel.setText("Part Name already exists. Please try again.");

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Part Name already exists and has a different ID. Please use a different part name or look for the part name in the dashboard, select the row, and click on the Modify button.");
                    alert.showAndWait();
                } else {
                    //if updated part name does not exist in our DB, and it does not match with the current ID... then call the call the UpdatePart(); method.
                    UpdatePart();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    };

    public void UpdatePart() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String modifyPage_partID = modifyPart_partIDTextField.getText();
        String updatedPartName = modifyPart_setPartName.getText();
        String updatedPartInventoryLevel = modifyPart_setInventoryLevel.getText();
        String updatedPartPriceUnit = modifyPart_setPriceUnit.getText();
        String updatedPartMax= modifyPart_setMax.getText();
        String updatedPartMin = modifyPart_setMin.getText();


        if(modifyPartInHouseRadioBtn.isSelected()) {
            String updatedMachineId = modifyPart_inputCompanyOrMachineInputField.getText();
            String updateCompanyNameToNull = "UPDATE parts SET company_name = NULL WHERE partID = '" + getSinglePartID + "' ";
            String updateInHousePartInDB = "UPDATE parts SET part_name = '" + updatedPartName + "', stock = '" + updatedPartInventoryLevel + "', price_unit = '" + updatedPartPriceUnit + "', min = '" + updatedPartMin + "', max = '" + updatedPartMax + "', machineID = '" + updatedMachineId + "' WHERE partID = '" + modifyPage_partID + "' ";

            try {
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(updateInHousePartInDB);
                statement.executeUpdate(updateCompanyNameToNull);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful In-House Part Update");
                alert.setHeaderText(null);
                alert.setContentText("In House Part has been successfully updated in EM Inventory Management System");
                alert.showAndWait();

                //After successfully saving a new part we redirect to the home_page and are able to see the updated data table
                modifyPartRedirectsToEMIMSHomePage();

            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }

        } else if(modifyPartOutsourcedRadioBtn.isSelected()){
            String updatedCompanyName = modifyPart_inputCompanyOrMachineInputField.getText();
            String updateMachineIDToNull = "UPDATE parts SET machineID = NULL WHERE partID = '" + getSinglePartID + "' ";
            String updateOutsourcedPartInDB = "UPDATE parts SET part_name = '" + updatedPartName + "', stock = '" + updatedPartInventoryLevel + "', price_unit = '" + updatedPartPriceUnit + "', min = '" + updatedPartMin + "', max = '" + updatedPartMax + "',  company_name = '" + updatedCompanyName + "' WHERE partID = '" + modifyPage_partID + "' ";

            try {
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(updateMachineIDToNull);
                statement.executeUpdate(updateOutsourcedPartInDB);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful Outsourced Part Update");
                alert.setHeaderText(null);
                alert.setContentText("Outsourced Part has been successfully updated in EM Inventory Management System");
                alert.showAndWait();

                //After successfully saving the updated part we redirect to the home_page and are able to see the updated data table
                modifyPartRedirectsToEMIMSHomePage();

            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        }
    };

    /**
     * Void modifyPart_cancelBtnAction() method is used to go back to the landing page while still working on adding a new part to the database.
     * @param event represents the event that triggers the action.
     * A confirmation alert will be shown when the user clicks the cancel button. If the user clicks OK, then the addPart Page will be hidden, and the user will be redirected to the landing page, unless an exception is caught. If the user press cancel, then it will return to the addPart page to keep working on the data part input.
     */
    public void modifyPart_cancelBtnAction(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Updated part hasn't been saved yet. Are you sure that you want to leave the window?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)) {
              //go back to the landing page by doing ...
                modifyPart_cancelBtn.getScene().getWindow().hide();
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
     * Void modifyPart_addProductBtnAction() method is used to call the modifyPartRedirectsToAddProductPage(), unless an exception is caught.
     * @param event represents the event that triggers the action.
     * A confirmation alert is displayed.
     */
    public void modifyPart_addProductBtnAction(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Updated part hasn't been saved yet. Are you sure that you want to switch to the Add Product window?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)) {
                modifyPartRedirectsToAddProductPage();
            } else {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     * When the modify product button is clicked, an error alert will be displayed.
     * @param event represents the event that triggers the action.
     */
    public void modifyPart_modifyProductBtnAction_Error(ActionEvent event) {
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
     * Void modifyPart_addPartBtnAction() method is used to call the modifyPartRedirectsToAddPartPage();, unless an exception is caught.
     * @param event represents the event that triggers the action.
     * A confirmation alert is displayed.
     */
    public void modifyPart_addPartBtnAction(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Updated part hasn't been saved yet. Are you sure that you want to switch to the Add Part window?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)) {
                modifyPartRedirectsToAddPartPage();
            } else {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    //SIDE MENU
    /**
     * Public void modifyPartRedirectsToEMIMSHomePage() method called after successfully saving the updated new part into the database, and no exceptions were caught.
     * The Modify Part page is hided, and the user is redirected to the homepage, where it can see the updated part displaying on the parts table.
     * @throws IOException if an input or output error occurs
     * @see IOException
     */
    public void modifyPartRedirectsToEMIMSHomePage() throws IOException {
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
     * Void modifyPartRedirectsToAddProductPage() method is called by the modifyPart_addProductBtnAction; and it is used to open Add Product Page, unless an exception is caught.
     * @throws IOException if an input or output error occurs
     * @see IOException
     */
    public void modifyPartRedirectsToAddProductPage () throws IOException {
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
     * Void modifyPartRedirectsToAddPartPage() method is called by the modifyPart_addPartBtnAction; and it is used to open Add Part Page, unless an exception is caught.
     * @throws IOException if an input or output error occurs
     * @see IOException
     */
    public void modifyPartRedirectsToAddPartPage() throws IOException {
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
     * @param url is used to resolve relative paths for the root object. It is null if the url is not known.
     * @param rb is used to localize the root object, and it is null if the root object is not located.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //modifyPart_setPartName.setText("hellooooo");

        modifyPart_displayCompanyOrMachineLabel.setText("Machine ID:");

        modifyPart_partIDTextField.setText(getSinglePartID);
        modifyPart_setPartName.setText(getSinglePartName);
        modifyPart_setInventoryLevel.setText(getSinglePartStock);
        modifyPart_setPriceUnit.setText(getSinglePartPriceUnit);
        modifyPart_setMin.setText(getSinglePartMin);
        modifyPart_setMax.setText(getSinglePartMax);

        //if machineID DOES exist && companyName DOES NOT exist t --> we have a in-house item so...
        if(getSinglePartMachineID != null && !getSinglePartMachineID.trim().isEmpty()) {
            System.out.println("part machine -In Home data is in the database!!");
            //we are displaying the in-house radio button selected, the machineID label, and the machineID in the inputfield
            modifyPartInHouseRadioBtn.setSelected(true);
            modifyPart_displayCompanyOrMachineLabel.setText("Machine ID:");
            modifyPart_inputCompanyOrMachineInputField.setText(getSinglePartMachineID);
            //if company_name DOES exist && machineID DOES NOT EXIST--> we have an outsourced item so...
        } else if (getSinglePartCompanyName != null && !getSinglePartCompanyName.trim().isEmpty()){
            System.out.println("company name - outsourced data is in the database!!");
            //we are displaying the outsourced radio button selected, the companyName label, and the companyName in the inputfield
            modifyPartOutsourcedRadioBtn.setSelected(true);
            modifyPart_displayCompanyOrMachineLabel.setText("Company Name:");
            modifyPart_inputCompanyOrMachineInputField.setText(getSinglePartCompanyName);
        }
        //if machineID DOES NOT EXIST && companyName DOES NOT EXIST --> user was already playing with the radiobuttons so...
        //display in-house radio button as SELECTED, the machineID label, and inputfield="", and machineID in the promptText



        //on change
        //if the in-house radio button is selected && machineID exists...
        //display the in-house radio button selected, the machineID label, and the machineID in the promptText, and inputfield="" ~OR~the machineID in the inputfield~

        //if outsourced radio button is selected && company_name exists...
        //display the outsourced radio button selected, the companyName label, and the companyName in the promptText, and the inputfield="" ~OR~the companyName in the inputfield~

        //if the in-house radio button is selected && machineID DOES NOT exist && companyName DOES EXIST...
        //set getSingleCompanyName = ""; ???
        //display the in-house radio button selected, the machineID label, the machineID word as promptText, and inputField=""

        //if the outsourced radio button is selected && companyName DOES NOT EXIST && machineID DOES EXIST...
        //set getSingleMachineID = ""; ??
        //display the outsourced radio button selected, the companyName label, the company name phrase as promptText, and inputfield = ""


    }

}
