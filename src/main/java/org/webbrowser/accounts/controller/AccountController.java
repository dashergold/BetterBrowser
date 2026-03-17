package org.webbrowser.accounts.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import org.webbrowser.accounts.Account;
import org.webbrowser.accounts.service.AccountService;
/**
 * Controller responsible for handling account related user interface actions.
 * <p>
 *     This controller manages the currently logged in account view,
 *     displays the username, and provides the ability to sign out
 *     and delete the account.
 *
 * @author Axel
 * @since 2026
 */
public class AccountController {
    /**
     * Service responsible for account operations.
     */
    private final AccountService accountService = AccountService.getInstance();
    /**
     * The currently logged in account.
     */
    private Account account;
    /**
     * Label used to display the username of the current account.
     */
    @FXML
    private Label usernameLabel;

    /**
     * Initializes the controller after the FXML file has been loaded.
     * <p>
     * Retrieves the currently logged in account from {@link AccountService} and displays it in the UI.
     *
     */
    public void initialize() {
        account = accountService.getCurrentAccount();
        if(account != null) {
            usernameLabel.setText(account.getUsername());
        }
    }
    /**
     * Signs the user out of the application.
     */
    @FXML
    private void signOut() {
        accountService.signOut();
    }
    /**
     * Deletes the currently logged in account.
     */
    @FXML
    private void deleteAccount() {
        accountService.deleteAccount();
    }

}
