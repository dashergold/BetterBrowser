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

//todo figure out how to separate the xml from this class into the ConfigManager class
//todo this class should only be used to handle changes from the javafx scene (rn default browser)

/**
 * @since 2026
 * @author Axel
 */
public class SettingsController {
    private final SettingsService settingsService = SettingsService.getInstance();

    @FXML
    private Label currentAcc;

    @FXML
    private ComboBox<String> defaultBrowserSelectionBox;

    public void initialize() {
        Account account = settingsService.getCurrentAccount();
        currentAcc.setText(account.isRegistered() ? account.getUsername() : "not signed in");
        String defaultBrowser = settingsService.getDefaultBrowser();
        defaultBrowserSelectionBox.setItems(settingsService.getBrowserOptions());
        defaultBrowserSelectionBox.getSelectionModel().select(settingsService.getDefaultBrowserLabel(defaultBrowser));
    }

    @FXML
    private void saveChanges() {
        String selectedBrowser = defaultBrowserSelectionBox.getValue();
        settingsService.saveDefaultBrowser(selectedBrowser);
    }

    @FXML
    private void openAccountWindow() {
        settingsService.openAccountWindow();
    }

    @FXML
    private void closeSettings() {
        settingsService.closeSettings();
    }
}
