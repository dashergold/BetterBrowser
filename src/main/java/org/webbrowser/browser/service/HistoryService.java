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

/**
 * Service responsible for managing browser history operations.
 * <p>
 * This class handles:
 * <ul>
 *     <li>Creating history tabs.</li>
 *     <li>Loading history from the database.</li>
 *     <li>Deleting history for the current account.</li>
 *     <li>Storing new history entries.</li>
 * </ul>
 * <p>
 * Implements a singleton to ensure a single shared instance across the application.
 *
 * @author Axel
 * @since 2026
 */
public class HistoryService {
    /**
     * A singleton, the single instance of this service.
     */
    private static final HistoryService instance = new HistoryService();
    /**
     * Repository used for database operations.
     */
    private final HistoryRepository repository = new HistoryRepository();

    /**
     * Private constructor for singleton usage.
     */
    private HistoryService() {}

    /**
     * Creates a new tab with its FXML content.
     * @return the history {@link Tab}.
     */
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

    /**
     * Loads the browsing history for a given account.
     * @param account the account whose history is loaded.
     * @return an {@link ObservableList} of {@link HistoryEntry}.
     */
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

    /**
     * Deletes all browsing history for a given account.
     * @param account the account whose history should be deleted.
     */
    public void deleteHistory(Account account) {
        if(account.isRegistered()) {
            try {
                repository.deleteHistory(account);
            } catch(SQLException e ) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Stores a new history entry for the current account.
     * @param date the visit date and time.
     * @param url the visited URL.
     */
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

    /**
     * getter for the singleton instance.
     * @return the current instance.
     */
    public static HistoryService getInstance() {
        return instance;
    }
}
