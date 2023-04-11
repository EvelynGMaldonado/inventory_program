package com.example.inventory_program;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AddPartController {

    @FXML
    private StackPane addPart_page;

    @FXML
    private Button addProductPageBtn;

    @FXML
    private Button close;

    @FXML
    private Button modifyPartPageBtn;

    @FXML
    private Button modifyProductPageBtn;

    @FXML
    private Button settingsBtn;

    @FXML
    private Button startBtn;

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

    public void closeBtnAction(ActionEvent e) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
}
