package org.webbrowser.browser.service;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import org.webbrowser.accounts.Account;
import org.webbrowser.accounts.service.AccountService;
import org.webbrowser.browser.HistoryEntry;
import org.webbrowser.browser.controller.HistoryController;
import org.webbrowser.browser.repository.HistoryRepository;

import java.io.IOException;
import java.sql.SQLException;

public class HistoryService {
    private static final HistoryService instance = new HistoryService();
    private final HistoryRepository repository = new HistoryRepository();




    private HistoryService() {}

    public Tab createHistoryTab() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/webbrowser/browser/history/history.fxml"));
            Parent root = loader.load();
            HistoryController controller = loader.getController();
            Tab tab = new Tab("History");
            tab.setContent(root);
            return tab;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<HistoryEntry> loadHistory(Account account) {
        ObservableList<HistoryEntry> entries = javafx.collections.FXCollections.observableArrayList();
        if(account.isRegistered()) {
            try {
                entries = repository.getHistory(account);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return entries;
    }

    public void deleteHistory(Account account) {
        if(account.isRegistered()) {
            try {
                repository.deleteHistory(account);
            } catch(SQLException e ) {
                throw new RuntimeException(e);
            }
        }
    }

    public void storeHistory(String date, String url) {
        AccountService accountService = AccountService.getInstance();
        Account account = accountService.getCurrentAccount();
        if(account.isRegistered()) {
            try {
                repository.append(account.getEmail(), date, url);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static HistoryService getInstance() {
        return instance;
    }
}
