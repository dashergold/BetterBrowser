package org.webbrowser.browser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.webbrowser.chat.network.Client;
import org.webbrowser.chat.network.Server;
import org.webbrowser.settings.ConfigManager;

import java.io.IOException;

/**
 * @author Axel
 * @since 2026
 */
public class BrowserApplication extends Application {
    private static Stage primaryStage;
    private final ConfigManager configManager = ConfigManager.getInstance();
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        configManager.loadConfig();
        FXMLLoader fxmlLoader = new FXMLLoader(BrowserApplication.class.getResource("/org/webbrowser/browser/browser/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 700); //default at 1920x1080
        primaryStage.setTitle("Browser");
        primaryStage.setScene(scene);



        primaryStage.show();

    }
    @Override
    public void stop() throws Exception {
        super.stop();



        Client client = Client.getInstance();
        if (client != null) {
            client.close();
        }
        Server server = Server.getInstance();
        if(server != null) {
            server.close();
        }


    }


}
