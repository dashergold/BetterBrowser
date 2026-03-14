package org.webbrowser.browser.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;

import javafx.scene.layout.BorderPane;
import org.webbrowser.browser.BrowserApplication;
import org.webbrowser.browser.service.BrowserService;

/**
 * @author Axel
 * @since 2026
 */
public class BrowserController {

    private final BrowserService browserService = BrowserService.getInstance();



   @FXML
    private TabPane tabPane;

   @FXML
   private BorderPane rootPane;

    public void initialize() {
       browserService.setTabPane(tabPane);
       browserService.addTab();
   }

   @FXML
   private void openHistory() {
        browserService.addHistoryTab();

   }
   @FXML
    private void openSettings() {
        browserService.openSettings(rootPane);
   }

   @FXML
    public void openChat() {
        browserService.openChat(rootPane);
    }



}
