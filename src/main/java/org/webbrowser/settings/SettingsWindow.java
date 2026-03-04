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
    private Stage stage;
    public SettingsWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/webbrowser/browser/settings.fxml"));
        Scene scene = new Scene(loader.load(), 400, 1000);
        stage = new Stage();
        stage.setTitle("Settings");
        stage.setScene(scene);
    }
    public void show(double x, double y) {
        stage.setX(x);
        stage.setY(y);
        stage.show();
    }

}
