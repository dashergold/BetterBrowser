package org.webbrowser.browser.controller;

import javafx.beans.binding.Bindings;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import org.webbrowser.browser.service.HistoryService;
import org.webbrowser.browser.service.TabService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Controller responsible for managing a single browser tab.
 * <p>
 * This controller handles:
 * <ul>
 *     <li>Loading and navigating web pages.</li>
 *     <li>Managing browser history (back/forward).</li>
 *     <li>Updating tab title dynamically.</li>
 *     <li>Storing visited URLs in history.</li>
 * </ul>
 *
 * @author Axel
 * @since 2026
 */
public class TabController {
    /**
     * Default URL loaded when a new tab is created.
     */
    private static String defaultBrowser;
    /**
     * Service responsible for storing browser history.
     */
    private final HistoryService historyService = HistoryService.getInstance();
    /**
     * Service responsible for handling {@link WebView} operations.
     */
    private TabService tabService;
    /**
     * The JavaFX tab associated with this controller.
     */
    private Tab tab;
    /**
     * Input field for entering URLs.
     */
    @FXML
    private TextField searchField;
    /**
     * Button for navigating forwards.
     */
    @FXML
    private Button forwardButton;
    /**
     * Button for navigating backwards.
     */
    @FXML
    private Button backButton;
    /**
     * WebView component used to render web pages.
     */
    @FXML
    private WebView webView;

    /**
     * Initializes the tab after the FXML has been loaded.
     * <p>
     * Sets up:
     * <ul>
     *     <li>Default page loading.</li>
     *     <li>Navigation button bindings.</li>
     *     <li>Title and location listeners</li>
     * </ul>
     */
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

    /**
     * Navigates one step backwards in browsing history.
     */
    @FXML
    public void backward() {
        tabService.back();
    }

    /**
     * Navigates one stop forward in browsing history.
     */
    @FXML
    public void forward() {
        tabService.forward();
    }

    /**
     * Reloads the current page.
     */
    @FXML
    public void reload() {
        tabService.reload();
    }

    /**
     * Loads the URL entered in the {@link TabController#searchField}.
     */
    @FXML
    public void enterURLContent() {
        tabService.loadURL(searchField.getText());
    }

    /**
     * Sets the JavaFX {@link Tab} associated with this controller.
     * @param tab the tab instance.
     */
    public void setTab(Tab tab) {
        this.tab = tab;
    }

    /**
     * Updates the title when the page title changes.
     */
    private void titleHandler() {
        tabService.getEngine().titleProperty().addListener((obs, oldTitle, newTitle) -> {
            if (newTitle != null && tab != null) {
                tab.setText(newTitle);
            }
        });
    }

    /**
     * Handles the page load completion.
     * <p>
     * Updates the URL field and stores the visited page in history.
     */
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

    /**
     * Sets the default browser URL for new tabs.
     * @param browser the default URL.
     */
    public static void setDefaultBrowser(String browser) {
        defaultBrowser = browser;
    }
}
