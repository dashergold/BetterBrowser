package org.webbrowser.browser.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.webbrowser.accounts.Account;
import org.webbrowser.accounts.service.AccountService;
import org.webbrowser.browser.HistoryEntry;
import org.webbrowser.browser.repository.HistoryRepository;
import org.webbrowser.browser.service.HistoryService;

import java.sql.*;
//TODO THIS AND ACCOUNT SQL SHOULD BE MOVED TO A SEPARATE FILE E.G. "SQLHANDLER"

/**
 * @author Axel
 * @since 2026
 */
public class HistoryController {

    private final AccountService accountService = AccountService.getInstance();
    private final HistoryService historyService = HistoryService.getInstance();
    private Account account;

    @FXML
    private TableView<HistoryEntry> historyTable;
    @FXML
    private TableColumn<HistoryEntry, String> dateCol;
    @FXML
    private TableColumn<HistoryEntry, String> urlCol;

    public void initialize() {
        historyTable.getItems().clear();
        account= accountService.getCurrentAccount();
        dateCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDate())
        );
        urlCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUrl())
        );
        historyTable.setItems(historyService.loadHistory(account));

    }

    @FXML
    private void deleteHistory() {
        historyService.deleteHistory(account);
        historyTable.getItems().clear();
    }





}
