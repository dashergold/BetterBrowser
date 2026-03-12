package org.webbrowser.accounts.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.webbrowser.accounts.Account;
import org.webbrowser.accounts.service.AccountService;


/**
 * @author Axel
 * @since 2026
 */
public class AccountController {
    private Account account;
    private final AccountService accountService = AccountService.getInstance();
    @FXML
    private Label usernameLabel;
    public void initialize() {
        account = accountService.getCurrentAccount();
        if(account != null) {
            usernameLabel.setText(account.getUsername());
        }
    }

    @FXML
    private void signOut() {
        accountService.signOut();
    }

    @FXML
    private void deleteAccount() {
        accountService.deleteAccount();
    }





}
