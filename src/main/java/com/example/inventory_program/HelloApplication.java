package com.example.inventory_program;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * @author Evelyn G Morrow.
 * @version 1.0.
 * Public class HelloApplication is the primary class of the EM Inventory Management System.
 * Public class HelloApplication extends the Application class, which is a standard class in Java.
 * RUNTIME ERROR:
 * FUTURE ENHANCEMENT:
 */
public class HelloApplication extends Application {
//    static {
//        Font.loadFont(HelloApplication.class.getResource("/font/fontawesome-webfont.ttf").toExternalForm(), 10);
//    }

    /**
     * Public void start() method is called when the JavaFX application is started.
     * @param stage of the type Stage is taken.
     * The visual parts of the JavaFX Application (displayed).
     * @exception IOException if an input or output error occurred.
     * @see IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("landing_page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        //scene.getStylesheets().add(getClass().getResource("startPageDesign.css").toString());
        stage.setTitle("EM-Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Public static void main() method launches the application and let the developer passing command line parameters if needed.
     * The launch() method is a static method located in the Application class, and it detects from which class it is called, so it is not necessary to explicitly tell it what class to launch.
     */
    public static void main(String[] args) {
        launch();
    }
}


