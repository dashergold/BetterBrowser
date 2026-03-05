package org.webbrowser.settings;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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
    private static HashMap<String, String> config;

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
        config = ConfigManager.getConfig();

        browserOptions.addAll(browsers.keySet());
        defaultBrowserSelectionBox.setItems(browserOptions);
        String defaultBrowser = getDefaultBrowserLabel();
        defaultBrowserSelectionBox.getSelectionModel().select(defaultBrowser);


    }
    private String getDefaultBrowserLabel() {
        String defaultBrowser = config.get("default-browser");
        for(Map.Entry<String, String> entry: browsers.entrySet()) {
            if(entry.getValue().equals(defaultBrowser)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @FXML
    private void saveChanges() {
        String newBrowser = browsers.get(defaultBrowserSelectionBox.getValue());
        editConfig("default-browser",newBrowser);
        BrowserApplication.loadConfig();
    }
    @FXML
    private void resetChanges() {
        ConfigManager.createDefaultConfig();
        BrowserApplication.loadConfig();
        String defaultBrowser = getDefaultBrowserLabel();
        defaultBrowserSelectionBox.getSelectionModel().select(defaultBrowser);


    }

    private void editConfig(String key, String value) {
        for(String s: config.keySet()) {
            if(s.equals(key)) {
                config.put(s,value);
            }
        }
        ConfigManager.editConfig(config);
    }


}
