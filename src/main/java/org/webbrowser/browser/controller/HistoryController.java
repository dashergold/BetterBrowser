package org.webbrowser.browser.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import org.webbrowser.accounts.Account;
import org.webbrowser.accounts.service.AccountService;
import org.webbrowser.browser.HistoryEntry;
import org.webbrowser.browser.repository.HistoryRepository;
import org.webbrowser.browser.service.HistoryService;

import java.sql.*;


/**
 * Controller responsible for displaying and managing browsing history.
 * <p>
 * This controller:
 * <ul>
 *     <li>Loads the current users browsing history.</li>
 *     <li>Displays it in a table view.</li>
 *     <li>Allows the user to delete their history.</li>
 * </ul>
 * @author Axel
 * @since 2026
 */
public class HistoryController {
    /**
     * Service used to retrieve the current account.
     */
    private final AccountService accountService = AccountService.getInstance();
    /**
     * Service responsible for history related operations.
     */
    private final HistoryService historyService = HistoryService.getInstance();
    /**
     * The currently logged in account.
     */
    private Account account;
    /**
     * Table displaying the browser history.
     */
    @FXML
    private TableView<HistoryEntry> historyTable;
    /**
     * Column displaying the visit date.
     */
    @FXML
    private TableColumn<HistoryEntry, String> dateCol;
    /**
     * Column displaying the visited URL.
     */
    @FXML
    private TableColumn<HistoryEntry, String> urlCol;

    /**
     * Initializes the controller after the FXML has been loaded.
     * <p>
     * Configures table columns and loads the browsing history for the current account.
     */
    public void initialize() {
        historyTable.setMaxWidth(Double.MAX_VALUE);
        historyTable.setMaxHeight(Double.MAX_VALUE);
        account= accountService.getCurrentAccount();
        historyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        dateCol.setPrefWidth(180);
        urlCol.setPrefWidth(400);
        dateCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDate())
        );
        urlCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUrl())
        );
        historyTable.setItems(historyService.loadHistory(account));
        if (historyTable.getParent() instanceof Region) {
            Region parent = (Region) historyTable.getParent();
            parent.setMaxWidth(Double.MAX_VALUE);
            parent.setMaxHeight(Double.MAX_VALUE);
        }
    }
    /**
     * Deletes the browsing history for the current account and clears the table view.
     */
    @FXML
    private void deleteHistory() {
        historyService.deleteHistory(account);
        historyTable.getItems().clear();
    }
}
