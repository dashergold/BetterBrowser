package org.webbrowser.browser.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class ConsoleController {
    /**
     * Text area where the HTML code will be displayed.
     */
    @FXML
    private TextArea htmlArea;
    /**
     * The root pane for the tab window.
     */
    private BorderPane rootPane;

    /**
     * Close the developer console.
     */
    @FXML
    private void close() {
        this.rootPane.setBottom(null);
    }

    /**
     * Takes a webpages URL, fetches the HTML and sets the {@link ConsoleController#htmlArea} to display the HTML-code.
     * @param url The url of the site.
     * @throws IOException if the URL failed to load, or was malformed.
     */
    public void setHtmlArea(String url) throws IOException {
        URL webUrl = new URL(url);
        URLConnection conn = webUrl.openConnection();

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder html = new StringBuilder();

        String l;
        while((l = reader.readLine()) != null) {
            html.append(l).append("\n");
        }

        htmlArea.setText(html.toString());
        reader.close();
    }

    /**
     * Sets the root pane.
     * @param rootPane The root pane for the tab.
     */
    public void setRootPane(BorderPane rootPane) {
        this.rootPane = rootPane;
    }
}
