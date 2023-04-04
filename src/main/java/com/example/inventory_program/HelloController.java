package com.example.inventory_program;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
//public class HelloController {

    @FXML
    private AnchorPane landing_page;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button startBtn;

    @FXML
    private TextField usernameField;

    @FXML
    private StackPane home_page;

    @FXML
    private Button addPartPageBtn;

    @FXML
    private Button addProductPageBtn;

    @FXML
    private Button modifyPartPageBtn;

    @FXML
    private Button modifyProductPageBtn;

    @FXML
    private Button settingsBtn;

    @FXML
    private StackPane addPart_page;

    @FXML
    private StackPane addProduct_page;

    @FXML
    private StackPane modifyPart_page;

    @FXML
    private StackPane modifyProduct_page;

    @FXML
    private Button close;

    public void clickStartBtn (ActionEvent event) throws IOException {
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

    public void clickAddPartPageBtn (ActionEvent event) throws IOException {
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

    public void clickAddProductPageBtn (ActionEvent event) throws IOException {
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

    public void clickModifyProductPageBtn (ActionEvent event) throws IOException {
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

    public void clickModifyPartPageBtn (ActionEvent event) throws IOException {
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

    public void close() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        borderpane.setCenter(GlyphsDude.createIcon(FontAwesomeIcon.DATABASE, "200px"));

    }

}