package org.webbrowser.settings.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.webbrowser.accounts.Account;
import org.webbrowser.accounts.AccountWindow;
import org.webbrowser.accounts.service.AccountService;
import org.webbrowser.settings.ConfigManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SettingsService {
    private static final SettingsService instance = new SettingsService();
    private final ConfigManager configManager = ConfigManager.getInstance();
    private final AccountService accountService = AccountService.getInstance();

    private SettingsService() {}



    private final Map<String, String> browsers = Map.of(
            "Google", "https://google.com",
            "Yahoo", "https://yahoo.com",
            "DuckDuckGo", "https://duckduckgo.com",
            "Bing", "https://bing.com"
    );

    public Map<String, String> getBrowsers() {
        return browsers;
    }

    public ObservableList<String> getBrowserOptions() {
        return FXCollections.observableArrayList(browsers.keySet());
    }

    public Account getCurrentAccount() {
        return accountService.getCurrentAccount();
    }

    public String getDefaultBrowser() {
        return configManager.getDefaultBrowser();
    }

    public String getDefaultBrowserLabel (String defaultBrowser) {
        for(Map.Entry<String,String> entry : browsers.entrySet()) {
            if(entry.getValue().equals(defaultBrowser)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void saveDefaultBrowser(String browserLabel) {
        HashMap<String, String> config = new HashMap<>();
        config.put("default-browser",browsers.get(browserLabel));

        configManager.editSettingsConfig(config);
        configManager.loadConfig();
    }

    public void openAccountWindow() {
        try {
            new AccountWindow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SettingsService getInstance() {
        return instance;
    }

}
