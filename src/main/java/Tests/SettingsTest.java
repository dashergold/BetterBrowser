package Tests;

import javafx.application.Application;
import javafx.stage.Stage;
import org.webbrowser.settings.SettingsWindow;

import java.io.IOException;

public class SettingsTest extends Application {
    static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) {
        stage.show();
        openSettingsWindow(100,100);
    }
    private void openSettingsWindow(double y, double x) {
        try {
            SettingsWindow settings = new SettingsWindow();
            settings.show(x,y, 1000, 100);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
