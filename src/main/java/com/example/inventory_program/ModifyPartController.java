package com.example.inventory_program;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {

    HelloController helloController;
    PartData partData;
    PartsAndProductsInventory partsAndProductsInventory;


    @FXML
    private Button addPartPageBtn;

    @FXML
    private Button addProductPageBtn;

    @FXML
    private Button modifyPart_closeBtn;

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

    private final String getSinglePartID;
    private final String getSinglePartName;
    private final String getSinglePartStock;
    private final String getSinglePartPriceUnit;
    private final String getSinglePartMin;
    private final String getSinglePartMax;
    private final String getSinglePartMachineID;
    private final String getSinglePartCompanyName;

    //display info from the row selected to be modified/edited at the home_page
//    public void showSelectedPartDataInformation(String name, String age) {
//        nameScene2.setText(name);
//        ageScene2.setText(age);
//
//    };


//**new ok!!
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

//    //**new ok!! testing
//    public void checkingIfInOrOutSourced(String getSinglePartMachineID, String getSinglePartCompanyName) {
//        System.out.println("we are in checkingIfInOrOutSourced function located in Modify controller!! showing the machineID number as : " + getSinglePartMachineID + " and the company name for outsourced as : " + getSinglePartCompanyName );

//        if(modifyPart_setPartName.getText() == "" || modifyPart_setPartName == null) {
//           modifyPart_setPartName.setText(getSinglePartName);
//        }
//        if (!(modifyPart_setPartName == null) || modifyPart_setPartName.equals("")) {
//            modifyPart_setPartName.setText("hello!");
//        }
//    }


//    public void showSelectedPartDataInformation(String part_name) {
//        System.out.println("we are on showselectedpartDataInformation");
//        modifyPart_setPartName.setText(part_name);
//    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        modifyPart_setPartName.setText("hellooooo");

        modifyPart_displayCompanyOrMachineLabel.setText("Machine ID:");

        modifyPart_partIDTextField.setText(getSinglePartID);
        modifyPart_setPartName.setText(getSinglePartName);
        modifyPart_setInventoryLevel.setText(getSinglePartStock);
        modifyPart_setPriceUnit.setText(getSinglePartPriceUnit);
        modifyPart_setMin.setText(getSinglePartMin);
        modifyPart_setMax.setText(getSinglePartMax);

        if(getSinglePartMachineID != null && !getSinglePartMachineID.trim().isEmpty()) {
            System.out.println("part machine -In Home data is in the database!!");
            modifyPartInHouseRadioBtn.setSelected(true);
            modifyPart_displayCompanyOrMachineLabel.setText("Machine ID:");
            modifyPart_inputCompanyOrMachineInputField.setText(getSinglePartMachineID);
        } else if (getSinglePartCompanyName != null && !getSinglePartCompanyName.trim().isEmpty()){
            System.out.println("company name - outsourced data is in the database!!");
            modifyPartOutsourcedRadioBtn.setSelected(true);
            modifyPart_displayCompanyOrMachineLabel.setText("Company Name:");
            modifyPart_inputCompanyOrMachineInputField.setText(getSinglePartCompanyName);
        }

//        Platform.runLater(() -> {
//            startingToModify();
//        });
//
    }




//    public void closeBtnAction(ActionEvent e) {
//        Stage stage = (Stage) close.getScene().getWindow();
//        stage.close();
//    }

}
