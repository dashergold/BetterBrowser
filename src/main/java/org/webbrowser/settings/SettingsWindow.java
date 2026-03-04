package org.webbrowser.settings;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @since 2026
 * @author Axel
 */
public class SettingsWindow {
    private static final double DEFAULT_WIDTH = 400;
    private Stage stage;
    public SettingsWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/webbrowser/browser/settings.fxml"));
        Scene scene = new Scene(loader.load(), DEFAULT_WIDTH, 1000);
        stage = new Stage();
        stage.setTitle("Settings");
        stage.setScene(scene);
    }
    public void show(double x, double y, double windowHeight, double windowWith) {
        stage.setX(x+windowWith-DEFAULT_WIDTH-15);
        stage.setY(y+5);
        stage.setHeight(windowHeight);
        stage.show();
    }

}
