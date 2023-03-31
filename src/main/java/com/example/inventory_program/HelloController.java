package com.example.inventory_program;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private Button startBtn;

    @FXML
    private StackPane home_page;

    @FXML
    private Button addPartPageBtn;

    @FXML
    private Button addProductPageBtn;

    @FXML
    private Button homePageBtn;

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
        FXMLLoader ppMainloader = new FXMLLoader(getClass().getResource("home_page-parts&products.fxml"));

        //set view in ppMainWindow
        ppMainWindow.setScene(new Scene(ppMainloader.load(), 600, 400));

        //launch
        ppMainWindow.show();

    }

    public void close() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        borderpane.setCenter(GlyphsDude.createIcon(FontAwesomeIcon.DATABASE, "200px"));

    }

}