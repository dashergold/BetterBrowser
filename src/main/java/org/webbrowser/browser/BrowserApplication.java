package org.webbrowser.browser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.webbrowser.chat.network.Client;
import org.webbrowser.chat.network.Server;
import org.webbrowser.chat.network.ServerManager;
import org.webbrowser.settings.ConfigManager;

import java.io.IOException;

/**
 * Main JavaFX application class for the web browser.
 * <p>
 * This classes responsibilities entail:
 * <ul>
 *     <li>Initialize and display the primary stage.</li>
 *     <li>Load user configuration via {@link ConfigManager}.</li>
 *     <li>Manage application shutdown and network cleanup.</li>
 * </ul>
 * <p>
 *
 * This class extends {@link Application} and serves as the entry point.
 *
 * @author Axel
 * @since 2026
 */
public class BrowserApplication extends Application {
    /**
     * The primary stage of the application.
     */
    private static Stage primaryStage;
    /**
     * The instance for managing the configurations.
     */
    private final ConfigManager configManager = ConfigManager.getInstance();

    /**
     * Called when the JavaFX application starts.
     * <p>
     * Loads the main FXML view and displays the stage.
     * @param stage the primary stage.
     * @throws IOException if the FXML file can't be loaded.
     */
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

    /**
     * Called when the JavaFX application stops.
     * <p>
     * Closes chat clients and shuts down active servers.
     * @throws Exception if shutdown fails.
     */
    @Override
    public void stop() throws Exception {
        super.stop();

        Client client = Client.getInstance();
        if (client != null) {
            client.close();
        }
        ServerManager.getInstance().shutdownServer();


    }


}
