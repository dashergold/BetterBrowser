package org.webbrowser.settings.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.webbrowser.accounts.Account;
import org.webbrowser.accounts.AccountWindow;
import org.webbrowser.accounts.service.AccountService;
import org.webbrowser.settings.ConfigManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Service responsible for managing application settings.
 * <p>
 *     This class handles:
 *     <ul>
 *         <li>Default browser configuration.</li>
 *         <li>Settings UI rendering.</li>
 *     </ul>
 *     Implements a singleton to ensure a single shared instance across the application.
 * </p>
 * @author Axel
 * @since 2026
 */
public class SettingsService {
    /**
     * A singleton, the single instance of this service.
     */
    private static final SettingsService instance = new SettingsService();
    /**
     * The instance of the manager for browser configurations.
     */
    private final ConfigManager configManager = ConfigManager.getInstance();
    /**
     * The instance of the manager required to open the account window.
     */
    private final AccountService accountService = AccountService.getInstance();
    /**
     * Available browser options mapped from label to URL.
     */
    private final Map<String, String> browsers = Map.of(
            "Google", "https://google.com",
            "Yahoo", "https://yahoo.com",
            "DuckDuckGo", "https://duckduckgo.com",
            "Bing", "https://bing.com"
    );
    /**
     * Root pane used for rendering settings UI
     */
    private BorderPane rootPane;

    /**
     * Private constructor for singleton usage.
     */
    private SettingsService() {}

    /**
     * Opens the settings view in the given root pane.
     * @param rootPane the main UI container.
     */
    public void openSettings(BorderPane rootPane) {
        this.rootPane = rootPane;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/webbrowser/browser/settings/settings.fxml"));
            Parent settingsView = loader.load();
            this.rootPane.setRight(settingsView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves the selected browser as default.
     * @param browserLabel the selected browser label.
     */
    public void saveDefaultBrowser(String browserLabel) {
        HashMap<String, String> config = new HashMap<>();
        config.put("default-browser",browsers.get(browserLabel));

        configManager.editSettingsConfig(config);
        configManager.loadConfig();
    }

    /**
     * Closes the settings view-
     */
    public void closeSettings() {
        if (rootPane != null) {
            rootPane.setRight(null);
        }
    }

    /**
     * Opens the account management window.
     */
    public void openAccountWindow() {
        try {
            new AccountWindow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns all available browser mappings.
     * @return map of browsers {@code key: label} {@code value: URL}.
     */
    public Map<String, String> getBrowsers() {
        return browsers;
    }

    /**
     * Returns browser labels for UI selection.
     * @return List of browser names.
     */
    public ObservableList<String> getBrowserOptions() {
        return FXCollections.observableArrayList(browsers.keySet());
    }

    /**
     * Returns the currently active account.
     * @return the current {@link Account}.
     */
    public Account getCurrentAccount() {
        return accountService.getCurrentAccount();
    }

    /**
     * Returns the configured default browsers URL.
     * @return browser URL.
     */
    public String getDefaultBrowser() {
        return configManager.getDefaultBrowser();
    }

    /**
     * Retrieves the browser label from a URL.
     * @param defaultBrowser the browser URL.
     * @return  the corresponding label, or null if not found.
     */
    public String getDefaultBrowserLabel (String defaultBrowser) {
        for(Map.Entry<String,String> entry : browsers.entrySet()) {
            if(entry.getValue().equals(defaultBrowser)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Getter for the singleton instance.
     * @return the current instance.
     */
    public static SettingsService getInstance() {
        return instance;
    }
}
