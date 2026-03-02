package org.webbrowser.browser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BrowserApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BrowserApplication.class.getResource("main.fxml"));
        //change size to 1920:1080
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setTitle("Browser");
        stage.setScene(scene);
        stage.show();
    }
}
