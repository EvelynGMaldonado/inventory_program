package com.example.inventory_program;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {

    //adding private doubles x and y
//    private double x = 0;
//    private double y = 0;

    @Override
    //public void start(Stage stage) throws Exception {
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
//        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");

//        root.setOnMousePressed((MouseEvent event) -> {
//            x = event.getSceneX();
//            y = event.getSceneY();
//        });

//        root.setOnMouseDragged((MouseEvent event) -> {
//            stage.setX(event.getSceneX() - x);
//            stage.setY(event.getSceneY() - y);
//
//            stage.setOpacity(.8);
//        });
//
//        root.setOnMouseReleased((MouseEvent event) -> {
//            stage.setOpacity(1);
//        });



        //setting up initial style
        //stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}