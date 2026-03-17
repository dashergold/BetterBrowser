package org.webbrowser.browser.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import org.webbrowser.browser.controller.TabController;
import org.webbrowser.chat.service.ChatService;
import org.webbrowser.settings.service.SettingsService;

import java.io.IOException;

/**
 * Service responsible for managing the browser UI, including tabs, history, settings, and chat.
 * <p>
 * This class handles:
 * <ul>
 *     <li>Tab creation and navigation.</li>
 *     <li>Opening history, settings, and chat.</li>
 *     <li>Delegation to {@link HistoryService}, {@link SettingsService}, and {@link ChatService}.</li>
 * </ul>
 * <p>
 * Implements a singleton to ensure a single shared instance across the application.
 *
 * @author Axel
 * @since 2026
 */
public class BrowserService {
    /**
     * A singleton, the single instance of this service.
     */
    private static final BrowserService instance = new BrowserService();
    /**
     * The instance for managing history.
     */
    private final HistoryService historyService = HistoryService.getInstance();
    /**
     * the instance for managing settings.
     */
    private final SettingsService settingsService = SettingsService.getInstance();
    /**
     * The instance for managing the chat.
     */
    private final ChatService chatService = ChatService.getInstance();
    /**
     * A JavaFX graphical container of all the tab instances.
     */
    private TabPane tabPane;

    /**
     * Private constructor for singleton use.
     */
    private BrowserService() {}

    /**
     * Creates a new browser tab with its content and controller.
     * @return the created tab
     */
    private Tab createNewTab() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/webbrowser/browser/browser/tab.fxml"));
            Parent root = loader.load();

            TabController controller = loader.getController();
            Tab tab = new Tab("New tab");
            tab.setContent(root);
            controller.setTab(tab);

            return tab;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a special "add tab" button tab.
     * <p>
     * When selected it inserts a new tab and re-selects the new tab created.
     * @return the "add tab" tab.
     */
    private Tab newTabButton() {
        Tab addTab = new Tab("+");
        addTab.setClosable(false);
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab)-> {
            if(newTab == addTab) {
                tabPane.getTabs().add(tabPane.getTabs().size() -1, createNewTab());
                tabPane.getSelectionModel().select(tabPane.getTabs().size()-2);
            }
        });
        return addTab;
    }

    /**
     * Adds a new tab to the tab pane.
     */
    public void addTab() {
        Tab tab = createNewTab();
        tabPane.getTabs().addAll(tab,newTabButton());
    }

    /**
     * Adds a new tab that displays the browsing history.
     */
    public void addHistoryTab() {
        Tab historyTab = historyService.createHistoryTab();
        tabPane.getTabs().add(tabPane.getTabs().size() - 1,historyTab);
        tabPane.getSelectionModel().select(tabPane.getTabs().size() -2);
    }

    /**
     * Opens the settings view inside the provided root pane.
     * @param rootPane the container for settings.
     */
    public void openSettings(BorderPane rootPane) {
        settingsService.openSettings(rootPane);
    }

    /**
     * Opens the chat view inside the provided root pane and assigns it the current account from {@link SettingsService}.
     * @param rootPane the container for chat.
     */
    public void openChat(BorderPane rootPane) {
        chatService.setAccount(settingsService.getCurrentAccount());
        chatService.openChat(rootPane);
    }

    /**
     * Sets the {@link TabPane} where tabs are displayed.
     * @param tabPane the tab pane.
     */
    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    /**
     * getter for the singleton instance.
     * @return the current instance.
     */
    public static BrowserService getInstance() {
        return instance;
    }
}
