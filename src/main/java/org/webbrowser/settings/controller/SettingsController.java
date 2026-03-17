package org.webbrowser.settings.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.webbrowser.accounts.Account;
import org.webbrowser.accounts.AccountWindow;
import org.webbrowser.accounts.service.AccountService;
import org.webbrowser.settings.ConfigManager;
import org.webbrowser.settings.service.SettingsService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller responsible for handling the settings view.
 * <p>
 *     This class manages:
 *     <ul>
 *         <li>Selecting and saving a default browser.</li>
 *         <li>Displaying the current account information.</li>
 *         <li>Opening the account management window.</li>
 *     </ul>
 * </p>
 * @author Axel
 * @since 2026
 */
public class SettingsController {
    /**
     * Service responsible for handling settings.
     */
    private final SettingsService settingsService = SettingsService.getInstance();
    /**
     * Label displaying the current account.
     */
    @FXML
    private Label currentAcc;
    /**
     * Dropdown for selecting the default browser.
     */
    @FXML
    private ComboBox<String> defaultBrowserSelectionBox;

    /**
     * Initializes the settings view.
     * <p>
     *     Sets the current account label, and populates the default browser selection dropdown.
     * </p>
     */
    public void initialize() {
        Account account = settingsService.getCurrentAccount();
        currentAcc.setText(account.isRegistered() ? account.getUsername() : "not signed in");
        String defaultBrowser = settingsService.getDefaultBrowser();
        defaultBrowserSelectionBox.setItems(settingsService.getBrowserOptions());
        defaultBrowserSelectionBox.getSelectionModel().select(settingsService.getDefaultBrowserLabel(defaultBrowser));
    }

    /**
     * Saves the selected browser settings.
     */
    @FXML
    private void saveChanges() {
        String selectedBrowser = defaultBrowserSelectionBox.getValue();
        settingsService.saveDefaultBrowser(selectedBrowser);
    }

    /**
     * Opens the account management window.
     */
    @FXML
    private void openAccountWindow() {
        settingsService.openAccountWindow();
    }

    /**
     * Closes the settings view.
     */
    @FXML
    private void closeSettings() {
        settingsService.closeSettings();
    }
}
