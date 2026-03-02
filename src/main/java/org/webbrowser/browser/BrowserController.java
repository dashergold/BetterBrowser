package org.webbrowser.browser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class BrowserController {
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private WebView result;
    private WebEngine engine;

    @FXML
    public void enterURLContent(ActionEvent event) {
        search();
    }

    private void search() {
        String url = searchField.getText().trim();
        if(!url.startsWith("http")) {url = "https://"+url;}
        System.out.println(url);
        engine = result.getEngine();
        try {
            engine.load(url);
        } catch (Exception e) {
            System.out.println("Error occurred when fetching url "+url );
        }
    }

}
