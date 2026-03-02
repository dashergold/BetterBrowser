package org.webbrowser.browser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BrowserController {
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;


    @FXML
    public void onButtonClick(ActionEvent event) {
        String text = searchField.getText();
        System.out.println(text);

    }
}
