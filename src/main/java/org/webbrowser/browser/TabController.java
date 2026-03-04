package org.webbrowser.browser;

import javafx.beans.binding.Bindings;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Axel
 * @since 2026
 */
public class TabController {
    private static final String DEFAULT_BROWSER = "google.com"; //todo make this interchangeable in settings
    private Tab tab;
    @FXML
    private TextField searchField;

    @FXML
    private Button backButton;
    @FXML
    private Button forwardButton;
    private WebHistory history;

    @FXML
    private WebView webView;
    private WebEngine engine;

    public void initialize() {
        engine = webView.getEngine();
        searchField.setText(DEFAULT_BROWSER);
        search();
        history = engine.getHistory();

        backButton.disableProperty().bind(history.currentIndexProperty().isEqualTo(0));
        forwardButton.disableProperty().bind(history.currentIndexProperty().isEqualTo(Bindings.size(history.getEntries()).subtract(1)));

        titleHandler();
        locationHandler();
    }

    @FXML
    public void backward() {
        history.go(-1);
    }

    @FXML
    public void forward() {
        history.go(1);
    }

    @FXML
    public void enterURLContent(ActionEvent event) {
        search();
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    private void titleHandler() {
        engine.titleProperty().addListener((obs, oldTitle, newTitle) -> {
            if (newTitle != null && tab != null) {
                tab.setText(newTitle);
            }
        });
    }

    private void locationHandler() {
        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                String url = engine.getLocation();
                String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                searchField.setText(url);
                HistoryController.appendToDB(date, url);
                //System.out.println(url + " "+ time);

            }
        });
    }

    private void search() {
        String url = searchField.getText().trim();
        if (!url.startsWith("http")) {
            url = "https://" + url;
        }
        System.out.println(url);
        engine = webView.getEngine();
        try {
            engine.load(url);
        } catch (Exception e) {
            System.out.println("Error occurred when fetching url " + url);
        }
    }


}
