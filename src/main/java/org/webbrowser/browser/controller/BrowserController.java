package org.webbrowser.browser.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;

import org.webbrowser.browser.BrowserApplication;
import org.webbrowser.browser.service.BrowserService;
import org.webbrowser.settings.SettingsWindow;
/**
 * @author Axel
 * @since 2026
 */
public class BrowserController {

    private final BrowserService browserService = BrowserService.getInstance();

   @FXML
    private TabPane tabPane;

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
        double windowHeight = BrowserApplication.getWindowHeight();
        double windowWidth = BrowserApplication.getWindowWidth();
        double windowX = BrowserApplication.getWindowX();
        double windowY = BrowserApplication.getWindowY();

       try {
           SettingsWindow settingsWindow = new SettingsWindow();
           settingsWindow.show(windowX,windowY,windowHeight, windowWidth); //hard coded, fix
       } catch (IOException e) {
           throw new RuntimeException(e);
       }

   }

}
