package com.example.inventory_program;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.example.inventory_program.HelloController;
import com.example.inventory_program.PartData;
import com.example.inventory_program.PartsAndProductsInventory;
import java.sql.*;

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
    private TextField modifyPart_setPartName = new TextField();

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

    //display info from the row selected to be modified/edited at the home_page
//    public void showSelectedPartDataInformation(String name, String age) {
//        nameScene2.setText(name);
//        ageScene2.setText(age);
//
//    };



    public static String modPart;
//**new ok!!
    public ModifyPartController(PartsAndProductsInventory partsAndProductsInventory, PartData partData) {
        this.partsAndProductsInventory = partsAndProductsInventory;
        this.partData = partData;
    }

    //**new ok!!
    public void startingToModify(String getPartName) {
//        modifyPart_setPartName.setText(getPartName);
//        modifyPart_setPartName.setPromptText(getPartName);
        System.out.println("we are in modify part controller now!! showing the: " + getPartName);

        if(modifyPart_setPartName.getText() == "" || modifyPart_setPartName == null) {
           modifyPart_setPartName.setText(getPartName);

        }
//        if (!(modifyPart_setPartName == null) || modifyPart_setPartName.equals("")) {
//            modifyPart_setPartName.setText("hello!");
//        }

    }


//    @FXML
//    void Initalize(MouseEvent event) {
//        System.out.println("Constructor for Part");
//    }


//    public void showSelectedPartDataInformation(String part_name) {
//        System.out.println("we are on showselectedpartDataInformation");
//        modifyPart_setPartName.setText(part_name);
//    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modifyPart_setPartName.setText("hellooooo");


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
