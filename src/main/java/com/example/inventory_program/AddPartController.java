package com.example.inventory_program;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
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
    private StackPane addPart_page;

    @FXML
    private Button addProductPageBtn;

    @FXML
    private Button modifyProduct_closeBtn;

    @FXML
    private Button modifyPartPageBtn;

    @FXML
    private Button modifyProductPageBtn;

    @FXML
    private Button settingsBtn;

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
    private ToggleGroup selectInHouseOutsourcedToggleGroup;

    @FXML
    private TextField addPart_partIDTextField;

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
    private Button addPart_saveBtn;

    @FXML
    private Button addPart_cancelBtn;

    @FXML
    private Button addPart_closeBtn;

    @FXML
    private TextField addPart_partID;


    //Toggle Group - radio buttons functionality
    public void displayMachineIDOrCompanyName(ActionEvent event) {
        if(inHouseRadioBtn.isSelected()) {
            displayCompanyOrMachineLabel.setText("Machine ID:");
            inputCompanyOrMachineInputField.setPromptText("machine ID");
        } else if(outsourcedRadioBtn.isSelected()) {
            displayCompanyOrMachineLabel.setText("Company Name:");
            inputCompanyOrMachineInputField.setPromptText("company name");
        }
    }

    public void clickSavePartBtn(ActionEvent event) throws IOException{
        //Part Category Selection Validation - No null Accepted ~ it has to select inHouse or Outsourced
        if(inHouseRadioBtn.isSelected() || outsourcedRadioBtn.isSelected()) {
            //Not null accepted Input validation checks that none of the fields are blank or empty...
            if(!addPart_setPartName.getText().trim().isEmpty() || !addPart_setInventoryLevel.getText().trim().isEmpty() || addPart_setPriceUnit.getText().trim().isEmpty() || !addPart_setMax.getText().trim().isEmpty() || !addPart_setMin.getText().trim().isEmpty() || !inputCompanyOrMachineInputField.getText().trim().isEmpty()) {
                //check if the part name is available or if it already exists using the validatePartName method
                validatePartName();

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

    public void addPart_cancelBtnAction(ActionEvent event) {
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


    //SIDE MENU
    public void addPartRedirectsToEMIMSHomePage() throws IOException {
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

    public void addPartRedirectsToAddProductPage (ActionEvent event) throws IOException {
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

    public void addPartRedirectsToModifyProductPage (ActionEvent event) throws IOException {
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

    public void addPartRedirectsToModifyPartPage (ActionEvent event) throws IOException {
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

//    public void closeBtnAction(ActionEvent e) {
//        Stage stage = (Stage) close.getScene().getWindow();
//        stage.close();
//    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inHouseRadioBtn.setSelected(true);

    }
}
