package org.webbrowser.browser.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

import javafx.scene.layout.BorderPane;
import org.webbrowser.browser.service.BrowserService;

/**
 * Controller responsible for manging the main browser view.
 * <p>
 * This controller handles user interactions such as:
 * <ul>
 *     <li>Initializing the browser with a default tab.</li>
 *     <li>Opening browser history.</li>
 *     <li>Opening settings</li>
 *     <li>Opening TCP chat-server functionality between users.</li>
 * </ul>
 *
 * @author Axel
 * @since 2026
 */
public class BrowserController {
    /**
     * Service responsible for browser related logic and tab management.
     */
    private final BrowserService browserService = BrowserService.getInstance();
    /**
     * The tab pane that holds the browser tabs.
     */
   @FXML
    private TabPane tabPane;
    /**
     * The root layout pane used for storing the content of a tab, as well as the settings and the chat functionality.
     */
   @FXML
   private BorderPane rootPane;

    /**
     * Initializes the controller after the FXML has been loaded.
     * <p>
     * Sets up the {@link BrowserController#tabPane} in the {@link BrowserService} and opens the initial browser tab.
     */
    public void initialize() {
       browserService.setTabPane(tabPane);
       browserService.addTab();
   }
    /**
     * Opens the browsing history in a new tab.
     */
   @FXML
   private void openHistory() {
        browserService.addHistoryTab();
   }
    /**
     * Opens the settings view inside the root pane.
     */
   @FXML
    private void openSettings() {
        browserService.openSettings(rootPane);
   }
    /**
     * Opens the chat view inside the root pane.
     */
   @FXML
    public void openChat() {
        browserService.openChat(rootPane);
    }
}
