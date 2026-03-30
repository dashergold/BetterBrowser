package org.webbrowser.accounts;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.webbrowser.accounts.service.AccountService;

import java.io.IOException;

/**
 * Represents the account management window in the application.
 * <p>
 * This class is responsible for:
 * <ul>
 *     <li>Opening the account window.</li>
 *     <li>Determining the initial view (account or sign-in).</li>
 *     <li>Handling scene switching within the window.</li>
 * </ul>
 * <p>
 * The window is displayed as a modal stage, blocking interaction with other windows until closed.
 *
 * @author Axel
 * @since 2026
 */
public class AccountWindow {
    /**
     * The primary stage used for the account window.
     */
    private static Stage stage;
    /**
     * Service used to determine the current account state.
     */
    private final AccountService accountService = AccountService.getInstance();

    /**
     * Creates and displays the account window.
     * <p>
     * Loads the appropriate scene depending on if a user is logged in or not.
     * @throws IOException if the FXML file can't be loaded.
     */
    public AccountWindow() throws IOException {
        FXMLLoader loader;
        if(accountService.getCurrentAccount().isRegistered()) {
            loader = new FXMLLoader(getClass().getResource("/org/webbrowser/browser/account/account.fxml"));
        } else {
            loader = new FXMLLoader(getClass().getResource("/org/webbrowser/browser/account/accountSignIn.fxml"));
        }
        Scene scene = new Scene(loader.load(),400,400);
        scene.getStylesheets().add(getClass().getResource("/org/webbrowser/browser/style/style.css").toExternalForm());

        stage = new Stage();
        stage.setTitle("Account manager");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * Switches the current scene within the account window.
     * @param fxml the path to the FXML file to load.
     * @throws IOException if the FXML file can't be loaded.
     */
    public static void switchScene(String fxml) throws IOException{
        FXMLLoader loader = new FXMLLoader(AccountWindow.class.getResource(fxml));
        Scene scene = new Scene(loader.load(),400,400);

        stage.setScene(scene);
    }
}
