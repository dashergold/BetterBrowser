package org.webbrowser.browser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
/**
 * @author Axel
 * @since 2026
 */
public class BrowserApplication extends Application {
    private static Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(BrowserApplication.class.getResource("main.fxml"));
        //change size to 1920:1080
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        primaryStage.setTitle("Browser");
        primaryStage.setScene(scene);



        primaryStage.show();
    }
    public static double getWindowX() {
        return primaryStage.getX();
    }
    public static double getWindowY() {
        return primaryStage.getY();
    }
    public static double getWindowHeight() {
        return primaryStage.getHeight();
    }
    public static double getWindowWidth() {
        return primaryStage.getWidth();
    }

}
