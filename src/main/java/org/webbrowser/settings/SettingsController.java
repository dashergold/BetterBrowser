package org.webbrowser.settings;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class SettingsController {
    //todo figure out how to separate the xml from this class into the ConfigManager class
    //todo this class should only be used to handle changes from the javafx scene (rn default browser)

    @FXML
    private ComboBox<String> defaultBrowserSelectionBox;
    private ObservableList<String> browserOptions = FXCollections.observableArrayList("Google", "yahoo","duckduckgo");

    public void initialize() {
        defaultBrowserSelectionBox.setItems(browserOptions);
    }

    @FXML
    private void saveChanges() {

    }

}
