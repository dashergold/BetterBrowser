package org.webbrowser.browser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.webbrowser.settings.ConfigManager;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Axel
 * @since 2026
 */
public class BrowserApplication extends Application {
    private static Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        loadConfig();
        FXMLLoader fxmlLoader = new FXMLLoader(BrowserApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 700); //default at 1920x1080
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
    public static void loadConfig() {
        HashMap<String, String> config = ConfigManager.getConfig();
        String defaultBrowser = null;
        for(String s: config.keySet()) {
            if(s.equals("default-browser")) {
                defaultBrowser = config.get(s);
            }
        }
        TabController.setDefaultBrowser(defaultBrowser);
    }


}
