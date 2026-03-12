package org.webbrowser.browser.controller;

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
import org.webbrowser.browser.service.BrowserService;
import org.webbrowser.browser.service.HistoryService;
import org.webbrowser.browser.service.TabService;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Axel
 * @since 2026
 */
public class TabController {

    private final HistoryService historyService = HistoryService.getInstance();

    private static String defaultBrowser;
    private Tab tab;
    private TabService tabService;

    @FXML
    private TextField searchField;

    @FXML
    private Button backButton;
    @FXML
    private Button forwardButton;

    @FXML
    private WebView webView;


    public void initialize() {
        tabService = new TabService(webView);
        WebHistory history = tabService.getHistory();

        searchField.setText(defaultBrowser);
        tabService.loadURL(defaultBrowser);

        backButton.disableProperty().bind(history.currentIndexProperty().isEqualTo(0));
        forwardButton.disableProperty().bind(history.currentIndexProperty().isEqualTo(Bindings.size(history.getEntries()).subtract(1)));

        titleHandler();
        locationHandler();

    }

    @FXML
    public void backward() {
        tabService.back();
    }

    @FXML
    public void forward() {
        tabService.forward();
    }
    @FXML
    public void reload() {
        tabService.reload();
    }

    @FXML
    public void enterURLContent(ActionEvent event) {
        tabService.loadURL(searchField.getText());
    }
    public void setTab(Tab tab) {
        this.tab = tab;
    }
    private void titleHandler() {
        tabService.getEngine().titleProperty().addListener((obs, oldTitle, newTitle) -> {
            if (newTitle != null && tab != null) {
                tab.setText(newTitle);
            }
        });
    }
    private void locationHandler() {
        tabService.getEngine().getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if(newState == Worker.State.SUCCEEDED) {
                String url = tabService.getEngine().getLocation();
                String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                searchField.setText(url);
                historyService.storeHistory(date, url);
            }
        });
    }

    public static void setDefaultBrowser(String browser) {
        defaultBrowser = browser;
    }











}
