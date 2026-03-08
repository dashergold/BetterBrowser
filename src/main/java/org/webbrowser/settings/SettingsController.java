package org.webbrowser.settings;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.webbrowser.accounts.AccountWindow;
import org.webbrowser.browser.BrowserApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @since 2026
 * @author Axel
 */
public class SettingsController {
    //todo figure out how to separate the xml from this class into the ConfigManager class
    //todo this class should only be used to handle changes from the javafx scene (rn default browser)


    private String defaultBrowser;
    private static String accountLabel;
    @FXML
    private Label currentAcc;

    @FXML
    private ComboBox<String> defaultBrowserSelectionBox;
    private Map<String, String> browsers = Map.of(
            "Google", "https://google.com",
            "Yahoo", "https://yahoo.com",
            "DuckDuckGo", "https://duckduckgo.com",
            "Bing", "https://bing.com"
    );
    private ObservableList<String> browserOptions = FXCollections.observableArrayList();


    public void initialize() {
        currentAcc.setText(accountLabel);
        defaultBrowser = ConfigManager.getDefaultBrowser();


        browserOptions.addAll(browsers.keySet());
        defaultBrowserSelectionBox.setItems(browserOptions);

        defaultBrowserSelectionBox.getSelectionModel().select(getDefaultBrowserLabel());
    }
    private String getDefaultBrowserLabel() {
        for(Map.Entry<String, String> entry: browsers.entrySet()) {
            if(entry.getValue().equals(defaultBrowser)) {
                return entry.getKey();
            }
        }
        return null;
    }


    @FXML
    private void saveChanges() {
        HashMap<String, String> config = new HashMap<>();
        config.put("default-browser",browsers.get(defaultBrowserSelectionBox.getValue()));
        //for other future settings: config.put("setting","get the setting");

        ConfigManager.editSettingsConfig(config);
        BrowserApplication.loadConfig();

    }
    public static void setAccountLabel(String accountName) {
        accountLabel = accountName;
    }






    @FXML
    private void openAccountWindow() {
        try {
            AccountWindow accountWindow = new AccountWindow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
