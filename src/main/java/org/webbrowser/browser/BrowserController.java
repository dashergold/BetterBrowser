package org.webbrowser.browser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class BrowserController {
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private WebView result;
    private WebEngine engine;

    @FXML
    public void onButtonClick(ActionEvent event) {
        String text = searchField.getText();
        System.out.println(text);
        try {
            URL url = new URL(searchField.getText().trim());
            fetchHTML(url);
        } catch (Exception e) {
            System.out.println("Error occurred when fetching url");
        }

    }
    private void fetchHTML(URL url) throws IOException {
        engine = result.getEngine();
        URLConnection connection = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder content = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null) {
            content.append(line).append("\n");

        }
        reader.close();
        buildHTML(content.toString());
    }
    private void buildHTML(String html) {
        engine.loadContent(html);
    }
}
